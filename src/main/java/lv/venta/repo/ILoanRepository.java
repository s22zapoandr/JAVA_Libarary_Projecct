package lv.venta.repo;

import lv.venta.model.Loan;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ILoanRepository extends CrudRepository<Loan, Long> {
    ArrayList<Loan> findByDueDateBefore(LocalDate date);
}

