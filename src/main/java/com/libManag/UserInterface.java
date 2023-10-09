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
        clearScreen();
        System.out.println(
                """
                        Welcome!
                        ---------------
                        1. Create user
                        2. Log In
                        3. Admin
                        ---------------
                        Enter Q to quit"""
        );
        System.out.print("-> ");
        String mainMenuChoice = scanner.next();

        clearScreen();
        switch (mainMenuChoice) {
            case "1" -> createUser();
            case "2" -> userAuthentication();
            case "3" -> adminMenu();
            case "Q" -> System.out.println("Exiting the program...");
            default -> mainMenu();
        }
    }
    private void createUser() {
        // DONE: search if the username doesn't already exist when creating another one

        System.out.println("Please enter your last name: ");
        System.out.print("-> ");
        String lastName = scanner.next();

        System.out.println("Please enter your first name: ");
        System.out.print("-> ");
        String firstName = scanner.next();

        String username;
        boolean usernameExists = false;

        while (true) {
            if (usernameExists) System.err.println("Username already exists in the database.");

            System.out.println("Please enter a username: ");
            System.out.print("-> ");
            username = scanner.next();
            try {
                // It doesn't make sense to recheck for equality if it already found a Client based on the username entered
                ClientManager.searchUsername(username, entityManager).get(0);
                usernameExists = true;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }


        System.out.println("Please enter a password: ");
        System.out.print("-> ");
        String password = scanner.next();

        List<Reservation> clientReservations = new ArrayList<>();
        Client newClient = new Client(firstName, lastName, username, password, clientReservations);

        Service.save(newClient, entityManager);

        clearScreen();
        mainMenu();
    }
    private void userAuthentication() {
        clearScreen();
        System.out.println("Please enter your username: ");
        System.out.print("-> ");
        String username = scanner.next();

        Client clientFound;
        try {
            clientFound = ClientManager.searchUsername(username, entityManager).get(0);
            System.out.println("Please enter your password: ");
            System.out.print("-> ");
            String typePassword = scanner.next();
            // once the user has been identified in the database it will ask for it's password
            if (clientFound.getPassword().equals(typePassword)) {
                // check the password found in the database (.getPassword()) with the one entered by the user in the terminal
                clearScreen();
                clientMenu(clientFound);
            }
            clearScreen();
            System.err.println("Entered password was not correct!");
            mainMenu();
        } catch (IndexOutOfBoundsException e) {
            clearScreen();
            System.out.println("User not found! Please create a new user.");
            mainMenu();
        }
    }
    private void clientMenu(Client client) {
        ClientSession clientSession = ClientSession.ClientSession();

        System.out.printf("""
                        Welcome %s %s!
                        -------------------------------
                        1. Search for books
                        2. View active borrowings
                        3. Return borrowed books
                        4. View borrowing history
                        5. Cart (%d)
                        6. Notifications (%d)
                        -------------------------------
                        Enter B to go back or Q to quit
                        """,
                client.getFirstName(),
                client.getLastName(),
                clientSession.getCartList().size(),
                clientSession.getNotifications().size()
        );
        System.out.print("-> ");
        String choice = scanner.next();
        clearScreen();
        switch (choice) {
            case "1" -> {
                List<Book> foundBooks = findBooks();
                if (foundBooks == null) {
                    System.out.println("No books have been found matching your criteria...");
                    clientMenu(client);
                }
                searchBooksMenu(client, clientSession, foundBooks);
            }
            case "2" -> {
                viewBorrowings(client, true);
                System.out.println("\nPress any key to go back to menu.");
                scanner.next();
                clearScreen();
                clientMenu(client);
            }
            case "3" -> returnBooks();
            case "4" -> {
                viewBorrowings(client, false);
                System.out.println("\nPress any key to go back to menu.");
                scanner.next();
                clearScreen();
                clientMenu(client);
            }
            case "5" -> cartMenu(client, clientSession);
            case "6" -> notificationMenu(client);
            case "B","b" -> {
                clientSession.resetSession();
                mainMenu();
            }
            case "Q","q" -> System.out.println("Exiting the program...");
            default -> {
                System.out.println("Invalid choice. Please choose again: ");
                clientMenu(client);
            }
        }
    }
    private void searchBooksMenu(Client client, ClientSession clientSession, List<Book> foundBooks) {
        showBooks(foundBooks);

        System.out.println("Select a book or type B to go back to menu: ");
        String bookChoice = scanner.next();

        int bookIndex = 0;
        try {
            bookIndex = Integer.parseInt(bookChoice) - 1;
        } catch (Exception e) {
            clearScreen();
            clientMenu(client);
        }

        Book chosenBook = foundBooks.get(bookIndex);

        clearScreen();
        bookMenu(chosenBook,client,clientSession,foundBooks);
    }
    private void bookMenu(Book chosenBook, Client client, ClientSession clientSession, List<Book> foundBooks) {
        System.out.printf("""
                        "%s", %d
                        ------------------
                        1. Borrow book
                        2. Back to results
                        3. Back to menu
                        ------------------
                        """
                , chosenBook.getTitle()
                , chosenBook.getYearReleased());
        int bookMenuChoice = scanner.nextInt();
        clearScreen();
        switch (bookMenuChoice) {
            case 1 -> {
                clientSession.getCartList().add(chosenBook);
                clientMenu(client);
            }
            case 2 -> searchBooksMenu(client, clientSession, foundBooks);
            case 3 -> clientMenu(client);
            default -> {
                System.out.println("Invalid choice...");
                bookMenu(chosenBook,client,clientSession,foundBooks);
            }
        }
    }
    private void viewBorrowings(Client client, boolean active) {
        List<Reservation> clientReservations = client.getClientReservations();
        int count = 1;
        if (active) {
            for (Reservation reservation : clientReservations) {
                if (reservation.getState() == StateOfReservation.ACTIVE) {
                    System.out.println(
                            count + ". Reservation (id: " + reservation.getId() + "): " + reservation.getBookList().size() +
                                    " book(s), date borrowed: " + reservation.getDateOfReservation());
                    reservation.getBookList().forEach(book -> System.out.printf("\t" + book.getTitle() + "\n"));
                    count ++;
                }
            }
        } else {
            for (Reservation reservation : clientReservations) {
                System.out.println(
                        count + ". Reservation (id: " + reservation.getId() + "): " +
                                reservation.getBookList().size() +
                                " book(s), date borrowed: " + reservation.getDateOfReservation() +
                                (reservation.getState().equals(StateOfReservation.CLOSED) ?
                                        (", date returned: " + reservation.getDateOfReturn()) : ""));
                reservation.getBookList().forEach(book -> System.out.printf("\t" + book.getTitle() + "\n"));
                count ++;
            }
        }
    }
    private void returnBooks() {

    }
    private void cartMenu(Client client, ClientSession clientSession) {
        int cartBookIndex = 1;
        for(Book book: clientSession.getCartList()) {
            System.out.println(cartBookIndex + ". " + book.getTitle());
        }

        System.out.println("""
                -------------------------------
                1. Add books
                2. Remove books
                3. Confirm booking
                -------------------------------
                Enter B to go back or Q to quit
                """);
        System.out.print("-> ");
        String choice = scanner.next();
        clearScreen();
        switch (choice) {
            case "1" -> {
                List<Book> foundBooks = findBooks();
                if (foundBooks == null) {
                    System.out.println("No books have been found matching your criteria...");
                    cartMenu(client, clientSession);
                }
                searchBooksMenu(client, clientSession, foundBooks);
            }
            case "2" -> {
                cartBookIndex = 1;
                for(Book book: clientSession.getCartList()) {
                    System.out.println(cartBookIndex + ". " + book.getTitle());
                    cartBookIndex ++;
                }
                System.out.print("Choose book to remove from cart: ");
                int removeBook = scanner.nextInt();
                clientSession.getCartList().remove(clientSession.getCartList().get(removeBook-1));
                cartMenu(client, clientSession);
            }
            case "3" -> {
                LocalDate dateNow = LocalDate.now();
                Reservation reservation = new Reservation(StateOfReservation.ACTIVE,
                        dateNow,
                        client,
                        clientSession.getCartList()
                        );
                Service.save(reservation,entityManager);

                client.getClientReservations().add(reservation);
                Service.update(client, entityManager);

                for (Book book : clientSession.getCartList()) {
                    book.setBookCount(book.getBookCount()-1);
                    Service.update(book, entityManager);
                }
                clientSession.resetSession();
                System.out.println("Your reservation with the ID: " + reservation.getId() + " has been processed.");
                clientMenu(client);
            }
            case "B" -> clientMenu(client);
            case "Q" -> System.out.println("Exiting the program...");
            default -> cartMenu(client, clientSession);
        }
    }
    private void notificationMenu(Client client) {
        System.out.println("No new notifications...");
        clientMenu(client);
    }
    private void adminMenu() {
        // TODO - add option to MODIFY, DELETE books from the inventory
        System.out.println("""
                                Welcome admin!
                                -------------------------------
                                1. Create ...
                                2. Edit - NOT AVAILABLE
                                3. Delete - NOT AVAILABLE
                                -------------------------------
                                Enter B to go back or Q to quit""");
        String choice = scanner.next();

        clearScreen();
        switch (choice) {
            case "1" -> createMenu();
            case "2" -> {
                editMenu();
                adminMenu();
            }
            case "3" -> {
                deleteMenu();
                adminMenu();
            }
            case "B", "b" -> mainMenu();
            case "Q", "q" -> System.out.println("Exiting the program...");
        }
    }
    private void createMenu() {
        System.out.println("""
                                Welcome admin!
                                -------------------------------
                                1. Create book
                                2. Create author
                                3. Create genre
                                -------------------------------
                                Enter B to go back or Q to quit""");
        String choice = scanner.next();

        clearScreen();
        switch (choice) {
            case "1" -> createBook();
            case "2" -> {
                createAuthor();
                clearScreen();
                adminMenu();
            }
            case "3" -> {
                createGenre();
                clearScreen();
                adminMenu();
            }
            case "6" -> mainMenu();
            case "9" -> System.out.println("Exiting the program...");
        }
    }
    private void editMenu() {
        System.err.println("Not yet available...");
    }
    private void deleteMenu() {
        System.err.println("Not yet available...");
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
    private List<Book> findBooks() {
        System.out.println("Search: ");
        String term = scanner.next();
        List<Book> booksFound = searchMatchingBooks(term);

        if (!booksFound.isEmpty()) {
            return booksFound;
        }

        clearScreen();
        System.out.println("Your search returned no results...");
        return null;
    }
    private void showBooks(List<Book> booksFound) {
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
    }
    private List<Book> searchMatchingBooks(String term) {
        // add books to a list , from searching books by their properties
        // title
        List<Book> booksFound = new ArrayList<>(BookManager.searchTitle(term, entityManager));
        // location
        booksFound.addAll(BookManager.searchLocation(term, entityManager));
        // year of release
        try {
            booksFound.addAll(BookManager.searchYearOfRelease(Integer.parseInt(term), entityManager));
        } catch (Exception ignored) {

        }

        // search for an author name/last name/ first name and add all books from found authors to the list
        List<Author> foundAuthors = new ArrayList<>();
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
        // add all the books in the booksFound list for each author found
        foundAuthors.forEach(author -> booksFound.addAll(author.getBookList()));

        // search for a genre name - add books from that genre to the booksFound list
        GenreManager.searchName(term, entityManager).forEach(genre -> booksFound.addAll(genre.getBookGenres()));

        return booksFound;
    }
    private void clearScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}
