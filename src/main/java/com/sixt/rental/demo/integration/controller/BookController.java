package com.sixt.rental.demo.integration.controller;

import com.sixt.rental.demo.domain.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class BookController {

    private ArrayList<Book> books = new ArrayList<>() {{
        add(0, Book.builder().id(UUID.randomUUID()).title("Test 1").isbn("-").build());
        add(1, Book.builder().id(UUID.randomUUID()).title("Test 2").isbn("-").build());
        add(2, Book.builder().id(UUID.randomUUID()).title("Test 3").isbn("-").build());
        add(3, Book.builder().id(UUID.randomUUID()).title("Test 4").isbn("-").build());
    }};

    @GetMapping("books")
    public ResponseEntity list() {
        return ResponseEntity.ok().body(books);
    }

    @GetMapping("books/{bookId}")
    public ResponseEntity get(@PathVariable int bookId) {
        if (bookId >= books.size()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(books.get(bookId));
    }

    @PostMapping("books")
    public ResponseEntity add(@RequestBody Book book) {
        books.add(book);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("books/{bookId}")
    public ResponseEntity remove(@PathVariable int bookId) {
        if (bookId >= books.size()) {
            return ResponseEntity.notFound().build();
        }
        books.remove(bookId);
        return ResponseEntity.ok().build();
    }

}
