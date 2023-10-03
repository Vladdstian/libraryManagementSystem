package com.libManag;

import jakarta.persistence.EntityManager;

import java.util.List;

public class ClientRepository {
    private final EntityManager entityManager;

    public ClientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // searching clients by complete name
    public List<Client> searchName(String lastName, String firstName) {
        return entityManager.createQuery("select a from Clients a where " +
                        "lastName = :lastName and firstName = :firstName", Client.class)
                .setParameter("lastName", lastName)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    // searching clients by last name
    public List<Client> searchName(String lastName) {
        return entityManager.createQuery("select a from Clients a where lastName = :lastName", Client.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    // searching clients by id - useless
    public List<Client> searchId(int Id) {
        return entityManager.createQuery("select a from Clients a where id = :id", Client.class)
                .setParameter("id", Id)
                .getResultList();
    }

    // search a client's all reservations
    public List<Client> searchReservations(String lastName) {
        return entityManager.createQuery("select a from Clients a where lastName = :lastName", Client.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }
}

