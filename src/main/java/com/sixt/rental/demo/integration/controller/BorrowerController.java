package com.sixt.rental.demo.integration.controller;

import com.sixt.rental.demo.domain.BorrowerService;
import com.sixt.rental.demo.domain.Entity.Borrower;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BorrowerController {

    private final BorrowerService borrowerService;

    @GetMapping("borrowers")
    public ResponseEntity list() {
        return ResponseEntity.ok().body(borrowerService.findAll());
    }

    @GetMapping("borrowers/{borrowerId}")
    public ResponseEntity get(@PathVariable UUID borrowerId) {
        var result = borrowerService.getById(borrowerId);
        if (result.isPresent()) {
            return ResponseEntity.ok().body(result.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("borrowers")
    public ResponseEntity add(@RequestBody Borrower borrower) {
        return ResponseEntity.ok().body(borrowerService.add(borrower));
    }

    @DeleteMapping("borrowers/{borrowerId}")
    public ResponseEntity remove(@PathVariable UUID borrowerId) {
        borrowerService.deleteById(borrowerId);
        return ResponseEntity.ok().build();
    }
}
