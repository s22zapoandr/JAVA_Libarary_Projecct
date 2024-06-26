package lv.venta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Author")
public class Author implements Serializable{

    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdA")
    private long idA;

    @NotNull
    @Pattern(regexp = "[A-Z]{1}[a-z]+")
    @Size(min = 2, max = 10)
    @Column(name = "Name")
    private String name;

    @NotNull
    @Pattern(regexp = "[A-Z]{1}[a-z]+")
    @Size(min = 2, max = 20)
    @Column(name = "Surname")
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name = "Genre")
    private Genre genre;

    @ManyToMany(mappedBy = "authors")
    private List<Book> writtenBooks = new ArrayList<>();

    public Author(String name, String surname, Genre genre) {
        this.name = name;
        this.surname = surname;
        this.genre = genre;
    }
}
