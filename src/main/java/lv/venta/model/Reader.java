package lv.venta.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Reader")
@Entity
public class Reader implements Serializable {
    private static final long serialVersionUID = 1L;

	@Setter(value = AccessLevel.NONE)
    @Column(name = "IdR")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idR;

    @NotNull
    @Pattern(regexp = "[A-Z][a-z]+")
    @Size(min = 2, max = 10)
    @Column(name = "Name")
    private String name;
    
    @NotNull
    @Pattern(regexp = "[A-Z][a-z]+")
    @Size(min = 2, max = 20)
    @Column(name = "Surname")
    private String surname;
    
    @ManyToOne
    @JoinColumn(name = "IdLD")
    private LibraryDepartment libraryDepartment;
    
    @ManyToMany
    @JoinTable(
        name = "ReaderCurrentBook",
        joinColumns = @JoinColumn(name = "IdR"),
        inverseJoinColumns = @JoinColumn(name = "IdB")
    )
    private Collection<Book> currentTakenBookList = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "ReaderBookHistory",
        joinColumns = @JoinColumn(name = "IdR"),
        inverseJoinColumns = @JoinColumn(name = "IdB")
    )
    private Collection<Book> bookHistory = new HashSet<>();
    
    public Reader(String name, String surname){
        setName(name);
        setSurname(surname);
    }
}
