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
@Table(name = "Author")
@Entity
public class Author {
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "IdA")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ida;

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
	
	@NotNull
	@Column(name = "Genre")
	private Genre genre;
	
	@OneToMany(mappedBy = "author")
	private Collection<Book> writtenBooks = new ArrayList<Book>();
	
	public Author(String name, String surname, Genre genre) {
		setName(surname);
		setSurname(surname);
		setGenre(genre);
	}
	
	
}
