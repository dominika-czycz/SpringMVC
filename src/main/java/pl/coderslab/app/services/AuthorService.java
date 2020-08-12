package pl.coderslab.app.services;

import pl.coderslab.app.domain.model.Author;
import pl.coderslab.app.exceptions.InvalidIdException;
import pl.coderslab.app.exceptions.ValidationFailedException;

import java.util.List;

public interface AuthorService {
    List<Author> loadAll();

    Author loadById(long id) throws InvalidIdException;

    void save(Author author) throws InvalidIdException, ValidationFailedException;

    void delete(long id) throws InvalidIdException;
}
