package com.sixt.rental.demo.integration.repository;

import com.sixt.rental.demo.domain.Entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<Book, UUID> {
}
