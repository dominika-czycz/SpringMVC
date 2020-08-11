package pl.coderslab.app.domain.model;

import lombok.*;

import javax.persistence.*;

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
    private String firstName;
    @Column(nullable = false, name = "last_name")
    private String lastName;
    @Transient
    private String fullName;

    public String getFullName() {
        return this.firstName + " " + lastName;
    }
}
