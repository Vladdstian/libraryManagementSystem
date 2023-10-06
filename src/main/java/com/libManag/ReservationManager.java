package com.libManag;

import jakarta.persistence.EntityManager;

import java.util.List;

public interface ReservationManager {

    static List<Reservation> searchId(int id, EntityManager entityManager) {
        return entityManager.createQuery("select r from Reservations r where id = :id", Reservation.class)
                .setParameter("id", id)
                .getResultList();
    }
}
