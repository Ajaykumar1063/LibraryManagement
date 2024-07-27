package org.library.management.service;

import org.library.management.model.Book;
import org.library.management.model.Patron;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LibraryServiceImpl implements LibraryService {

    private static LibraryService instance;
    private List<Book> books;
    private List<Patron> patrons;
    private static final Logger logger = Logger.getLogger(LibraryServiceImpl.class.getName());

    private LibraryServiceImpl() {
        this.books = new ArrayList<>();
        this.patrons = new ArrayList<>();
    }

    //Singleton
    public static synchronized LibraryService getInstance() {
        if (instance == null) {
            instance = new LibraryServiceImpl();
        }
        return instance;
    }

    @Override
    public void addBook(Book book){
            books.add(book);
            logger.info("Added book: {}" + book.getTitle());
    }

    @Override
    public void removeBook(String ISBN)  {
        boolean removed = books.removeIf(book -> book.getISBN().equals(ISBN));
        if (removed) {
            logger.info("Removed book with ISBN: {}"+ ISBN);
        } else {
            logger.info("Failed to remove book with ISBN: {}"+ ISBN);
        }
    }

    @Override
    public void updateBook(String ISBN, Book updatedBook)  {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getISBN().equals(ISBN)) {
                books.set(i, updatedBook);
                logger.info("Updated book with ISBN: {}"+ ISBN);
                return;
            }
        }
        logger.info("Failed to update book with ISBN: {}"+ ISBN);
    }

    @Override
    public void addPatron(Patron patron) {
        patrons.add(patron);
        logger.info("Added patron: {}"+ patron.getName());
    }

    @Override
    public void updatePatron(int id, Patron updatedPatron) {
        for (int i = 0; i < patrons.size(); i++) {
            if (patrons.get(i).getId() == id) {
                patrons.set(i, updatedPatron);
                logger.info("Updated patron with ID: {}" + id);
                return;
            }
        }
        logger.info("Failed to update patron with ID: {}"+ id);
    }

    @Override
    public List<Book> getBooks() {
        return books;
    }

    @Override
    public List<Patron> getPatrons() {
        return patrons;
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        logger.info("search the list of books with given title: {}"+ title);
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchBooksByAuthor(String author) {
        logger.info("search the list of books with given author: {}"+ author);
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    @Override
    public Book searchBooksByISBN(String ISBN) {
        logger.info("search the book with given ISBN: {}"+ ISBN);
        return books.stream()
                .filter(book -> book.getISBN().equals(ISBN))
                .findFirst()
                .orElse(null);
    }




}