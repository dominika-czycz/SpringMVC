package pl.coderslab.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.app.domain.model.Author;
import pl.coderslab.app.exceptions.InvalidIdException;
import pl.coderslab.app.exceptions.ValidationFailedException;
import pl.coderslab.app.services.AuthorService;

import java.util.List;

@RestController
@RequestMapping("authors")
@Slf4j
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(@Qualifier("repositoryAuthorService") AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAllAuthor() {
        log.info("Loading all authors...");
        return authorService.loadAll();
    }

    @GetMapping("{id}")
    public Author getAuthor(@PathVariable long id) throws InvalidIdException {
        log.debug("Loading author with id {}...", id);
        return authorService.loadById(id);
    }

    @PostMapping
    public void addAuthor(@RequestBody Author author) throws InvalidIdException, ValidationFailedException {
        log.debug("Starting the saving procedure of entity {}...", author);
        authorService.save(author);
    }

    @PutMapping("{id}")
    public void editAuthor(@RequestBody Author author) throws InvalidIdException, ValidationFailedException {
        log.debug("Starting the editing procedure of entity {}...", author);
        authorService.save(author);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) throws InvalidIdException {
        log.debug("Starting the removing procedure of entity with id {}...", id);
        authorService.delete(id);
    }
}
