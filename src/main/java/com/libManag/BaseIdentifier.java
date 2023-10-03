package com.libManag;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseIdentifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
