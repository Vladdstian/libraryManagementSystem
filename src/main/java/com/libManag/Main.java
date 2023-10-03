package com.libManag;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
        List<Book> selfDevBooks = new ArrayList<>();
        Genre selfDevelopment = new Genre("Self Development", selfDevBooks);

        List<Book> novelBooks = new ArrayList<>();
        Genre novel = new Genre("Novel", selfDevBooks);

        List<Book> sciFiBooks = new ArrayList<>();
        Genre sciFi = new Genre("Science Fiction", sciFiBooks);

        List<Author> book1Authors = new ArrayList<>();
        List<Reservation> book1Reservations = new ArrayList<>();
        Book book1 = new Book("How to Win Friends and Influence People",
                "Bucharest",
                1936,
                5,
                true,
                book1Authors,
                selfDevelopment,
                book1Reservations);

        List<Author> book2Authors = new ArrayList<>();
        List<Reservation> book2Reservations = new ArrayList<>();
        Book book2 = new Book("The 7 Habits of Highly Effective People: Powerful Lessons in Personal Change",
                "Brasov",
                1989,
                10,
                true,
                book2Authors,
                selfDevelopment,
                book2Reservations);

        List<Author> book3Authors = new ArrayList<>();
        List<Reservation> book3Reservations = new ArrayList<>();
        Book book3 = new Book("Dune",
                "Brasov",
                1965,
                6,
                true,
                book3Authors,
                selfDevelopment,
                book3Reservations);

        List<Author> book4Authors = new ArrayList<>();
        List<Reservation> book4Reservations = new ArrayList<>();
        Book book4 = new Book("Dune",
                "Brasov",
                1965,
                6,
                true,
                book4Authors,
                selfDevelopment,
                book4Reservations);
    }















    public static List<Book> displayBooks(String name, EntityManager entityManager) {
        List<Book> foundBooks = InventoryManager.searchTitle(name, entityManager);
        if(foundBooks.isEmpty()) {
            System.out.println("Book couldn't be found. Please try again");
            return null;
        } else {
            int count = 0;
            for (Book foundBook : foundBooks) {
                System.out.printf("%d. \"%s\" \n\tAuthor(s): %s \n\tGenre: %s\n",
                        count,
                        foundBook.getTitle(),
                        Arrays.toString(foundBook.getAuthorList().toArray()),
                        foundBook.getGenre().toString()
                );
            }
            return foundBooks;
        }
    }

}
