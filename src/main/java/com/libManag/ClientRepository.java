package com.libManag;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class ClientRepository {
    
        private final EntityManager entityManager;

        public ClientRepository(EntityManager entityManager) {

            this.entityManager = entityManager;
        }


        // searching records by Name
        public List<Client> searchName(String lastName, String firstName) {

            return entityManager.createQuery("select a from Clients a where lastName = :lastName and firstName = :firstName", Client.class)
                    .setParameter("lastName", lastName)
                    .setParameter("firstName", firstName)
                    .getResultList();
        }

        public List<Client> searchName(String lastName) {

            return entityManager.createQuery("select a from Clients a where lastName = :lastName", Client.class)
                    .setParameter("lastName", lastName)
                    .getResultList();
        }

    public List<Client> searchId(int Id) {

        return entityManager.createQuery("select a from Clients a where id = :id", Client.class)
                .setParameter("id", Id)
                .getResultList();
    }


    }

