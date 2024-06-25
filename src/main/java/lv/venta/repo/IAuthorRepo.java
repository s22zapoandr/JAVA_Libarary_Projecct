package lv.venta.repo;

import lv.venta.model.Author;
import lv.venta.model.Genre;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorRepo extends JpaRepository<Author, Long> {

	ArrayList<Author> findAllByGenre(Genre genre);
	
}
