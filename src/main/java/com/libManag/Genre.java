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
public class Genre extends BaseIdentifier {
    private String name;

    @OneToMany (mappedBy = "genre")
    private List<Book> bookGenres;
}
