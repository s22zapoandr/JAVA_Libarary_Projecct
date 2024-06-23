package lv.venta.repo;

import lv.venta.model.Book;
import lv.venta.model.Condition;
import lv.venta.model.Genre;
import lv.venta.model.Rarity;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepo extends JpaRepository<Book, Long> {
	
	ArrayList<Book> findCurrentTakenBookListByIdR(long idR);
	
	ArrayList<Book> findBookHistoryByIdR(long idR);
	
	ArrayList<Book> findWrittenBooksByIdA(long idA);
	
	ArrayList<Book> findByGenre(Genre genre);
	
	ArrayList<Book> findByRarity(Rarity rarity);
	
	ArrayList<Book> findByCondition(Condition condition);
	
	ArrayList<Book> findByRating(int rating);
	
	ArrayList<Book> findByYear(long year);
}
