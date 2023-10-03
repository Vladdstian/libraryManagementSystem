package com.libManag;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
public class Client extends Person {
    private String username;
    private String password;

    @OneToMany (mappedBy = "client")
    private List<Reservation> clientReservations;

    public Client(String firstName,
                  String lastName, String username,
                  String password, List<Reservation> clientReservations) {
        super(firstName, lastName);
        this.username = username;
        this.password = password;
        this.clientReservations = clientReservations;
    }
}
