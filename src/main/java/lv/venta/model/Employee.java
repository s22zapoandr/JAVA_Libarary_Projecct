package lv.venta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Employee")
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdEmp")
    private long idEmp;

    @NotNull
    @Column(name = "Name")
    private String Name;

    @NotNull
    @Column(name = "Surname")
    private String Surname;
    
    @NotNull
    @Column(name = "Specialization")
    private Specialization slaves;
    
    @ManyToOne
    @JoinColumn(name = "IdLD", nullable = false)
    private LibraryDepartment libraryDepartment;
}
