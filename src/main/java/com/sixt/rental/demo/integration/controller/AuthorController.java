package com.sixt.rental.demo.integration.controller;

import com.sixt.rental.demo.domain.Entity.Author;
import com.sixt.rental.demo.domain.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("authors")
    public ResponseEntity list() {
        return ResponseEntity.ok().body(authorService.findAll());
    }

    @GetMapping("authors/{authorId}")
    public ResponseEntity get(@PathVariable UUID authorId) {
        var result = authorService.getById(authorId);
        if (result.isPresent()) {
            return ResponseEntity.ok().body(result.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("authors")
    public ResponseEntity add(@RequestBody Author author) {
        if (authorService.add(author)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("authors/{authorId}")
    public ResponseEntity remove(@PathVariable UUID authorId) {
        authorService.deleteById(authorId);
        return ResponseEntity.ok().build();
    }
}
