package com.libManag;

import jakarta.persistence.EntityManager;

import java.util.List;

public class AuthorsRepository {
//    private final EntityManager entityManager;

//    public AuthorsRepository(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    // searching Authors by complete name
    public static List<Author> searchName(String lastName, String firstName, EntityManager entityManager) {
        return entityManager.createQuery("select a from Author a where lastName = :lastName and firstName = :firstName", Author.class)
                .setParameter("lastName", lastName)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    // searching Authors by last name
    public static List<Author> searchName(String lastName, EntityManager entityManager) {
        return entityManager.createQuery("select a from Author a where lastName = :lastName", Author.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    // searching Authors by id - useless
    public static List<Author> searchId(int Id, EntityManager entityManager) {
        return entityManager.createQuery("select a from Author a where id = :id", Author.class)
                .setParameter("id", Id)
                .getResultList();
    }


}

