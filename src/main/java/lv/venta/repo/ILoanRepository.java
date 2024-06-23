package lv.venta.repo;

import lv.venta.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ILoanRepository extends JpaRepository<Loan, Long> {
    ArrayList<Loan> findByDueDateBefore(LocalDate date);
}

