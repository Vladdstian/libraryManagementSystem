package com.libManag;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "reservations")
@Data
public class Reservation extends BaseIdentifier {
    public static final int maxDaysLoaned = 30;
    private StateOfReservation state;
    private LocalDate dateOfReservation;
    private LocalDate dateOfReturn;

    private Reservation() {

    }
    public Reservation(StateOfReservation state, LocalDate dateOfReservation, Client client, List<Book> bookList) {
        super();
        this.state = state;
        this.dateOfReservation = dateOfReservation;
        this.client = client;
        this.bookList = bookList;
    }

    @ManyToOne
    private Client client;

    @ManyToMany
    private List<Book> bookList;

}
