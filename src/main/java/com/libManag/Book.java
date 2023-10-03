package com.libManag;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book extends BaseIdentifier {
    private String title;
    private String location;
    private int yearReleased;
    private int bookCount;
    private boolean isAvailable;

    @ManyToMany(mappedBy = "bookList")
    private List<Author> authorList;

    @ManyToOne
    private Genre genre;

    @ManyToMany
    private List<Reservation> reservation;

//    no need to have a relationship with the books since we have a reservation class
//    @ManyToOne
//    private Client client;
}
