package com.sixt.rental.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Author {
    private UUID id;
    private String firstName;
    private String lastName;
}
