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
        UserInterface ui = new UserInterface(entityManager);
        ui.start();
//        List<Book> selfDevBooks = new ArrayList<>();
//        Genre selfDevelopment = new Genre("Self Development", selfDevBooks);
//
//        List<Book> novelBooks = new ArrayList<>();
//        Genre novel = new Genre("Novel", selfDevBooks);
//
//        List<Book> sciFiBooks = new ArrayList<>();
//        Genre sciFi = new Genre("Science Fiction", sciFiBooks);
//
//        List<Author> book1Authors = new ArrayList<>();
//        List<Reservation> book1Reservations = new ArrayList<>();
//        Book book1 = new Book("How to Win Friends and Influence People",
//                "Bucharest",
//                1936,
//                5,
//                book1Authors,
//                selfDevelopment,
//                book1Reservations);
//
//        List<Author> book5Authors = new ArrayList<>();
//        List<Reservation> book5Reservations = new ArrayList<>();
//        Book book5 = new Book("How to Stop Worrying and Start Living.",
//                "Bucharest",
//                1948,
//                2,
//                book5Authors,
//                selfDevelopment,
//                book5Reservations);
//
//        List<Author> book2Authors = new ArrayList<>();
//        List<Reservation> book2Reservations = new ArrayList<>();
//        Book book2 = new Book("The 7 Habits of Highly Effective People: Powerful Lessons in Personal Change",
//                "Brasov",
//                1989,
//                10,
//                book2Authors,
//                selfDevelopment,
//                book2Reservations);
//
//        List<Book> dCarnegieBooks = new ArrayList<>();
//        Author dCarnegie = new Author("Dale","Carnegie",dCarnegieBooks);
//        book1Authors.add(dCarnegie);
//        book5Authors.add(dCarnegie);
//
//        List<Book> sCoveyList = new ArrayList<>();
//        Author sCovey = new Author("Stephen R.","Covey",dCarnegieBooks);
//        book2Authors.add(sCovey);

//        entityManager.getTransaction().begin();
//        entityManager.persist(book1);
//        entityManager.persist(book2);
//        entityManager.persist(book5);
//        entityManager.persist(selfDevelopment);
//        entityManager.persist(sciFi);
//        entityManager.persist(novel);
//        entityManager.persist(dCarnegie);
//        entityManager.persist(sCovey);
//        entityManager.getTransaction().commit();
//        List<Book> newList = InventoryManager.allBooks(entityManager);
//        newList.forEach(book -> System.out.println(book.getTitle()));
//        List<Book> newList = InventoryManager.searchAuthor("Carnegie", entityManager);
//        System.out.println(newList.size());
//        newList.forEach(book -> System.out.println(book.getTitle()));
//        AuthorsRepository aRepo = new AuthorsRepository(entityManager);
//        List<Author> newList = aRepo.searchName("Carnegie");
//        newList.forEach(author -> System.out.println(author.getFirstName() + " " + author.getLastName()));

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
