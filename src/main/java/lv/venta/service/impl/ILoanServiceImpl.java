package lv.venta.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.Book;
import lv.venta.model.Loan;
import lv.venta.model.Reader;
import lv.venta.repo.ILoanRepository;
import lv.venta.service.ILoanService;

@Service
public class ILoanServiceImpl implements ILoanService {
    @Autowired
    private ILoanRepository loanRepository;

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

    // CRUD methods
    @Override
    public Loan createLoan(Book book, Reader reader) {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(100);
        Loan loan = new Loan(book, reader, today, dueDate);
        loanRepository.save(loan);
        return loan;
    }

    @Override
    public Loan updateLoan(long id, Loan loanDetails) throws Exception {
        Optional<Loan> loanOpt = loanRepository.findById(id);
        if (loanOpt.isPresent()) {
            Loan loan = loanOpt.get();
            loan.setBook(loanDetails.getBook());
            loan.setReader(loanDetails.getReader());
            loan.setLoanDate(loanDetails.getLoanDate());
            loan.setDueDate(loanDetails.getDueDate());
            return loanRepository.save(loan);
        } else {
            throw new Exception("Loan not found with id: " + id);
        }
    }

    @Override
    public void deleteLoan(long id) throws Exception {
        if (loanRepository.existsById(id)) {
            loanRepository.deleteById(id);
        } else {
            throw new Exception("Loan not found with id: " + id);
        }
    }

    @Override
    public Loan getLoanById(long id) throws Exception {
        return loanRepository.findById(id).orElseThrow(() -> new Exception("Loan not found with id: " + id));
    }
}




