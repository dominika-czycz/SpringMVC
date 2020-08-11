package pl.coderslab.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.app.domain.model.Book;
import pl.coderslab.app.domain.services.BookService;
import pl.coderslab.app.exceptions.InvalidIdException;


import java.util.List;

@RestController
@RequestMapping("books")
@Slf4j
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(@Qualifier("repositoryBookService") BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBook() {
        log.info("Getting all books...");
        return bookService.loadAll();
    }

    @GetMapping("{id}")
    public Book getBook(@PathVariable long id) throws InvalidIdException {
        log.debug("Getting book with id {}...", id);
        return bookService.loadById(id);
    }

    @PostMapping
    public void addBook(@RequestBody Book book) throws InvalidIdException {
        log.debug("Starting the saving procedure of entity {}...", book);
        bookService.save(book);
    }

    @PutMapping("{id}")
    public void editBook(@RequestBody Book book) throws InvalidIdException {
        log.debug("Starting the editing procedure of entity {}...", book);
        bookService.save(book);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) throws InvalidIdException {
        log.debug("Starting the removing procedure of entity with id {}...", id);
        bookService.delete(id);
    }
}
