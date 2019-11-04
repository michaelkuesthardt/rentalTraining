package com.sixt.rental.demo.domain;

import com.sixt.rental.demo.domain.Entity.Author;
import com.sixt.rental.demo.integration.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    public boolean add(Author author) {
        if (author.getId() != null && authorRepository.existsById(author.getId())) {
            return false;
        }
        authorRepository.save(author);
        return true;
    }

    public Optional<Author> getById(UUID id) {
        return authorRepository.findById(id);
    }

    public void deleteById(UUID id) {
        authorRepository.deleteById(id);
    }
}
