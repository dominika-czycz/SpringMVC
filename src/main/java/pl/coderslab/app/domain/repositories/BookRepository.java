package pl.coderslab.app.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.app.domain.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
