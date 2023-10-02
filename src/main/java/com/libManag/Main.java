package com.libManag;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** Sistem de Gestionare a Rețelei de Biblioteci
 Descriere Scurtă:
 Dezvoltă un sistem de gestionare pentru o rețea de biblioteci care permite utilizatorilor să împrumute, să returneze și să rezerve cărți. Sistemul ar trebui să aibă următoarele funcții principale:
 Gestionarea Inventarului de Cărți:
 Stocarea informațiilor despre cărțile disponibile, cum ar fi titlul, autorul, anul publicării, genul și locația în bibliotecă.
 Actualizarea inventarului atunci când cărțile sunt împrumutate sau returnate.
 Urmărirea disponibilității cărților în funcție de starea de împrumut.
 Managementul Cititorilor:
 Stocarea detaliilor despre cititori, inclusiv numele, informațiile de contact și istoricul împrumuturilor.
 Permiterea cititorilor să creeze conturi și să se autentifice pentru a gestiona împrumuturile lor.
 Verificarea informațiilor cititorilor în timpul procesului de împrumut.
 Procesul de Împrumut:
 Permiterea cititorilor să caute cărți disponibile în funcție de titlu, autor, gen și alte criterii.
 Afișarea unei liste de cărți disponibile care se potrivesc cu criteriile de căutare.
 Permiterea cititorilor să selecteze o carte și să continue cu împrumutul.
 Calcularea duratei de împrumut și a eventualelor penalități pentru întârziere.
 Confirmarea împrumutului și furnizarea unui ID unic de împrumut cititorului.
 Actualizarea inventarului de cărți și marcarea cărții ca împrumutată pentru perioada specificată.
 Managementul Împrumuturilor:
 Permiterea cititorilor să vizualizeze, să prelungească sau să încheie împrumuturile existente.
 Gestionarea conflictelor dacă o carte este deja împrumutată sau rezervată pentru perioada solicitată.
 Trimiterea de notificări către cititori pentru confirmările de împrumut, prelungiri sau încheieri ale împrumuturilor.

 */

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







    }


}
