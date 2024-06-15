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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    private long idl;
	
	
	@Min(1)
	@Max(30)
	@Column(name = "Emplyees")
	private long employees;
	
	
	@OneToMany(mappedBy = "libraryDepartment")
    private Collection<Book> bookList = new ArrayList<>();

	@OneToMany(mappedBy = "libraryDepartment")
    private Collection<Book> bookQueueForFutureCheckout = new ArrayList<>();

    // Method to give a book to a reader
    public void giveBook(Book book, Reader reader) {
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
    	if(reader.getCurrentTakenBookList().contains(book) == false) throw new Exception("This reader did not take any book"); 
        book.setQuantity(book.getQuantity() + 1);
        book.setReader(null);
        reader.getCurrentTakenBookList().remove(book);
        reader.addBookToHistory(book);
        bookList.add(book);
     } 
 

    // Method to add a book to the department
    public void addBook(Book book) {
        bookList.add(book);
    }

    // Method to remove a book from the department
    public void removeBook(Book book) {
        bookList.remove(book);
    }
}

