package lv.venta.model;

import java.util.Collection;
import java.util.HashSet;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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
    private int workingHoursStart; 

    @NotNull
    @Column(name = "WorkingHoursEnd")
    private int workingHoursEnd; 

    @OneToMany(mappedBy = "libraryDepartment")
    private Collection<Reader> readers = new HashSet<>(); 
    
    @OneToMany(mappedBy = "libraryDepartment")
    private Collection<Book> bookList = new HashSet<>();

    @OneToMany(mappedBy = "libraryDepartment")
    private Collection<Book> bookQueueForFutureCheckout = new HashSet<>();

    // Override
    public void setWorkingHoursStart() {
        this.workingHoursStart = 9;
    }
    
    public void setWorkingHoursEnd() {
        this.workingHoursEnd = 17;
    }
    
    public LibraryDepartment(Genre specialization) {
        setWorkingHoursStart();
        setWorkingHoursEnd();
        this.specialization = specialization;
    }
}
