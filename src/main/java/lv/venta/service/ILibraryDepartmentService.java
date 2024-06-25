package lv.venta.service;

import lv.venta.model.Book;
import lv.venta.model.Genre;
import lv.venta.model.LibraryDepartment;
import lv.venta.model.Reader;

import java.util.ArrayList;

public interface ILibraryDepartmentService {

	void validateWorkingHours() throws Exception;
	
    void giveBook(Book book, Reader reader) throws Exception;

    void takeBook(Book book, Reader reader) throws Exception;

    void addBook(Book book) throws Exception;

    void removeBook(Book book) throws Exception;

    LibraryDepartment createLibraryDepartment(Genre specialization);

    ArrayList<Book> getAllBooks();
    
    ArrayList<Book> getBookQueueForFutureCheckout();

    void checkOverdueLoans();

    ArrayList<Book> getAllBoksByAuthorId(long IdA) throws Exception;
	
	// CRUD methods
    ArrayList<LibraryDepartment> getAllLibraryDepartments() throws Exception;

    LibraryDepartment getLibraryDepartmentById(Long idLD) throws Exception;

    void deleteLibraryDepartmentById(Long idLD) throws Exception;

    void updateLibraryDepartmentById(Long idLD, Genre specialization) throws Exception;
}
