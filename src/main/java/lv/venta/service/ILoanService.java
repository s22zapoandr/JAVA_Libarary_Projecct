package lv.venta.service;

import java.util.ArrayList;

import lv.venta.model.Book;
import lv.venta.model.Loan;
import lv.venta.model.Reader;

public interface ILoanService {

	void loanBook(Book book, Reader reader);

	ArrayList<Loan> getOverdueLoans();

	ArrayList<Loan> getAllLoans();

	void returnBook(Loan loan);

}
