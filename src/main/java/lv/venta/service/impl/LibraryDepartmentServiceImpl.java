package lv.venta.service.impl;

import lv.venta.model.Book;
import lv.venta.model.Genre;
import lv.venta.model.LibraryDepartment;
import lv.venta.model.Reader;
import lv.venta.service.ILibraryDepartmentService;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private void validateWorkingHours(LibraryDepartment libraryDepartment) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek currentDay = now.getDayOfWeek();
        LocalTime currentTime = now.toLocalTime();

        if (currentDay == DayOfWeek.SUNDAY || currentDay == DayOfWeek.SATURDAY ||
                currentTime.isBefore(LocalTime.of(libraryDepartment.getWorkingHoursStart(), 0)) ||
                currentTime.isAfter(LocalTime.of(libraryDepartment.getWorkingHoursEnd(), 0))) {
            throw new Exception("It is not working hours now");
        }
    }

    @Override
    public void giveBook(Book book, Reader reader) throws Exception {
        validateWorkingHours(libraryDepartment);
        if (libraryDepartment.getBookList().contains(book) && book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            book.setReader(reader);
            reader.getCurrentTakenBookList().add(book);
            libraryDepartment.getBookList().remove(book);
        } else {
            libraryDepartment.getBookQueueForFutureCheckout().add(book);
        }
    }

    @Override
    public void takeBook(Book book, Reader reader) throws Exception {
        validateWorkingHours(libraryDepartment);
        if (!reader.getCurrentTakenBookList().contains(book)) throw new Exception("This reader did not take any book");
        book.setQuantity(book.getQuantity() + 1);
        book.setReader(null);
        reader.getCurrentTakenBookList().remove(book);
        reader.addBookToHistory(book);
        libraryDepartment.getBookList().add(book);
    }

    @Override
    public void addBook(Book book) throws Exception {
        validateWorkingHours(libraryDepartment);
        libraryDepartment.getBookList().add(book);
    }

    @Override
    public void removeBook(Book book) throws Exception {
        validateWorkingHours(libraryDepartment);
        libraryDepartment.getBookList().remove(book);
    }

    @Override
    public LibraryDepartment createLibraryDepartment(Genre specialization) {
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
