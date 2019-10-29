package com.sixt.rental.demo.integration.controller;

import com.sixt.rental.demo.domain.Borrower;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

public class BorrowerController {

    private ArrayList<Borrower> borrowers = new ArrayList<>() {{
        add(0, Borrower.builder().id(UUID.randomUUID()).email("test1@test.com").firstName("Test 1").lastName("Test 1").build());
        add(1, Borrower.builder().id(UUID.randomUUID()).email("test2@test.com").firstName("Test 2").lastName("Test 2").build());
        add(2, Borrower.builder().id(UUID.randomUUID()).email("test3@test.com").firstName("Test 3").lastName("Test 3").build());
        add(3, Borrower.builder().id(UUID.randomUUID()).email("test4@test.com").firstName("Test 4").lastName("Test 4").build());
    }};

    @GetMapping("borrowers")
    public ResponseEntity list() {
        return ResponseEntity.ok().body(borrowers);
    }

    @GetMapping("borrowers/{borrowerId}")
    public ResponseEntity get(@PathVariable int borrowerId) {
        if (borrowerId >= borrowers.size()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(borrowers.get(borrowerId));
    }

    @PostMapping("borrowers")
    public ResponseEntity add(@RequestBody Borrower borrower) {
        borrowers.add(borrower);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("borrowers/{borrowerId}")
    public ResponseEntity remove(@PathVariable int borrowerId) {
        if (borrowerId >= borrowers.size()) {
            return ResponseEntity.notFound().build();
        }
        borrowers.remove(borrowerId);
        return ResponseEntity.ok().build();
    }
}
