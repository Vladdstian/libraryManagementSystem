package com.libManag;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class AuthorsRepository {
    private final EntityManager entityManager;

    public AuthorsRepository(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    // searching records by Name
    public List<Author> searchName(String lastName, String firstName) {

        return entityManager.createQuery("select a from Authors a where lastName = :lastName and firstName = :firstName", Author.class)
                .setParameter("lastName", lastName)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    public List<Author> searchName(String lastName) {

        return entityManager.createQuery("select a from Authors a where lastName = :lastName", Author.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    public List<Author> searchId(int Id) {

        return entityManager.createQuery("select a from Authors a where id = :id", Author.class)
                .setParameter("id", Id)
                .getResultList();
    }
}

