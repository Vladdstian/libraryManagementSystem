package com.libManag;

import jakarta.persistence.EntityManager;

import java.util.List;

public interface GenreManager {

    static List<Genre> searchName(String name, EntityManager entityManager) {
        return entityManager.createQuery("select g from Genre g where name = :name", Genre.class)
                .setParameter("name", name)
                .getResultList();
    }
}
