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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Reader")
@Entity
public class Reader {
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "IdR")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idr;

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
	
	
	@OneToMany(mappedBy = "reader")
	private Collection<Book> currentTakenBookList = new ArrayList<Book>();
	
    @OneToMany(mappedBy = "reader")
    private Collection<Book> bookHistory = new ArrayList<>();

    // Method to load history
    public Collection<Book> loadHistory() {
        return bookHistory;
    }

    // Method to add a book to history
    public void addBookToHistory(Book book) {
        bookHistory.add(book);
    }
	
	public Reader(String name, String surname){
		setName(name);
		setSurname(surname);
	}
	

}
