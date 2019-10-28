package com.sixt.rental.demo.integration.controller;

import com.sixt.rental.demo.domain.Author;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class AuthorController {

    private ArrayList<Author> authors = new ArrayList<>(){{
        add(0, Author.builder().id(UUID.randomUUID()).firstName("Firstname 1").lastName("Lastname 1").build());
        add(1, Author.builder().id(UUID.randomUUID()).firstName("Firstname 2").lastName("Lastname 2").build());
        add(2, Author.builder().id(UUID.randomUUID()).firstName("Firstname 3").lastName("Lastname 3").build());
        add(3, Author.builder().id(UUID.randomUUID()).firstName("Firstname 4").lastName("Lastname 4").build());
    }};

    @GetMapping("authors")
    public ResponseEntity list() {
        return ResponseEntity.ok().body(authors);
    }

    @GetMapping("authors/{authorId}")
    public ResponseEntity get(@PathVariable int authorId) {
        if (authorId >= authors.size()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(authors.get(authorId));
    }

    @PostMapping("authors")
    public ResponseEntity add(@RequestBody Author author) {
        authors.add(author);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("authors/{authorId}")
    public ResponseEntity remove(@PathVariable int authorId) {
        if (authorId >= authors.size()) {
            return ResponseEntity.notFound().build();
        }
        authors.remove(authorId);
        return ResponseEntity.ok().build();
    }
}
