package com.libManag;

import jakarta.persistence.EntityManager;

import java.util.List;

public class BookRepository {

        private final EntityManager entityManager;

        public BookRepository(EntityManager entityManager) {

            this.entityManager = entityManager;
        }

        // searching records by title
        public List<Book> searchTitle(String title) {

            return entityManager.createQuery("select b from Book b where title = :title", Book.class)
                    .setParameter("title", title)
                    .getResultList();
        }

    public List<Book> searchGenre(String genre) {

        return entityManager.createQuery("select b from Book b where genre = :genre", Book.class)
                .setParameter("genre", genre)
                .getResultList();
    }

    public List<Book> searchAuthor(String authorLastName) {

        return entityManager.createQuery("select b from Book b inner join b.authorList a where a.lastName = :authorLastName", Book.class)
                .setParameter("authorLastName", authorLastName)
                .getResultList();
    }


    }
