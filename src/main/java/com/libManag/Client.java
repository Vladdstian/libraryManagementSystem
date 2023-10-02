package com.libManag;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Person {
    private String username;
    private String password;

    @OneToMany(mappedBy = "client")
    private List<Book> clientBookList;
}
