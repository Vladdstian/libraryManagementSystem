package com.libManag;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity (name = "reservations")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reservation extends BaseIdentifier {

    public static final int Deadline = 30;

    private StateOfReservation state;

    private LocalDate dateOfReservation;

    @ManyToOne // mai multe rezervari facute de un client

    private Client client;

    @OneToMany // o rezervare pentru una sau mai multe carti

    private List<Book> bookList;

}

