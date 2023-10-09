package com.libManag;

import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class UserInterface {
    private final EntityManager entityManager;
    private final Scanner scanner;

    public UserInterface(EntityManager entityManager) {
        this.entityManager = entityManager;
        // changed the scanner type to only read the entry after enter is pressed
        scanner = new Scanner(System.in).useDelimiter("\n");
    }

    public void mainMenu() {
        System.out.println(
                """
                        Welcome!
                        1. Create user
                        2. Log In
                        3. Admin
                        Enter 4 to quit"""
        );
        int mainMenuChoice = scanner.nextInt();

        clearScreen();
        switch (mainMenuChoice) {
            case 1 -> {
                createUser();
            }
            case 2 -> {
                userAuthentication();
            }
            case 3 -> {
                adminMenu();
            }
            case 4 -> {
                System.out.println("Exiting the program...");
            }
        }
    }

    private void clientMenu(Client client) {
        System.out.printf("""
                Welcome %s %s!
                1. Borrow new books
                2. View active borrowings
                3. Return borrowed books
                4. View borrowing history
                Enter 5 to go back or 9 to quit
                """, client.getFirstName(), client.getLastName());
        int choice = scanner.nextInt();

        clearScreen();
        switch (choice) {
            case 1 -> {
                List<Book> booksToBeReserved = new ArrayList<>();
                Reservation reservation = new Reservation(StateOfReservation.PENDING, LocalDate.now(), client, booksToBeReserved);
                Service.save(reservation, entityManager);
                borrowNewBooks(client, reservation);
                clientMenu(client);
            }
            case 2 -> {
                viewActiveBorrowings();
                clientMenu(client);
            }
            case 3 -> {
                returnBooks();
            }
            case 4 -> {
                viewBorrowingHistory();
                clientMenu(client);
            }
            case 5 -> {
                mainMenu();
            }
            case 9 -> {
                System.out.println("Exiting the program...");
            }
        }
    }

    private void adminMenu() {
        // TODO - add option to MODIFY, DELETE books from the inventory
        System.out.println(
                """
                                Welcome admin!
                                1. Create book
                                2. Create author
                                3. Create genre
                                4. Edit
                                5. Delete
                                Enter 6 to go back or 9 to quit
                        """
        );
        int choice = scanner.nextInt();

        clearScreen();
        switch (choice) {
            case 1 -> {
                createBook();
            }
            case 2 -> {
                createAuthor();
                clearScreen();
                adminMenu();
            }
            case 3 -> {
                createGenre();
                clearScreen();
                adminMenu();
            }
            case 6 -> {
                mainMenu();
            }
            case 9 -> {
                System.out.println("Exiting the program...");
            }
        }
    }

    private void userAuthentication() {
        clearScreen();
        System.out.println("Please enter your username: ");
        String username = scanner.next();

        Client clientFound;
        try {
            clientFound = ClientManager.searchUsername(username, entityManager).get(0);
            System.out.println("Please enter your password: ");
            String typePassword = scanner.next();
            // once the user has been identified in the database it will ask for it's password
            if (clientFound.getPassword().equals(typePassword)) {
                // check the password found in the database (.getPassword()) with the one entered by the user in the terminal
                clearScreen();
                clientMenu(clientFound);
            }
        } catch (IndexOutOfBoundsException e) {
            clearScreen();
            System.out.println("User not found! Please create a new user.");
            mainMenu();
        }
    }

    private void borrowNewBooks(Client client, Reservation reservation) {
        // TODO - rethink and restructure method
        // TODO - treat invalid entries - example: when choosing a book and entering something invalid
        System.out.println("Search: ");
        String term = scanner.next();
        List<Book> booksFound = searchBooks(term);

        if (booksFound.isEmpty()) {
            clearScreen();
            System.out.println("Your search returned no results...");
            clientMenu(client);
        }
        System.out.println("Books found: ");
        for (int i = 0; i < booksFound.size(); i++) {
            Book book = booksFound.get(i);
            StringBuilder stringBuilder = new StringBuilder();
            book.getAuthorList().forEach(author -> stringBuilder.append(author.getLastName())
                    .append(" ")
                    .append(author.getFirstName()));
            System.out.printf("%d. %s, %s, %d (Located in: %s)\n",
                    (i + 1),
                    book.getTitle(),
                    stringBuilder,
                    book.getYearReleased(),
                    book.getLocation());
        }
        System.out.println("Please choose a book to borrow: ");
        int borrowChoice = scanner.nextInt();
        Book borrowedBook = booksFound.get(borrowChoice - 1);
        reservation.getBookList().add(borrowedBook);
        System.out.println("Do you want to borrow more books?");
        String moreBooksChoice = scanner.next();
        if (moreBooksChoice.equalsIgnoreCase("yes") || moreBooksChoice.equalsIgnoreCase("y")) {
            borrowNewBooks(client, reservation);
        }
        reservation.setDateOfReturn(reservation.getDateOfReservation().plusDays(Reservation.maxDaysLoaned));
        reservation.setState(StateOfReservation.ACTIVE);
        borrowedBook.setBookCount(borrowedBook.getBookCount() - 1);
        // UPDATE IN DB
        System.out.printf("Your reservation has the id: %d\n", reservation.getId());
        Service.update(reservation, entityManager);
    }

    private List<Book> searchBooks(String term) {
        List<Book> booksFound = new ArrayList<>();
        List<Author> foundAuthors = new ArrayList<>();
        List<Genre> foundGenre = new ArrayList<>();
        // Searching for books by:
        // title
        booksFound.addAll(BookManager.searchTitle(term, entityManager));
        // year of release
        try {
            booksFound.addAll(BookManager.searchYearOfRelease(Integer.valueOf(term), entityManager));
        } catch (Exception e) {

        }
        // location
        booksFound.addAll(BookManager.searchLocation(term, entityManager));

        // Searching for authors by:
        // attempt to search by complete name by splitting the searched term by space
        try {
            String name1 = term.split(" ")[0];
            String name2 = term.split(" ")[1];
            foundAuthors.addAll(AuthorsManager.searchName(name1, name2, entityManager));
            foundAuthors.addAll(AuthorsManager.searchName(name2, name1, entityManager));
        } catch (Exception e) {
            // if the term cannot be split into 2 or multiple an exception is thrown when trying to
            // access the second element in the list
            // last name
            foundAuthors.addAll(AuthorsManager.searchLastName(term, entityManager));
            // first name
            foundAuthors.addAll(AuthorsManager.searchFirstName(term, entityManager));
        }

        // Searching for genre by name:
        foundGenre.addAll(GenreManager.searchName(term, entityManager));

        // add all the books in the list of books for each author found
        foundAuthors.forEach(author -> booksFound.addAll(author.getBookList()));

        // add all the books in the list of books for each genre found
        foundGenre.forEach(genre -> booksFound.addAll(genre.getBookGenres()));

        return booksFound;
    }

    private void viewActiveBorrowings() {

    }

    private void viewBorrowingHistory() {
    }

    private void returnBooks() {
    }

    private void createUser() {
        // DONE: search if the username doesn't already exist when creating another one

        System.out.println("Please enter your last name: ");
        String lastName = scanner.next();

        System.out.println("Please enter your first name: ");
        String firstName = scanner.next();

        String username;
        boolean usernameExists = false;

        while (true) {
            if (usernameExists) System.err.println("Username already exists in the database.");

            System.out.println("Please enter a username: ");
            username = scanner.next();
            try {
                // It doesn't make sense to recheck for equality if it already found a Client based on the username entered
                Client client = ClientManager.searchUsername(username, entityManager).get(0);
                usernameExists = true;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }


        System.out.println("Please enter a password: ");
        String password = scanner.next();

        List<Reservation> clientReservations = new ArrayList<>();
        Client newClient = new Client(firstName, lastName, username, password, clientReservations);

        Service.save(newClient, entityManager);

        clearScreen();
        mainMenu();
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
            List<Author> foundAuthors = AuthorsManager.searchLastName(authorName, entityManager);
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
        List<Genre> foundGenre = GenreManager.searchName(genreName, entityManager);
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

        // DONE: if it exists add the book to author's list of books and update author list of books in the DataBase
        // DONE: if it exists add the book to genre's list of books and update genre list of books in the DataBase

        try {
            bookGenre.getBookGenres().add(newBook);
            Service.update(bookGenre, entityManager);
            author.getBookList().add(newBook);
            Service.update(author, entityManager);
        } catch (NullPointerException ignored) {
        }
        ;


        Service.save(newBook, entityManager);
        clearScreen();
        adminMenu();
    }

    private Author createAuthor() {
        // TODO - search if the Author doesn't already exist
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
        // TODO - search if the genre doesn't already exist
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
