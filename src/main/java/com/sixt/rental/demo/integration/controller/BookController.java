package com.sixt.rental.demo.integration.controller;

import com.sixt.rental.demo.domain.BookService;
import com.sixt.rental.demo.domain.Entity.Book;
import com.sixt.rental.demo.domain.Entity.Borrower;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("books")
    public ResponseEntity list() {
        var result = bookService.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("books/{bookId}")
    public ResponseEntity get(@PathVariable UUID bookId) {
        var result = bookService.getById(bookId);
        if (result.isPresent()) {
            return ResponseEntity.ok().body(result.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("books")
    public ResponseEntity add(@RequestBody Book newBook) {
        return ResponseEntity.ok().body(bookService.add(newBook));
    }

    @DeleteMapping("books/{bookId}")
    public ResponseEntity remove(@PathVariable UUID bookId) {
        bookService.deleteById(bookId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("books/borrow/{bookId}")
    public ResponseEntity borrowBook (@PathVariable UUID bookId, @RequestBody Borrower borrower) {
        bookService.borrowBook(bookId, borrower);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("books/return/{bookId}")
    public ResponseEntity returnBook (@PathVariable UUID bookId, @RequestBody Borrower borrower) {
        bookService.returnBook(bookId, borrower);
        return ResponseEntity.ok().build();
    }

}
