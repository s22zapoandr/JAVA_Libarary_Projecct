package lv.venta.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Table(name = "BookList")
@Entity
public class Book {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "IdB")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idb;
	
	@NotNull
	@Pattern(regexp = "[A-Za-z ]+")
	@Size(min = 4, max = 50)
	@Column(name = "Title")
	private String title;
	
	@NotNull
	@Column(name = "Author")
	private Author author;
	
	@Min(1)
	@Max(10)
	@Column(name = "Rating")
	private int rating;
	
	@NotNull
	@Column(name = "Condition")
	private Condition condition;
	
	@NotNull
	@Column(name = "Rarity")
	private Rarity rarity;
	
	@Min(1)
	@Max(15)
	@Column(name = "Quantity")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "IdR")
	private Reader reader;
	
	@ManyToOne
	@JoinColumn(name = "IdLD")
	private LibraryDepartment libraryDepartment;
	
	public void changeCondition(Condition newCondition) {
		setCondition(newCondition);
	}
	
	public Book(String title, Author author, int rating, Condition condition, Rarity rarity,int quantity) {
		setTitle(title);
		setAuthor(author);
		setRating(rating);
		setCondition(condition);
		setRarity(rarity);
		setQuantity(quantity);
	}
	
	
	
	
	
	
	
	
}
