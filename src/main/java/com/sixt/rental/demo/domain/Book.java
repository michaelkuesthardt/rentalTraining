package com.sixt.rental.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Book {
    private UUID id;
    private String title;
    private String isbn;
    private UUID authorId;
    private UUID borrowerId;
}
