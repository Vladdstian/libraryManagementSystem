package com.libManag;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class Service {

    private Service() {
    }

    public static <T> T save(final T obj, EntityManager entityManager) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
            }
            entityManager.persist(obj);
            transaction.commit();
            return obj;
        } catch (final Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return null;
        }
    }

    public static <T> T delete(final T obj, EntityManager entityManager) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
            }
            entityManager.remove(obj);
            transaction.commit();
            return obj;
        } catch (final Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return null;
        }
    }

}
