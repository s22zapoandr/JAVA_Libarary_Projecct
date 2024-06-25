package lv.venta.service;

import java.util.ArrayList;

import lv.venta.model.Author;
import lv.venta.model.Genre;

public interface AuthorCRUDService {

	ArrayList<Author> selectAllAuthorsByGenre(Genre genre) throws Exception;

	ArrayList<Author> selectAllAuthors() throws Exception;

	Author selectAuthorById(Long idA) throws Exception;

	void deleteAuthorById(Long idA) throws Exception;

	void updateAuthorById(Long idA, String name, String surname, Genre genre) throws Exception;

	void insertNewAuthor(String name, String surname, Genre genre) throws Exception;

}
