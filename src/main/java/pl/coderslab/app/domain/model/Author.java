package pl.coderslab.app.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "first_name")
    @NotBlank
    @Size(max = 255)
    private String firstName;
    @Column(nullable = false, name = "last_name")
    @NotBlank
    @Size(max = 255)
    private String lastName;
    @Transient
    private String fullName;

    public String getFullName() {
        return this.firstName + " " + lastName;
    }
}
