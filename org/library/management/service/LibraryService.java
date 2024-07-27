package org.library.management.service;

import org.library.management.model.Book;
import org.library.management.model.Patron;

import java.util.List;

public interface LibraryService {

    void addBook(Book book) ;
    void removeBook(String ISBN) ;
    void updateBook(String ISBN, Book updatedBook) ;
    List<Book> getBooks();
    List<Patron> getPatrons();
    List<Book> searchBooksByTitle(String title);
    List<Book> searchBooksByAuthor(String author);
    Book searchBooksByISBN(String ISBN);
    void addPatron(Patron patron);
    void updatePatron(int id, Patron updatedPatron);
}
