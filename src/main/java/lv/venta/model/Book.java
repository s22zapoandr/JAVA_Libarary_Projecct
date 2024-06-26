package lv.venta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "BookList")
public class Book implements Serializable{

    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdB")
    private long idb;

    @NotNull
    @Pattern(regexp = "[A-Za-z ]+")
    @Size(min = 4, max = 50)
    @Column(name = "Title")
    private String title;

    @Min(1)
    @Max(10)
    @Column(name = "Rating")
    private int rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "Condition1")
    private Condition condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "Rarity")
    private Rarity rarity;

    @Min(1)
    @Max(15)
    @Column(name = "Quantity")
    private int quantity;

    @Min(0)
    @Max(2024)
    @Column(name = "Year")
    private int year;

    @ManyToMany
    @JoinTable(
        name = "Book_Author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @ManyToOne
    @JoinColumn(name = "library_department_id")
    private LibraryDepartment libraryDepartment;

    // Constructor
    public Book(String title, List<Author> authors, int rating, Condition condition, Rarity rarity, int quantity, int year) {
        this.title = title;
        this.authors = authors;
        this.rating = rating;
        this.condition = condition;
        this.rarity = rarity;
        this.quantity = quantity;
        this.year = year;
    }
}
