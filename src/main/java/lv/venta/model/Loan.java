package lv.venta.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "BookList")
@Entity
public class Loan {
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "IdL")
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long loanId;
	
	@NotNull
	@Column(name = "Book")
    private Book book;
	
	@NotNull
	@Column(name = "Reader")
    private Reader reader;
	
	@NotNull
	@Column(name = "From")
    private LocalDate loanDate;
	
	@NotNull
	@Column(name = "TO")
    private LocalDate dueDate;
	
    public boolean isOverdue() {
        return LocalDate.now().isAfter(getDueDate());
    }
	
	public Loan(Book book, Reader reader, LocalDate loanDate, LocalDate dueDate) {
		setBook(book);
		setReader(reader);
		setLoanDate(loanDate);
		setDueDate(dueDate);
	}
}
