package com.libManag;

import jakarta.persistence.EntityManager;

import java.util.List;

public interface BookManager {

    // Searching records by title
    // changed method to retrieve all similar titles to the inputted string
    static List<Book> searchTitle(String title, EntityManager entityManager) {
        return entityManager.createQuery("select b from Book b where LOWER(title) LIKE LOWER(:title)", Book.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }

    // Searching records by location
    static List<Book> searchLocation(String location, EntityManager entityManager) {
        return entityManager.createQuery("select b from Book b where location= :location", Book.class)
                .setParameter("location", location)
                .getResultList();
    }

    // Searching records by year of release
    static List<Book> searchYearOfRelease(int yearReleased, EntityManager entityManager) {
        return entityManager.createQuery("select b from Book b where yearReleased= :yearReleased", Book.class)
                .setParameter("yearReleased", yearReleased)
                .getResultList();
    }

    // Searching all books
    static List<Book> allBooks(EntityManager entityManager){
        return entityManager.createQuery("from Book", Book.class)
                .getResultList();
    }
}
