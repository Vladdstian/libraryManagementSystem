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
public class Book extends BaseIdentifier{
   private String title;
    private int yearReleased;
    private boolean isAvailable;

    private int bookCount;


    @ManyToMany (mappedBy = "bookList" )

   private List<Author> authorList;

    @ManyToOne

    private Genre genre;

    @ManyToOne
    private Client client;


}
