package pl.coderslab.app.domain.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.app.domain.model.Author;
import pl.coderslab.app.domain.model.Book;
import pl.coderslab.app.domain.repositories.AuthorRepository;
import pl.coderslab.app.domain.repositories.BookRepository;
import pl.coderslab.app.exceptions.InvalidIdException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RepositoryBookService implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

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
    public void save(Book book) throws InvalidIdException {
        log.debug("Entity from request {}.", book);
        if (book.getId() == null || book.getId().equals(0L)) {
            saveNewBook(book);
        } else {
            updateBook(book);
        }
    }

    private void updateBook(Book book) throws InvalidIdException {
        log.debug("Entity {} from request.", book);
        final Book toUpdate = loadById(book.getId());
        log.debug("Preparing to update entity {}...", toUpdate);
        final String authorName = book.getAuthorName();
        final Author author = getExistingAuthor(authorName);
        toUpdate.setTitle(book.getTitle());
        toUpdate.setIsbn(book.getIsbn());
        toUpdate.setPublisher(book.getIsbn());
        toUpdate.setType(book.getType());
        toUpdate.setAuthorName(authorName);
        toUpdate.setAuthor(author);
        log.debug("Updating entity {}...", toUpdate);
        final Book updated = bookRepository.save(toUpdate);
        log.debug("Entity {} has been updated.", updated);
    }

    private Author getExistingAuthor(String authorName) {
        final String[] names = authorName.split(" ");
        final String firstName = names[0];
        final String lastName = names[names.length - 1];
        final Optional<Author> author = authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName);
        if (author.isEmpty()) {
            log.debug("Author {} {} does not exist in database. Preparing to save... ", firstName, lastName);
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

    private void saveNewBook(Book book) {
        log.debug("Preparing to save entity {}...", book);
        final String authorName = book.getAuthorName();
        final Author author = getExistingAuthor(authorName);
        book.setAuthor(author);
        log.debug("Saving entity {}...", book);
        final Book saved = bookRepository.save(book);
        log.debug("Entity {} has been saved.", saved);
    }

    @Override
    public void delete(long id) throws InvalidIdException {
        log.debug("Deleting book with id {}...", id);
        final Book book = loadById(id);
        bookRepository.delete(book);
        log.debug("Entity {} has been deleted.", book);
    }
}
