package lv.venta.service.impl;

import lv.venta.model.Author;
import lv.venta.model.Book;
import lv.venta.model.Genre;
import lv.venta.model.LibraryDepartment;
import lv.venta.model.Loan;
import lv.venta.model.Reader;
import lv.venta.repo.IAuthorRepo;
import lv.venta.repo.IBookRepo;
import lv.venta.repo.ILibraryDepartmentRepo;
import lv.venta.service.ILibraryDepartmentService;
import lv.venta.service.ILoanService;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryDepartmentServiceImpl implements ILibraryDepartmentService {

    @Autowired
    private IBookRepo bookRepo;
    
    @Autowired
    private IAuthorRepo authorRepo;
    
    @Autowired
    private ILoanService loanService;
    
    private LibraryDepartment libraryDepartment;
	
    @Override
	public void validateWorkingHours() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek currentDay = now.getDayOfWeek();
        LocalTime currentTime = now.toLocalTime();

        if (currentDay == DayOfWeek.SUNDAY || currentDay == DayOfWeek.SATURDAY || 
            currentTime.isBefore(LocalTime.of(libraryDepartment.getWorkingHoursStart(), 0)) || currentTime.isAfter(LocalTime.of(libraryDepartment.getWorkingHoursEnd(), 0))) {
            throw new Exception("It is not working hours now");
        }
    }
	
	
    @Override
    public void giveBook(Book book, Reader reader) throws Exception {
    	validateWorkingHours();
        if(reader.getCurrentTakenBookList().contains(book) == false) throw new Exception("The user has already taken this book");
        List<Loan> loans = loanService.getAllLoans();
        boolean hasOverdueLoans = loans.stream()
                .filter(Loan::isOverdue)
                .anyMatch(loan -> loan.getReader().equals(reader));
        if(hasOverdueLoans) throw new Exception("The user has overdue loans");
        if (libraryDepartment.getBookList().contains(book) && book.getQuantity() > 0) {
        	book.setQuantity(book.getQuantity() - 1);
            book.setReader(reader);
            reader.getCurrentTakenBookList().add(book);
            libraryDepartment.getBookList().remove(book);
            libraryDepartment.getReaders().add(reader);
        } else {
        	libraryDepartment.getBookQueueForFutureCheckout().add(book);
        }
    }

    @Override
    public void takeBook(Book book, Reader reader) throws Exception{
        validateWorkingHours();
        List<Loan> loans = loanService.getAllLoans();
        Loan loan = loans.stream().filter(l -> l.getBook().equals(book) && l.getReader().equals(reader)).findFirst().orElse(null);
        
        if (loan == null) {
            throw new Exception("This reader did not take this book");
        }
        
        loanService.returnBook(loan);
        book.setQuantity(book.getQuantity() + 1);
        reader.getCurrentTakenBookList().remove(book);
        reader.getBookHistory().add(book);
        libraryDepartment.getBookList().add(book);
        libraryDepartment.getReaders().remove(reader);
    }

    
    @Override
    public void addBook(Book book) throws Exception {
    	validateWorkingHours();
    	libraryDepartment.getBookList().add(book);
    }

    @Override
    public void removeBook(Book book) throws Exception {
    	validateWorkingHours();
    	libraryDepartment.getBookList().remove(book);
    }
    
    @Override
    public List<Book> getAllBooks() {
        return libraryDepartment.getBookList().stream().collect(Collectors.toList());
    }

    @Override
    public List<Book> getBookQueueForFutureCheckout() {
        return libraryDepartment.getBookQueueForFutureCheckout().stream().collect(Collectors.toList());
    }

	@Override
    public LibraryDepartment createLibraryDepartment(Genre specialization) {
        return new LibraryDepartment(specialization);
    }
	
	
    @Scheduled(cron = "0 0 0 * * ?")  // Runs every day at midnight
    public void checkOverdueLoans() {
        List<Loan> overdueLoans = loanService.getOverdueLoans();
        for (Loan loan : overdueLoans) {
            try {
                takeBook(loan.getBook(), loan.getReader());
            } catch (Exception e) {
                // Handle exception 
            }
        }
    }	

    
    public ArrayList<Book> getAllBoksByAuthorId(long IdA) throws Exception{
    	Optional<Author> author = authorRepo.findById(IdA);
    	if(!author.isPresent()) {
    		throw new Exception("Driver with this id is not registered in system");
    	}
    	else {
    		ArrayList<Book> result = bookRepo.findWrittenBooksByIdA(IdA);
    		return result;
    	}
    }
 
    
  
}