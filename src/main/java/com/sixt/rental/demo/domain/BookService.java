package com.sixt.rental.demo.domain;

import com.sixt.rental.demo.domain.Entity.Book;
import com.sixt.rental.demo.domain.Entity.Borrower;
import com.sixt.rental.demo.integration.repository.AuthorRepository;
import com.sixt.rental.demo.integration.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

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
        return bookRepository.save(book);
    }

    public Book update(Book newBook, UUID bookId) {
        return bookRepository.findById(bookId)
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
    }

    public void deleteById(UUID id) {
        bookRepository.deleteById(id);
    }

    public void borrowBook(UUID bookId, Borrower borrower) {
        var bookResult = bookRepository.findById(bookId);
        if (bookResult.isPresent()) {
            var book = bookResult.get();
            book.setBorrower(borrower);
            bookRepository.save(book);
        }
    }

    public void returnBook(UUID bookId, Borrower borrower) {
        var bookResult = bookRepository.findById(bookId);
        if (bookResult.isPresent()) {
            var book = bookResult.get();
            if (book.getBorrower().getId().equals(borrower.getId())) {
                book.setBorrower(null);
                bookRepository.save(book);
            }
        }
    }
}
