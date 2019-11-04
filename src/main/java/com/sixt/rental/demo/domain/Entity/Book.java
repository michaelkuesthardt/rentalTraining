package com.sixt.rental.demo.domain.Entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter@Setter
@Accessors(chain = true)
@EqualsAndHashCode(exclude = "authors")
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String isbn;
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(name = "book_author",
            joinColumns = { @JoinColumn(name = "fk_book") },
            inverseJoinColumns = { @JoinColumn(name = "fk_author") })
    private Set<Author> authors = new HashSet<>();
    @ManyToOne()
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }
}
