package com.sixt.rental.demo.domain.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data // be careful with @data - it generates equals and hash code methods
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "books")
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue
    private UUID id;
    private String firstName;
    private String lastName;

    @ManyToMany(
            mappedBy = "authors"
    )
    @JsonIgnore
    private Set<Book> books = new HashSet<Book>();

    public void addBook(Book book) {
        this.books.add(book);
        book.getAuthors().add(this);
    }
}
