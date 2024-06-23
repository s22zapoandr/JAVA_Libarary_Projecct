package lv.venta.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.Book;
import lv.venta.model.Loan;
import lv.venta.model.Reader;
import lv.venta.repo.ILoanRepository;
import lv.venta.service.ILoanService;

@Service
public class ILoanServiceImpl implements ILoanService{
    @Autowired
    private ILoanRepository loanRepository;

    private Loan loan;
    
    @Override
    public void loanBook(Book book, Reader reader) {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(100);
        Loan loan = new Loan(book, reader, today, dueDate);
        loanRepository.save(loan);
    }
    
    @Override
    public void returnBook(Loan loan) {
        loanRepository.delete(loan);
    }
    
    
    @Override
    public ArrayList<Loan> getAllLoans() {
        return (ArrayList<Loan>) loanRepository.findAll();
    }
    
    @Override
	public ArrayList<Loan> getOverdueLoans() {
        LocalDate today = LocalDate.now();
        return (ArrayList<Loan>) loanRepository.findByDueDateBefore(today);
    }
}
