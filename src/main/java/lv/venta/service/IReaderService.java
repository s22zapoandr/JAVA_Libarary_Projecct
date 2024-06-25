package lv.venta.service;

import lv.venta.model.Book;

public interface IReaderService {

	void addBookToHistory(Book book) throws Exception;

	void returnBookToLibrary(Book book) throws Exception;

	void takeABook(Book book) throws Exception;
	
}
