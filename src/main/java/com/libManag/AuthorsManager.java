package com.libManag;

import jakarta.persistence.EntityManager;

import java.util.List;

public interface AuthorsManager {

    // searching Authors by complete name
    static List<Author> searchName(String lastName, String firstName, EntityManager entityManager) {
        return entityManager.createQuery("select a from Author a where lastName = :lastName and firstName = :firstName", Author.class)
                .setParameter("lastName", lastName)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    // searching Authors by first name
    static List<Author> searchFirstName(String firstName, EntityManager entityManager) {
        return entityManager.createQuery("select a from Author a where firstName = :firstName", Author.class)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    // searching Authors by last name
    static List<Author> searchLastName(String lastName, EntityManager entityManager) {
        return entityManager.createQuery("select a from Author a where lastName = :lastName", Author.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    // searching Authors by id - useless
    static List<Author> searchId(int Id, EntityManager entityManager) {
        return entityManager.createQuery("select a from Author a where id = :id", Author.class)
                .setParameter("id", Id)
                .getResultList();
    }
}
