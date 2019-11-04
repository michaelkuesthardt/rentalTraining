package com.sixt.rental.demo.domain;

import com.sixt.rental.demo.domain.Entity.Borrower;
import com.sixt.rental.demo.integration.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    public Iterable<Borrower> findAll() {
        return borrowerRepository.findAll();
    }

    public Optional<Borrower> getById(UUID id) {
        return borrowerRepository.findById(id);
    }

    public Borrower add(Borrower newBorrower) {
        return borrowerRepository.save(newBorrower);
    }

    public void deleteById(UUID id) {
        borrowerRepository.deleteById(id);
    }
}
