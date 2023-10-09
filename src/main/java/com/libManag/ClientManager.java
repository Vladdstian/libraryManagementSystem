package com.libManag;

import jakarta.persistence.EntityManager;

import java.util.List;

public interface ClientManager {

    // searching clients by complete name
    static List<Client> searchName(String lastName, String firstName, EntityManager entityManager) {
        return entityManager.createQuery("select a from Client a where " +
                        "lastName = :lastName and firstName = :firstName", Client.class)
                .setParameter("lastName", lastName)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    // searching clients by last name
    static List<Client> searchLastName(String lastName, EntityManager entityManager) {
        return entityManager.createQuery("select a from Client a where lastName = :lastName", Client.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    // searching clients by first name
    static List<Client> searchFirstName(String firstName, EntityManager entityManager) {
        return entityManager.createQuery("select a from Client a where firstName = :firstName", Client.class)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    // searching clients by id - useless
    static List<Client> searchId(int Id, EntityManager entityManager) {
        return entityManager.createQuery("select a from Client a where id = :id", Client.class)
                .setParameter("id", Id)
                .getResultList();
    }

    // searching clients by username
    static List<Client> searchUsername(String username, EntityManager entityManager) {
        return entityManager.createQuery("select a from Client a where LOWER(username)= LOWER(:username)", Client.class)
                .setParameter("username", username)
                .getResultList();
    }
}
