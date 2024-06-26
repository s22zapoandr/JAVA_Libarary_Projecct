package lv.venta.controller;

import lv.venta.model.Author;
import lv.venta.model.Book;
import lv.venta.repo.IAuthorRepo;
import lv.venta.service.ILibraryDepartmentService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private ILibraryDepartmentService libraryService;

    @Autowired
    private IAuthorRepo authorRepo;
    
    // Was
    @GetMapping("/filter/grade/failed")
    public String getFilterGradeFailed(Model model) {
        try {
            return "error-page";
        } catch (Exception e) {
            model.addAttribute("mypackage", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/books")
    public String viewBooks(Model model) {
        model.addAttribute("books", libraryService.getAllBooks());
        return "books";
    }

    @GetMapping("/add-book")
    public String showAddBookForm() {
        return "add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@RequestParam String title, @RequestParam List<Long> authorIds, @RequestParam int quantity) throws Exception {
        List<Author> authors = new ArrayList<>();
        try {
            authors = authorRepo.findAllById(authorIds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error fetching authors", e);
        }

        if (authors.isEmpty()) {
            throw new Exception("No authors found for the given IDs");
        }

        Book book = new Book();
        book.setTitle(title);
        book.setAuthors(authors);
        book.setQuantity(quantity);
        libraryService.addBook(book);

        return "redirect:/library/books";
    }
}

