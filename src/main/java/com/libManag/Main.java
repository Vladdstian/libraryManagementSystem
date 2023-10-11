package com.libManag;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
        ui.mainMenu();
    }
}