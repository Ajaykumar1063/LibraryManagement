package org.library.management.management;

import org.library.management.model.Book;
import org.library.management.process.LendingProcess;
import org.library.management.service.LibraryService;
import org.library.management.service.LibraryServiceImpl;

import java.util.List;
import java.util.logging.Logger;

public class InventoryManagement {
    private LibraryService libraryService= LibraryServiceImpl.getInstance();
    private LendingProcess lendingProcess = new LendingProcess();
    private static final Logger logger = Logger.getLogger(InventoryManagement.class.getName());

    public List<Book> getAvailableBooks() {
        logger.info("available books in inventory: ");
        return libraryService.getBooks();
    }

    public List<Book> getBorrowedBooks() {
        logger.info("borrowed books in inventory: ");
        return lendingProcess.getBorrowedBooks();
    }
}
