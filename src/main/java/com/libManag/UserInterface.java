package com.libManag;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
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

    public void mainMenu () {
        System.out.println(
                "Welcome!\n" +
                "1. Create user\n" +
                "2. Log In\n" +
                "3. Admin\n" +
                "Enter 4 to quit"
                );
        int mainMenuChoice = scanner.nextInt();

        clearScreen();
        switch (mainMenuChoice) {
            case 1 -> {
                createUser();
            }
            case 2 -> {
                userAuthentification();
            }
            case 3 -> {
                adminMenu();
            }
            case 4 -> {
                System.out.println("Exiting the program...");
            }
        }
    }

    private void userAuthentification() {
        clearScreen();
        System.out.println("Please enter your username: ");
        String username = scanner.next();

        Client clientFound = null;
        try{
            clientFound = ClientManager.searchUsername(username, entityManager).get(0);
            System.out.println("Please enter your password: ");
            String typePassword = scanner.next(); //odata identificat clientul acesta va introduce parola
            if (clientFound.getPassword().equals(typePassword)) { // se verifica parola din baza de date (.getPassword()) cu cea introdusa de el
                clearScreen();
                clientMenu(clientFound);
            }
        }catch (IndexOutOfBoundsException e){
            clearScreen();
            System.out.println("User not found please create user");
            createUser();
        }
    }

    private void clientMenu(Client client) {
        System.out.printf("Welcome %s %s!\n" +
                "1. Borrow new books\n" +
                "2. View active borrowings\n" +
                "3. View borrowing history\n" +
                "Enter 4 to go back or 5 to quit\n", client.getFirstName(), client.getLastName());
        int choice = scanner.nextInt();
        choiceClientMenu(choice, client);
    }

    private void choiceClientMenu(int choice, Client client){
        switch (choice){
            case 1 -> {
                borrowNewBooks();
                clientMenu(client);
            }
            case 2 ->{
                viewActiveBorrowings();
                clientMenu(client);
            }
            case 3 ->{
                viewBorrowingHistory();
                clientMenu(client);
            }
            case 4 ->{
                System.out.println("Go back");
                mainMenu();
            }
            case 5 ->{
                System.out.println("Quit...");
            }
        }
    }


    private void borrowNewBooks(){
        System.out.println("Search: ");
        String term = scanner.next();
        List<Book> foundBooks= new ArrayList<>();
        List<Author> foundAuthors= new ArrayList<>();
        List<Genre> foundGenre= new ArrayList<>();
        // searching for books, authors, genre
        foundBooks.addAll(InventoryManager.searchTitle(term, entityManager));
        foundAuthors.addAll(AuthorsRepository.searchName(term, entityManager));
        foundGenre.addAll(GenreRepository.searchName(term, entityManager));

        foundBooks.forEach(book -> System.out.println(book.getTitle()));
        foundAuthors.forEach(author -> System.out.println(author.getLastName() + " " +
                author.getFirstName()));
        foundGenre.forEach(genre -> System.out.println(genre.getName()));
    }

    private void viewActiveBorrowings(){

    }

    public void viewBorrowingHistory(){

    }


    private void adminMenu() {
        System.out.println(
                "Welcome admin!\n" +
                "1. Create book\n" +
                "2. Create author\n" +
                "3. Create genre\n" +
                "Enter 4 to go back or 5 to quit"
        );
        int choice = scanner.nextInt();
        choiceAdminMenu(choice);
    }

    private void choiceAdminMenu(int choice) {
        clearScreen();
        switch (choice) {
            case 1 -> {
                createBook();
                adminMenu();
            }
            case 2 -> {
                createAuthor();
                adminMenu();
            }
            case 3 -> {
                createGenre();
                adminMenu();
            }
            case 4 -> {
                mainMenu();
            }
            case 5 -> {
                System.out.println("Exit in the programm");

            }
        }
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
        List<Author> bookAuthors = new ArrayList<>();
        Author author = null;
        for (int i = 0; i < authorsNum; i++) {
            //1 - search for an author
            System.out.println("Please search for an Author last name in the database: ");
            String authorName = scanner.next();
            List<Author> foundAuthors = AuthorsRepository.searchName(authorName, entityManager);
            //2 - if exists, add the author
            if (!foundAuthors.isEmpty()) {
                author = foundAuthors.get(0);
                System.out.println("Author found: " + author.getFirstName() + " " + author.getLastName());
                bookAuthors.add(author);
            } else { // 3 - if it doesn't exist we create a new one
                System.out.println("Author not found in the database, please create a new author!");
                author = createAuthor();
                bookAuthors.add(author);
            }
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
            System.out.println("Genre found: " + bookGenre.getName());
        } else {
            System.out.println("Genre not found, please create a new genre!");
            bookGenre = createGenre(); // 3 - if it doesn't exist we create a new one
        }

        List<Reservation> bookReservations = new ArrayList<>();

        Book newBook = new Book(title,
                location, yearReleased, bookCount,
                bookAuthors, bookGenre, bookReservations);

        author.getBookList().add(newBook);
        bookGenre.getBookGenres().add(newBook);

        Service.save(newBook, entityManager);
    }


    private Author createAuthor() {
        System.out.println("Please enter author last name: ");
        String lastName = scanner.next();

        System.out.println("Please enter author first name: ");
        String firstName = scanner.next();

        List<Book> bookList = new ArrayList<>();

        Author author = new Author(firstName, lastName, bookList);
        Service.save(author, entityManager);

        return author;
    }

    private Genre createGenre() {
        System.out.println("Please enter a new genre: ");
        String genreName = scanner.next();

        List<Book> genreList = new ArrayList<>();

        Genre genre = new Genre(genreName, genreList);
        Service.save(genre, entityManager);

        return genre;
    }

    private void clearScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}
