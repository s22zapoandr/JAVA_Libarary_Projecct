package lv.venta.service.impl;

import lv.venta.model.Book;
import lv.venta.model.Genre;
import lv.venta.model.LibraryDepartment;
import lv.venta.model.Reader;
import lv.venta.service.ILibraryDepartmentService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryDepartmentServiceImpl implements ILibraryDepartmentService {

    @Autowired
    private LibraryDepartment libraryDepartment;

    public LibraryDepartmentServiceImpl() {
    }

    @Override
    public void giveBook(Book book, Reader reader) throws Exception {
        libraryDepartment.giveBook(book, reader);
    }

    @Override
    public void takeBook(Book book, Reader reader) throws Exception {
        libraryDepartment.takeBook(book, reader);
    }

    @Override
    public void addBook(Book book) throws Exception {
        libraryDepartment.addBook(book);
    }

    @Override
    public void removeBook(Book book) throws Exception {
        libraryDepartment.removeBook(book);
    }

    @Override
    public LibraryDepartment createLibraryDepartment(Genre specialization) {
        // Create and return a new LibraryDepartment with the given specialization
        return new LibraryDepartment(specialization);
    }

    @Override
    public List<Book> getAllBooks() {
        return libraryDepartment.getBookList().stream().collect(Collectors.toList());
    }

    @Override
    public List<Book> getBookQueueForFutureCheckout() {
        return libraryDepartment.getBookQueueForFutureCheckout().stream().collect(Collectors.toList());
    }
}