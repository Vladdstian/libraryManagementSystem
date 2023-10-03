# Library Network Management System

## Brief Description
A management system for a library network that allows users to borrow, return, and reserve books.

## Core Features

### 1. Inventory Management of Books
- **Book Information**: Storing data such as title, author, publication year, genre, and location in the library.
- **Inventory Update**: When books are borrowed or returned.
- **Availability Tracking**: Based on borrowing status.

### 2. Reader Management
- **Reader Details**: Storing information like name, contact, and borrowing history.
- **Authentication**: Allowing readers to create accounts and sign in.
- **Verification**: At the time of borrowing.

### 3. Borrowing Process
- **Search**: Allowing readers to search for books by title, author, and other criteria.
- **Listing Books**: Displaying available books based on search criteria.
- **Borrowing Process**: Selecting, calculating duration, penalties for delay.
- **Confirmation**: Providing a unique borrowing ID.
- **Inventory Update**: Marking the book as borrowed.

### 4. Borrowing Management
- **View Borrowings**: Allowing readers to view or modify their borrowings.
- **Conflict Management**: In case a book is already reserved or borrowed.
- **Notifications**: Sending messages to readers about the status of their borrowings.

#### Database Schema:
![db Schema](https://github.com/Vladdstian/libraryManagementSystem/blob/main/db_schema.png?raw=true
)

Read this in other languages: [Romanian](README-ro.md)