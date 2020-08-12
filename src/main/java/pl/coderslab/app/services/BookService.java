package pl.coderslab.app.services;

import pl.coderslab.app.domain.model.Book;
import pl.coderslab.app.exceptions.InvalidIdException;
import pl.coderslab.app.exceptions.ValidationFailedException;

import java.util.List;

public interface BookService {
    List<Book> loadAll();

    Book loadById(long id) throws InvalidIdException;

    void save(Book book) throws InvalidIdException, ValidationFailedException;

    void delete(long id) throws InvalidIdException;
}
