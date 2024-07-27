package org.library.management.process;

import org.library.management.model.Book;
import org.library.management.model.Patron;
import org.library.management.service.LibraryService;
import org.library.management.service.LibraryServiceImpl;

import java.util.*;
import java.util.logging.Logger;

public class LendingProcess {

    private Map<Integer, List<String>> borrowedBooksByPatron = new HashMap<>();
    private LibraryService libraryService = LibraryServiceImpl.getInstance();
    private static final Logger logger = Logger.getLogger(LendingProcess.class.getName());


    public boolean checkoutBook(String ISBN, int patronId) {
        Book book = libraryService.searchBooksByISBN(ISBN);
        if (book != null) {
            Patron patron = libraryService.getPatrons().stream()
                    .filter(p -> p.getId() == patronId)
                    .findFirst()
                    .orElse(null);
            if (patron != null) {
                borrowedBooksByPatron.computeIfAbsent(patronId, k -> new ArrayList<>()).add(ISBN);
                logger.info("Book with ISBN: {} checked out by patron with ID: {}" + ISBN + patronId);
                return true;
            } else {
                logger.info("Patron with ID: {} not found." + patronId);
            }
        } else {
            logger.info("Book with ISBN: {} not found." + ISBN);
        }
        return false;
    }

    public boolean returnBook(String ISBN, int patronId) {
        Patron patron = libraryService.getPatrons().stream()
                .filter(p -> p.getId() == patronId)
                .findFirst()
                .orElse(null);
        if (patron != null) {
            List<String> borrowedBooks = borrowedBooksByPatron.get(patronId);
            if (borrowedBooks != null && borrowedBooks.contains(ISBN)) {
                Book book = libraryService.searchBooksByISBN(ISBN);;
                borrowedBooks.remove(ISBN);
                if (borrowedBooks.isEmpty()) {
                    borrowedBooksByPatron.remove(patronId);
                }
                logger.info("Book with ISBN: {} returned by patron with ID: {}" + ISBN + patronId);
                return true;
            } else {
                logger.info("Patron with ID: {} not found." + patronId);
            }
        } else {
            logger.info("Book with ISBN: {} not found." + ISBN);
        }
        return false;
    }

    public List<Book> getBorrowedBooks() {
        List<Book> borrowedBooks = new ArrayList<>();
        for (List<String> isbns : borrowedBooksByPatron.values()) {
            for (String isbn : isbns) {
                Book book = libraryService.searchBooksByISBN(isbn);
                if (book != null) {
                    borrowedBooks.add(book);
                } else {
                    // If the book is not found in the library inventory, create a dummy book object
                    borrowedBooks.add(new Book("sample book", "sample Author", isbn, 0));
                }
            }
        }
        return borrowedBooks;
    }

}
