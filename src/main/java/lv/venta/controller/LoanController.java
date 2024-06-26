package lv.venta.controller;

import lv.venta.model.Book;
import lv.venta.model.Loan;
import lv.venta.model.Reader;
import lv.venta.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private ILoanService loanService;

    @GetMapping("/show/all")
    public String getAllLoans(Model model) {
        ArrayList<Loan> loans = loanService.getAllLoans();
        model.addAttribute("loans", loans);
        return "loan-show-all";
    }

    @GetMapping("/show/{id}")
    public String getLoanById(@PathVariable("id") long id, Model model) {
        try {
            Loan loan = loanService.getLoanById(id);
            model.addAttribute("loan", loan);
            return "loan-show-one";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/add")
    public String getAddLoanForm(Model model) {
        model.addAttribute("loan", new Loan());
        return "loan-add-page";
    }

    @PostMapping("/add")
    public String addLoan(@ModelAttribute Book book, Reader reader, Model model) {
        loanService.createLoan(book, reader);
        return "redirect:/loans/show/all";
    }

    @GetMapping("/update/{id}")
    public String getUpdateLoanForm(@PathVariable("id") long id, Model model) {
        try {
            Loan loan = loanService.getLoanById(id);
            model.addAttribute("loan", loan);
            return "loan-update-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{id}")
    public String updateLoan(@PathVariable("id") long id, @ModelAttribute Loan loanDetails, Model model) {
        try {
            loanService.updateLoan(id, loanDetails);
            return "redirect:/loans/show/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteLoan(@PathVariable("id") long id, Model model) {
        try {
            loanService.deleteLoan(id);
            return "redirect:/loans/show/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
}
