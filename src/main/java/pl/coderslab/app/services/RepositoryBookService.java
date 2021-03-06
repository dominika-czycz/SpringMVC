package pl.coderslab.app.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.app.domain.model.Author;
import pl.coderslab.app.domain.model.Book;
import pl.coderslab.app.domain.repositories.AuthorRepository;
import pl.coderslab.app.domain.repositories.BookRepository;
import pl.coderslab.app.exceptions.InvalidIdException;
import pl.coderslab.app.exceptions.ValidationFailedException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RepositoryBookService implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final Validator validator;

    @Override
    public List<Book> loadAll() {
        log.info("Loading all books...");
        return bookRepository.findAll();
    }

    @Override
    public Book loadById(long id) throws InvalidIdException {
        log.debug("Downloading book with id {}...", id);
        return bookRepository.findById(id)
                .orElseThrow(new InvalidIdException("Book with id " + id + " does not exist in database"));
    }

    @Override
    public void save(Book book) throws InvalidIdException, ValidationFailedException {
        log.debug("Entity from request: {}.", book);
        if (!isValid(book)) {
            throw new ValidationFailedException("Entity from request: " + book + " has failed validation!");
        }
        if (book.getId().equals(0L)) {
            saveNewBook(book);
        } else {
            updateBook(book);
        }
    }

    private boolean isValid(Book toValid) {
        final Set<ConstraintViolation<Book>> violations = validator.validate(toValid);
        if (violations.size() > 0) {
            log.warn("Entity {} fails validation!", toValid);
            violations.forEach(v -> log.warn("Property path: {}, invalid value: {}, message: {}", v.getPropertyPath(), v.getInvalidValue(), v.getMessage()));
            return false;
        }
        return true;
    }

    private void updateBook(Book book) throws InvalidIdException {
        log.debug("Entity {} from request.", book);
        final Book toUpdate = loadById(book.getId());
        log.debug("Preparing entity {} to update...", toUpdate);
        final Author author = getAuthor(book);
        toUpdate.setTitle(book.getTitle());
        toUpdate.setIsbn(book.getIsbn());
        toUpdate.setPublisher(book.getIsbn());
        toUpdate.setType(book.getType());
        toUpdate.setAuthorName(author.getFullName());
        toUpdate.setAuthor(author);
        log.debug("Updating entity {}...", toUpdate);
        final Book updated = bookRepository.save(toUpdate);
        log.debug("Entity {} has been updated.", updated);
    }

    /**
     * This method checks if the Book class object contains author field with id. If so it returns author record from database
     * searching for result by the author's id. Otherwise it searches by author full name. If the author's record
     * does not yet exist, it will be created.
     *
     * @param book - object from request
     * @return author already existing in the database or newly created and saved in the database
     * @throws InvalidIdException if the book already has author with id, but the author's id does not exist in database
     */
    private Author getAuthor(Book book) throws InvalidIdException {
        if (book.getAuthor() == null || book.getAuthor().getId() == null || book.getAuthor().getId().equals(0L)) {
            return getFromDbPersistIfRequired(book.getAuthorName());
        }
        final Long authorId = book.getAuthor().getId();
        return authorRepository.findById(authorId)
                .orElseThrow(new InvalidIdException("Author with id " + authorId + " does not exist in database"));
    }

    private Author getFromDbPersistIfRequired(String authorName) {
        final String[] names = authorName.split(" ");
        final String firstName = names[0];
        final String lastName = names[names.length - 1];
        final Optional<Author> author = authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName);
        if (author.isEmpty()) {
            log.debug("Author {} {} does not exist in database. Preparing to save new author... ", firstName, lastName);
            return saveAuthor(firstName, lastName);
        } else {
            log.debug("Entity {} from database.", author.get());
            return author.get();
        }
    }

    private Author saveAuthor(String firstName, String lastName) {
        log.debug("Saving new author {} {}...", firstName, lastName);
        final Author saved = authorRepository.save(
                Author.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .build());
        log.debug("Entity {} has been saved.", saved);
        return saved;
    }

    private void saveNewBook(Book book) throws InvalidIdException {
        log.debug("Preparing to save entity {}...", book);
        final Author author = getAuthor(book);
        book.setAuthor(author);
        book.setAuthorName(author.getFullName());
        log.debug("Saving entity {}...", book);
        final Book saved = bookRepository.save(book);
        log.debug("Entity {} has been saved.", saved);
    }

    @Override
    public void delete(long id) throws InvalidIdException {
        log.debug("Preparing book with id {} to delete...", id);
        final Book book = loadById(id);
        log.debug("Deleting book {}...", book);
        bookRepository.delete(book);
        log.debug("Entity {} has been deleted.", book);
    }
}
