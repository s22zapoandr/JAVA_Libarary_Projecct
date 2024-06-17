package lv.venta.service;

import lv.venta.model.Book;
import lv.venta.model.Genre;
import lv.venta.model.LibraryDepartment;
import lv.venta.model.Reader;

import java.util.List;

public interface ILibraryDepartmentService {

    void giveBook(Book book, Reader reader) throws Exception;

    void takeBook(Book book, Reader reader) throws Exception;

    void addBook(Book book) throws Exception;

    void removeBook(Book book) throws Exception;

    LibraryDepartment createLibraryDepartment(Genre specialization);

    List<Book> getAllBooks();
    
    List<Book> getBookQueueForFutureCheckout();
}