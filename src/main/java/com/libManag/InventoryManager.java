package com.libManag;

import jakarta.persistence.EntityManager;

import java.util.List;

public class InventoryManager {
//    private final EntityManager entityManager;

//    public InventoryManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    // Create new books
    public static void addBook(Book newBook, EntityManager entityManager) {
        Service.save(newBook, entityManager);
    }

    // Searching records by title
    public static List<Book> searchTitle(String title, EntityManager entityManager) {
        return entityManager.createQuery("select b from Book b where title= :title", Book.class)
                .setParameter("title", title)
                .getResultList();
    }

    // Searching books by Genre
    public static List<Book> searchGenre(String genre, EntityManager entityManager) {
        return entityManager.createQuery("select b from Book b where genre= :genre", Book.class)
                .setParameter("genre", genre)
                .getResultList();
    }

    // Searching books by Author
    public static List<Book> searchAuthor(String authorLastName, EntityManager entityManager) {
        return entityManager.createQuery(
                        "select b from Book b inner join b.authorList a where a.lastName= :authorLastName", Book.class)
                .setParameter("authorLastName", authorLastName)
                .getResultList();
    }
}
