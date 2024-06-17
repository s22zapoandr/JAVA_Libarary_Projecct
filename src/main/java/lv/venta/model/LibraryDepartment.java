
package lv.venta.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

	
	
    private void validateWorkingHours() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek currentDay = now.getDayOfWeek();
        LocalTime currentTime = now.toLocalTime();

        if (currentDay == DayOfWeek.SUNDAY || currentDay == DayOfWeek.SATURDAY || 
            currentTime.isBefore(LocalTime.of(workingHoursStart, 0)) || currentTime.isAfter(LocalTime.of(workingHoursEnd, 0))) {
            throw new Exception("It is not working hours now");
        }
    }
	
	
	//Override
	public void setWorkingHoursStart() {
		this.workingHoursStart = 9;
	}
	
	public void setWorkingHoursEnd() {
		this.workingHoursEnd = 17;
	}
	
	
	
    // Method to give a book to a reader
    public void giveBook(Book book, Reader reader) throws Exception {
    	validateWorkingHours();
        if (bookList.contains(book) && book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            book.setReader(reader);
            reader.getCurrentTakenBookList().add(book);
            bookList.remove(book);
        } else {
            bookQueueForFutureCheckout.add(book);
        }
    }

    // Method to take a book from a reader
    public void takeBook(Book book, Reader reader) throws Exception{
    	validateWorkingHours();
    	if(reader.getCurrentTakenBookList().contains(book) == false) throw new Exception("This reader did not take any book"); 
        book.setQuantity(book.getQuantity() + 1);
        book.setReader(null);
        reader.getCurrentTakenBookList().remove(book);
        reader.addBookToHistory(book);
        bookList.add(book);
     } 
 

    // Method to add a book to the department
    public void addBook(Book book) throws Exception {
    	validateWorkingHours();
        bookList.add(book);
    }

    // Method to remove a book from the department
    public void removeBook(Book book) throws Exception {
    	validateWorkingHours();
        bookList.remove(book);
    }
    
    
    public LibraryDepartment(Genre specialization) {
    	setSpecialization(specialization);
    	setWorkingHoursStart();
    	setWorkingHoursEnd();
    }
    
}
