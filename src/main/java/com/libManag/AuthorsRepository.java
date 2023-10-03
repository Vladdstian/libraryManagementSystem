package com.libManag;

import jakarta.persistence.EntityManager;

import java.util.List;

public class AuthorsRepository {
    private final EntityManager entityManager;

    public AuthorsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // searching Authors by complete name
    public List<Author> searchName(String lastName, String firstName) {
        return entityManager.createQuery("select a from Authors a where lastName = :lastName and firstName = :firstName", Author.class)
                .setParameter("lastName", lastName)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    // searching Authors by last name
    public List<Author> searchName(String lastName) {
        return entityManager.createQuery("select a from Authors a where lastName = :lastName", Author.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    // searching Authors by id - useless
    public List<Author> searchId(int Id) {
        return entityManager.createQuery("select a from Authors a where id = :id", Author.class)
                .setParameter("id", Id)
                .getResultList();
    }


}

