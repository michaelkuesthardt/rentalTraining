package com.sixt.rental.demo.integration.repository;

import com.sixt.rental.demo.domain.Entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, UUID> {
}
