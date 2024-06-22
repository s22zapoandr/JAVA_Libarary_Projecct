package lv.venta.controller;

import lv.venta.model.Book;
import lv.venta.service.ILibraryDepartmentService;
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

    // New(Need to check)
    @GetMapping("/books")
    public String viewBooks(Model model) {
        model.addAttribute("books", libraryService.getAllBooks());
        return "books";
    }

    // New(Need to check)
    @GetMapping("/add-book")
    public String showAddBookForm() {
        return "add-book";
    }

    // New(Need to check)
    @PostMapping("/add-book")
    public String addBook(@RequestParam String title, @RequestParam String author, @RequestParam int quantity) throws Exception {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setQuantity(quantity);
        libraryService.addBook(book);
        return "redirect:/library/books";
    }
}
