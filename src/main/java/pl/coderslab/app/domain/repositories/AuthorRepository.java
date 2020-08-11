package pl.coderslab.app.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.app.domain.model.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);
}
