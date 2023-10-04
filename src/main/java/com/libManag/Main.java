package com.libManag;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO - create service class for searching entities.
public class Main {

    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Genre.class)
                .addAnnotatedClass(Reservation.class)
                .buildSessionFactory();

        EntityManager entityManager = sessionFactory.createEntityManager();
        UserInterface ui = new UserInterface(entityManager);
        ui.start();

    }
}
