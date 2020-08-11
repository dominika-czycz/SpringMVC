package pl.coderslab.app.domain.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.app.domain.model.Book;
import pl.coderslab.app.exceptions.InvalidIdException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

@Service
@Slf4j
public class MemoryBookService implements BookService {
    private final List<Book> list;

    public MemoryBookService() {
        list = new ArrayList<>();
        list.add(Book.builder().id(1L).isbn("9788324631766").title("Thinking in Java").authorName("Bruce Eckel")
                .publisher("Helion").type("programming").build());
        list.add(Book.builder().id(2L).isbn("9788324627738").title("Rusz głową, Java.").authorName("Sierra Kathy")
                .publisher("Helion").type("programming").build());
        list.add(Book.builder().id(3L).isbn("9780130819338").title("Java 2. Podstawy").authorName("Cay Horstmann")
                .publisher("Helion").type("programming").build());
    }

    @Override
    public List<Book> loadAll() {
        return list;
    }

    @Override
    public Book loadById(long id) throws InvalidIdException {
        return list.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst().orElseThrow(() -> new InvalidIdException("Invalid id"));
    }

    private void saveNew(Book book) {
        list.add(book);
    }

    @Override
    public void save(Book book) {
        if (book.getId() == null) {
            book.setId(0L);
        }
        final Optional<Book> bookToUpdate = list.stream()
                .filter(v -> v.getId().equals(book.getId()))
                .findFirst();
        if (bookToUpdate.isPresent()) {
            list.remove(bookToUpdate.get());
            list.add(book);
        } else {
            final OptionalLong max = list.stream().mapToLong(Book::getId).max();
            if (max.isPresent()) {
                book.setId(max.getAsLong() + 1);
            }
            saveNew(book);
        }
    }

    @Override
    public void delete(long id) {
        final Optional<Book> bookToDelete = list.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
        bookToDelete.ifPresent(list::remove);
    }
}
