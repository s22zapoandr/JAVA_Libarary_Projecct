package lv.venta.repo;

import lv.venta.model.Author;
import lv.venta.model.Genre;

import java.util.ArrayList;

public interface IAuthorRepo extends CrudRepository<Author, Long> {

	ArrayList<Author> findAllByGenre(Genre genre);
	
}
