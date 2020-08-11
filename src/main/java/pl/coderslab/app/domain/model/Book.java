package pl.coderslab.app.domain.model;

import lombok.*;
import org.hibernate.validator.constraints.ISBN;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @ISBN
    @NotBlank
    private String isbn;
    @Column(nullable = false)
    @NotBlank
    @Size(min = 3, max = 255)
    private String title;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Author author;
    @Column(nullable = false, name = "author_name")
    @NotBlank
    @Size(min = 2, max = 255)
    private String authorName;
    @Column(nullable = false)
    @NotBlank
    @Size(max = 255)
    private String publisher;
    @Column(nullable = false)
    @NotBlank
    @Size(min = 2, max = 255)
    private String type;
}
