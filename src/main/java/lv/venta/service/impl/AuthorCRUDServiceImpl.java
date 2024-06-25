package lv.venta.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.Author;
import lv.venta.model.Genre;
import lv.venta.repo.IAuthorRepo;
import lv.venta.service.AuthorCRUDService;


@Service

public class AuthorCRUDServiceImpl implements AuthorCRUDService{

	@Autowired
	private IAuthorRepo authorRepo;
	
	
	
	@Override
	
	public ArrayList<Author> selectAllAuthorsByGenre(Genre genre) throws Exception{
		ArrayList<Author> result = (ArrayList<Author>) authorRepo.findAllByGenre(genre);
		
		if(result.isEmpty()) throw new Exception("There are no authors");
		
		return result;
	}
	
	
	@Override
	
	public ArrayList<Author> selectAllAuthors() throws Exception{
		ArrayList<Author> result = (ArrayList<Author>) authorRepo.findAll();
		
		if(result.isEmpty()) throw new Exception("There are no authors");
		
		return result;
	}
	
	@Override
	
	public Author selectAuthorById(Long idA) throws Exception{
		
		if(idA < 1) throw new Exception("Id should be positive");
		
		if(!authorRepo.existsById(idA)) throw new Exception("Driver with id ("+idA+") does not exist");
		
		return authorRepo.findById(idA).get();
		
	}
	
	@Override
	
	public void deleteAuthorById(Long idA) throws Exception{
		
		if(idA < 1) throw new Exception("Id should be positive");
		
		if(!authorRepo.existsById(idA)) throw new Exception("Author with id ("+idA+") does not exist");
		
		 authorRepo.deleteById(idA);
	}
	
	@Override
	
	public void updateAuthorById(Long idA, String name, String surname, Genre genre) throws Exception{
		
		if(idA < 1) throw new Exception("Id should be positive");
		
		if(!authorRepo.existsById(idA)) throw new Exception("Author with id ("+idA+") does not exist");
		
		Optional<Author> updateAuthor = authorRepo.findById(idA);
		
		updateAuthor.get().setName(name);
		updateAuthor.get().setSurname(surname);	
		updateAuthor.get().setGenre(genre);
;
		
		authorRepo.save(updateAuthor.get());
	}
	
	@Override
	
	public void insertNewAuthor(String name, String surname, Genre genre)throws Exception {
		if (name == null || surname == null || genre == null )
			throw new Exception("Problems with input params");


		Author newAuthor = new Author(name, surname, genre);
		authorRepo.save(newAuthor);

	}
}
