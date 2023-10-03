package com.libManag;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "reservations")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reservation extends BaseIdentifier {
    public static final int daysLoaned = 30;
    private StateOfReservation state;
    private LocalDate dateOfReservation;

    @ManyToOne
    private Client client;

    @ManyToMany
    private List<Book> bookList;

}

