package com.sixt.rental.demo.domain;

import com.sixt.rental.demo.domain.Entity.Book;
import com.sixt.rental.demo.domain.Entity.Borrower;
import com.sixt.rental.demo.events.Author;
import com.sixt.rental.demo.events.Identifier;
import com.sixt.rental.demo.events.LifecycleType;
import com.sixt.rental.demo.events.RawBookEvent;
import com.sixt.rental.demo.integration.kafka.KafkaProducer;
import com.sixt.rental.demo.integration.repository.AuthorRepository;
import com.sixt.rental.demo.integration.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private static final long TIMEOUT_VALUE = 1000L;

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final KafkaProducer kafkaProducer;

    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> getById(UUID id) {
        return bookRepository.findById(id);
    }

    public Book add(Book newBook) {
        var book = new Book();
        book.setTitle(newBook.getTitle())
                .setIsbn(newBook.getIsbn());
        newBook.getAuthors().forEach((givenAuthor -> {
            var authorResult = authorRepository.findById(givenAuthor.getId());
            if (authorResult.isPresent()) {
                var author = authorResult.get();
                author.addBook(book);
                book.addAuthor(author);
            }
        }));

        var persistedBook = bookRepository.save(book);
        sendEvent(persistedBook, LifecycleType.LIFECYCLE_ADDED);
        return persistedBook;
    }

    public Book update(Book newBook, UUID bookId) {
        var persistedBook = bookRepository.findById(bookId)
                .map(book -> {
                    book.setIsbn(newBook.getIsbn());
                    book.setTitle(newBook.getTitle());
                    book.setAuthors(newBook.getAuthors());
                    book.setBorrower(newBook.getBorrower());
                    return bookRepository.save(book);
                })
                .orElseGet(() -> {
                    newBook.setId(bookId);
                    return bookRepository.save(newBook);
                });

        sendEvent(persistedBook, LifecycleType.LIFECYCLE_UPDATED);
        return persistedBook;
    }

    public void deleteById(UUID id) {
        bookRepository.deleteById(id);
    }

    public void borrowBook(UUID bookId, Borrower borrower) {
        var bookResult = bookRepository.findById(bookId);
        if (bookResult.isPresent()) {
            var book = bookResult.get();
            book.setBorrower(borrower);
            var persistedBook = bookRepository.save(book);
            sendEvent(persistedBook, LifecycleType.LIFECYCLE_BORROWED);
        }
    }

    public void returnBook(UUID bookId, Borrower borrower) {
        var bookResult = bookRepository.findById(bookId);
        if (bookResult.isPresent()) {
            var book = bookResult.get();
            if (book.getBorrower().getId().equals(borrower.getId())) {
                book.setBorrower(null);
                var persistedBook = bookRepository.save(book);
                sendEvent(persistedBook, LifecycleType.LIFECYCLE_RETURNED);
            }
        }
    }

    private void sendEvent(Book book, LifecycleType status) {
        var event = RawBookEvent.newBuilder()
                .setIdentifier(Identifier.newBuilder().setValue(book.getId().toString()).build())
                .setIsbn(book.getIsbn())
                .setTitle(book.getTitle())
                .setStatus(status);

        book.getAuthors().forEach(
                (author -> {
                    event.addAuthor(Author.newBuilder()
                            .setIdentifier(Identifier.newBuilder().setValue(author.getId().toString()).build())
                            .setFirstName(author.getFirstName())
                            .setLastName(author.getLastName())
                            .build());
                })
        );

        var borrower = book.getBorrower();
        if (borrower != null) {
            event.setBorrower(com.sixt.rental.demo.events.Borrower.newBuilder()
                    .setFirstName(borrower.getFirstName())
                    .setLastName(borrower.getLastName())
                    .setEmail(borrower.getEmail())
                    .build());
        }
        var generatedEvent = event.build();
        CompletableFuture<SendResult<String, RawBookEvent>> completable =
                kafkaProducer.send(generatedEvent).completable();
        try {
            completable.get(TIMEOUT_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Error publishing event", e);
        }
        log.info("Event published {}", kv("event", book));
    }
}
