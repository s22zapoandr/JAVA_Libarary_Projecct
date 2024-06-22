package lv.venta.model;

import java.util.ArrayList;
import java.util.Collection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "LibraryDepartment")
@Entity
public class LibraryDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdLD")
    private long idLD;

    @NotNull
    @Column(name = "Specialization")
    private Genre specialization;

    @NotNull
    @Column(name = "WorkingHoursStart")
    private int workingHoursStart = 9; // Default start hour

    @NotNull
    @Column(name = "WorkingHoursEnd")
    private int workingHoursEnd = 17; // Default end hour

    @OneToMany(mappedBy = "libraryDepartment")
    private Collection<Book> bookList = new ArrayList<>();

    @OneToMany(mappedBy = "libraryDepartment")
    private Collection<Book> bookQueueForFutureCheckout = new ArrayList<>();

    public LibraryDepartment(Genre specialization) {
        this.specialization = specialization;
        this.workingHoursStart = 9;
        this.workingHoursEnd = 17;
    }
}
