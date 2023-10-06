package com.libManag;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;

@MappedSuperclass
@Data
public class BaseIdentifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
