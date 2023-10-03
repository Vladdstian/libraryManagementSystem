package com.libManag;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@MappedSuperclass
@NoArgsConstructor
public abstract class Person extends BaseIdentifier {
    private String firstName;
    private String lastName;
}
