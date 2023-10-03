package com.libManag;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Author extends Person {

    @ManyToMany
    private List<Book> bookList;

    public Author(String firstName, String lastName, List<Book> bookList) {
        super(firstName, lastName);
        this.bookList = bookList;
    }
}
