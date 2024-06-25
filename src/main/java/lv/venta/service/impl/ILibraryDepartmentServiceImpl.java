package lv.venta.service.impl;

import lv.venta.model.Author;
import lv.venta.model.Book;
import lv.venta.model.Condition;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ILibraryDepartmentServiceImpl implements ILibraryDepartmentService {

    @Autowired
    private IBookRepo bookRepo;
    
    @Autowired
    private IAuthorRepo authorRepo;
    
    @Autowired
    private ILibraryDepartmentRepo libraryDepartmentRepo;
    
    @Autowired
    private ILoanService loanService;
    
    private LibraryDepartment libraryDepartment;
    
    private Map<Loan, LocalDateTime> warningSent = new ConcurrentHashMap<>();
	
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
        // Check if the reader already has the book
        if (reader.getCurrentTakenBookList().contains(book)) {
            throw new Exception("The user has already taken this book");
        }
        // Check if the reader has overdue loans
        List<Loan> loans = loanService.getAllLoans();
        boolean hasOverdueLoans = loans.stream()
                .filter(Loan::isOverdue)
                .anyMatch(loan -> loan.getReader().equals(reader));
        if (hasOverdueLoans) {
            throw new Exception("The user has overdue loans");
        }
        // Filter books based on condition
        List<Book> availableBooks = libraryDepartment.getBookList().stream()
                .filter(b -> b.getTitle().equals(book.getTitle()) && b.getQuantity() > 0)
                .collect(Collectors.toList());
        Optional<Book> goodConditionBook = availableBooks.stream()
                .filter(b -> b.getCondition() == Condition.Good)
                .findFirst();
        Optional<Book> moderateConditionBook = availableBooks.stream()
                .filter(b -> b.getCondition() == Condition.Moderate)
                .findFirst();
        // Throw exception if only books with bad condition are available
        if (availableBooks.stream().allMatch(b -> b.getCondition() == Condition.Bad)) {
            throw new Exception("All available copies of this book are in bad condition");
        }
        // Get the best available book
        Book bookToGive = goodConditionBook.orElse(moderateConditionBook.orElseThrow(() -> new Exception("No suitable books available")));
        // Update book and reader details
        bookToGive.setQuantity(bookToGive.getQuantity() - 1);
        bookToGive.setReader(reader);
        reader.getCurrentTakenBookList().add(bookToGive);
        libraryDepartment.getBookList().remove(bookToGive);
        libraryDepartment.getReaders().add(reader);
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
    public ArrayList<Book> getAllBooks() {
        return (ArrayList<Book>) libraryDepartment.getBookList().stream().collect(Collectors.toList());
    }

    @Override
    public ArrayList<Book> getBookQueueForFutureCheckout() {
        return (ArrayList<Book>) libraryDepartment.getBookQueueForFutureCheckout().stream().collect(Collectors.toList());
    }

	@Override
    public LibraryDepartment createLibraryDepartment(Genre specialization) {
        return new LibraryDepartment(specialization);
    }
	
	@Override
    @Scheduled(cron = "0 0 0 * * ?")  // Runs every day at midnight
    public void checkOverdueLoans() {
        List<Loan> overdueLoans = loanService.getOverdueLoans();
        for (Loan loan : overdueLoans) {
            try {
                if (warningSent.containsKey(loan)) {
                    LocalDateTime warningTime = warningSent.get(loan);
                    if (ChronoUnit.DAYS.between(warningTime, LocalDateTime.now()) >= 3) {
                        takeBook(loan.getBook(), loan.getReader());
                    }
                } else {
                    // Send warning message to the user
                    warningSent.put(loan, LocalDateTime.now());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	@Override
    public ArrayList<Book> getAllBoksByAuthorId(long IdA) throws Exception{
    	Optional<Author> author = authorRepo.findById(IdA);
    	if(!author.isPresent()) {
    		throw new Exception("Author with this id is not registered in system");
    	}
    	else {
    		ArrayList<Book> result = bookRepo.findWrittenBooksByIdA(IdA);
    		return result;
    	}
    }
 
	// CRUD methods
    @Override
    public ArrayList<LibraryDepartment> getAllLibraryDepartments() throws Exception {
        return (ArrayList<LibraryDepartment>) libraryDepartmentRepo.findAll();
    }

    @Override
    public LibraryDepartment getLibraryDepartmentById(Long idLD) throws Exception {
        Optional<LibraryDepartment> department = libraryDepartmentRepo.findById(idLD);
        if (!department.isPresent()) {
            throw new Exception("Library Department with this id does not exist");
        }
        return department.get();
    }

    @Override
    public void deleteLibraryDepartmentById(Long idLD) throws Exception {
        if (!libraryDepartmentRepo.existsById(idLD)) {
            throw new Exception("Library Department with this id does not exist");
        }
        libraryDepartmentRepo.deleteById(idLD);
    }

    @Override
    public void updateLibraryDepartmentById(Long idLD, Genre specialization) throws Exception {
        Optional<LibraryDepartment> updateDepartment = libraryDepartmentRepo.findById(idLD);
        if (!updateDepartment.isPresent()) {
            throw new Exception("Library Department with this id does not exist");
        }
        LibraryDepartment department = updateDepartment.get();
        department.setSpecialization(specialization);
        libraryDepartmentRepo.save(department);
    }
}
