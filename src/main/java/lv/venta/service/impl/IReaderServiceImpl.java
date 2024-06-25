package lv.venta.service.impl;


import org.springframework.beans.factory.annotation.Autowired;

import lv.venta.model.Book;
import lv.venta.model.Reader;
import lv.venta.service.ILibraryDepartmentService;
import lv.venta.service.IReaderService;

public class IReaderServiceImpl implements IReaderService {
	
	@Autowired
	private ILibraryDepartmentService departmentService;
	
	
	@Autowired
	private Reader reader;
	
	@Override
	public void takeABook(Book book) throws Exception{
		if(reader.getCurrentTakenBookList().contains(book) == true) throw new Exception("Book already taken");
		departmentService.giveBook(book, reader);
		
	}
	
	@Override
    public void addBookToHistory(Book book) throws Exception{
    	if(reader.getCurrentTakenBookList().contains(book) == false) throw new Exception("No such book found in users current book list");
    	reader.getBookHistory().add(book);
    }
	
	@Override
    public void returnBookToLibrary(Book book) throws Exception{
    	if(reader.getBookHistory().contains(book) == false) throw new Exception("No such book found in users book history");
    	departmentService.takeBook(book, reader);
    }
	
	
}
