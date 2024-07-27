package org.library.management;

import org.library.management.management.InventoryManagement;
import org.library.management.model.Book;
import org.library.management.model.Patron;
import org.library.management.process.LendingProcess;
import org.library.management.service.LibraryService;
import org.library.management.service.LibraryServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Obtain the singleton instance of LibraryService
        LibraryService libraryService = LibraryServiceImpl.getInstance();

        LendingProcess lendingProcess = new LendingProcess();
        InventoryManagement inventoryManagement = new InventoryManagement();

        // Adding books
        libraryService.addBook(new Book("Design Patterns", "Erich Gamma", "1234567876", 1995));
        libraryService.addBook(new Book("Methods of Software Engineering", "Cay S. Horstmann", "1233456789", 2000));

        // Adding patrons
        libraryService.addPatron(new Patron("Ajay Kumar", 1));
        libraryService.addPatron(new Patron("suresh", 2));

        // Checking out a book
        boolean checkoutSuccess = lendingProcess.checkoutBook("1234567876", 1);
        if (checkoutSuccess) {
            System.out.println("Book checked out successfully.");
        } else {
            System.out.println("Failed to check out the book.");
        }

        // Returning the book
        boolean returnSuccess = lendingProcess.returnBook("1234567876", 1);
        if (returnSuccess) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Failed to return the book.");
        }

        // Display available books
        List<Book> availableBooks = inventoryManagement.getAvailableBooks();
        System.out.println("Available books:");
        for (Book book : availableBooks) {
            System.out.println(book.getTitle() + " by " + book.getAuthor());
        }

        // Display borrowed books
        List<Book> borrowedBooks = inventoryManagement.getBorrowedBooks();
        System.out.println("Borrowed books:");
        for (Book book : borrowedBooks) {
            System.out.println(book.getTitle() + " by " + book.getAuthor());
        }
    }
}
