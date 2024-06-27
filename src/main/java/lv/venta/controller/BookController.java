package lv.venta.controller;

import lv.venta.model.Author;
import lv.venta.model.Book;
import lv.venta.model.Condition;
import lv.venta.model.LibraryDepartment;
import lv.venta.model.Rarity;
import lv.venta.service.BookCRUDService;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookCRUDService bookService;

    @GetMapping("/show/all")
    public String getAllBooks(Model model) {
        try {
            ArrayList<Book> books = bookService.selectAllBooks();
            model.addAttribute("books", books);
            return "book-show-all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/show/{idB}")
    public String getBookById(@PathVariable("idB") Long idB, Model model) {
        try {
            Book book = bookService.selectBookById(idB);
            model.addAttribute("book", book);
            return "book-one-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/remove/{idB}")
    public String removeBookById(@PathVariable("idB") Long idB, Model model) {
        try {
            bookService.deleteBookById(idB);
            return "redirect:/book/show/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/add")
    public String getBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-add-page";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute String title, List<Author> authors, int rating, Condition condition, Rarity rarity, int quantity, int year, LibraryDepartment libraryDepartment, Model model) {
        try {
            bookService.insertNewBook(title, authors, rating, condition, rarity, quantity, year, libraryDepartment);
            return "redirect:/book/show/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/update/{idB}")
    public String getUpdateBookForm(@PathVariable("idB") Long idB, Model model) {
        try {
            Book book = bookService.selectBookById(idB);
            model.addAttribute("book", book);
            return "book-update-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{idB}")
    public String updateBookById(@PathVariable("idB") Long idB, @ModelAttribute Book book, Model model) {
        try {
            bookService.updateBookById(idB, book.getTitle(), book.getAuthors(), book.getRating(), book.getCondition(), book.getRarity(), book.getQuantity(), book.getYearb(), book.getLibraryDepartment());
            return "redirect:/book/show/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
}
