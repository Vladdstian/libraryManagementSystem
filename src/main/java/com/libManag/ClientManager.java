package com.libManag;

import jakarta.persistence.EntityManager;

import java.util.List;

public class ClientManager {

    private ClientManager() {

    }

    // searching clients by complete name
    public static List<Client> searchName(String lastName, String firstName, EntityManager entityManager) {
        return entityManager.createQuery("select a from Client a where " +
                        "lastName = :lastName and firstName = :firstName", Client.class)
                .setParameter("lastName", lastName)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    // searching clients by last name
    public static List<Client> searchName(String lastName, EntityManager entityManager) {
        return entityManager.createQuery("select a from Client a where lastName = :lastName", Client.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    // searching clients by id - useless
    public static List<Client> searchId(int Id, EntityManager entityManager) {
        return entityManager.createQuery("select a from Client a where id = :id", Client.class)
                .setParameter("id", Id)
                .getResultList();
    }

    // search a client's all reservations
    public static List<Client> searchReservations(String lastName, EntityManager entityManager) {
        return entityManager.createQuery("select a from Client a where lastName = :lastName", Client.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    public static List<Client> searchUsername(String username, EntityManager entityManager) {
        return entityManager.createQuery("select a from Client a where username = :username", Client.class)
                .setParameter("username", username)
                .getResultList();
    }
}

