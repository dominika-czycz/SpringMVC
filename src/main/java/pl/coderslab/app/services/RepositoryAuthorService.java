package pl.coderslab.app.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.app.domain.model.Author;
import pl.coderslab.app.domain.repositories.AuthorRepository;
import pl.coderslab.app.exceptions.InvalidIdException;
import pl.coderslab.app.exceptions.ValidationFailedException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RepositoryAuthorService implements AuthorService {
    private final AuthorRepository authorRepository;
    private final Validator validator;

    @Override
    public List<Author> loadAll() {
        log.info("Loading all authors...");
        return authorRepository.findAll();
    }

    @Override
    public Author loadById(long id) throws InvalidIdException {
        log.debug("Downloading book with id {}...", id);
        return authorRepository.findById(id)
                .orElseThrow(new InvalidIdException("Author with id " + id + " does not exist in database"));
    }

    @Override
    public void save(Author author) throws InvalidIdException, ValidationFailedException {
        log.debug("Entity from request: {}.", author);
        if (!isValid(author)) {
            throw new ValidationFailedException("Entity from request: " + author + " has failed validation!");
        }
        if (author.getId().equals(0L)) {
            saveNew(author);
        } else {
            update(author);
        }
    }

    private void saveNew(Author author) {
        log.debug("Saving entity {}...", author);
        final Author saved = authorRepository.save(author);
        log.debug("Entity {} has been saved.", saved);
    }

    private void update(Author author) throws InvalidIdException {
        log.debug("Entity from request: {}.", author);
        final Author toUpdate = loadById(author.getId());
        log.debug("Preparing entity {} to update...", toUpdate);
        toUpdate.setFirstName(author.getFirstName());
        toUpdate.setLastName(author.getLastName());
        log.debug("Updating entity {}...", toUpdate);
        final Author updated = authorRepository.save(toUpdate);
        log.debug("Entity {} has been updated.", updated);
    }

    private boolean isValid(Author toValid) {
        final Set<ConstraintViolation<Author>> violations = validator.validate(toValid);
        if (violations.size() > 0) {
            log.warn("Entity {} fails validation!", toValid);
            violations.forEach(v -> log.warn("Property path: {}; invalid value: {}; message: {}", v.getPropertyPath(), v.getInvalidValue(), v.getMessage()));
            return false;
        }
        return true;
    }

    @Override
    public void delete(long id) throws InvalidIdException {
        log.debug("Deleting author with id {}...", id);
        final Author author = loadById(id);
        authorRepository.delete(author);
        log.debug("Entity {} has been deleted.", author);
    }
}
