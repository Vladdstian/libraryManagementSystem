package com.libManag;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class UserInterface {
    private final EntityManager entityManager;
    private final Scanner scanner;

    public UserInterface(EntityManager entityManager) {
        this.entityManager = entityManager;
        scanner = new Scanner(System.in);
    }

    public void start() {
        while(true) {
            menu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 4 -> {
                    System.out.println("Exiting the program...");
                    return;
                }
                case 1 -> {
                    createUser();
                }
                case 2 -> {

                }
                case 3 -> {
                    createBook();
                }
            }
        }
    }

    private void menu() {
        System.out.println(
                    "Welcome!\n" +
                    "1. Create user\n" +
                    "2. Log In\n" +
                    "3. Admin\n" +
                    "Enter 4 to quit"
                );
    }

    private void createUser() {
        System.out.println("Please enter your last name: ");
        String lastName = scanner.next();

        System.out.println("Please enter your first name: ");
        String firstName = scanner.next();

        System.out.println("Please enter a username: ");
        String username = scanner.next();

        System.out.println("Please enter a password: ");
        String password = scanner.next();

        List<Reservation> clientReservations = new ArrayList<>();
        Client newClient = new Client(firstName,lastName,username,password,clientReservations);

        Service.save(newClient, entityManager);
    }

    private void createBook() {
        System.out.println("Please enter the book title: ");
        String title = scanner.next();

        System.out.println("Please enter the location where the book can be found: ");
        String location = scanner.next();

        System.out.println("Please enter the year when the book was released: ");
        int yearReleased = scanner.nextInt();

        System.out.println("Please enter how many books are on the inventory: ");
        int bookCount = scanner.nextInt();

        System.out.println("How many authors wrote the book?: ");
        int authorsNum = scanner.nextInt();

        // create or add author(s) to the new book
        List<Author> bookAuthors = new ArrayList<>(5);
        for (int i = 0; i < authorsNum; i++) {
            //1 - search for an author
            System.out.println("Please search for an Author last name in the database: ");
            String authorName = scanner.next();
            List<Author> foundAuthors = AuthorsRepository.searchName(authorName, entityManager);
            //2 - if exists, add the author
            if (!foundAuthors.isEmpty()) {
                bookAuthors.add(foundAuthors.get(0));
            } else bookAuthors.add(createAuthor()); // 3 - if it doesn't exist we create a new one
        }

        // create or add a genre to the book
        Genre bookGenre;
        //1 - search for a Genre
        System.out.println("What is the genre of the book: ");
        String genreName = scanner.next();
        List<Genre> foundGenre = GenreRepository.searchName(genreName, entityManager);
        //2 - if exists, add the genre
        if (!foundGenre.isEmpty()) {
            bookGenre = foundGenre.get(0);
        } else bookGenre = createGenre(); // 3 - if it doesn't exist we create a new one

        List<Reservation> bookReservations = new ArrayList<>();

        Book newBook = new Book(title,
                location, yearReleased, bookCount,
                bookAuthors, bookGenre, bookReservations);

        Service.save(newBook, entityManager);
    }


    private Author createAuthor() {
        return null;
    }

    private Genre createGenre() {
        return null;
    }
}
