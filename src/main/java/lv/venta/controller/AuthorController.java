package lv.venta.controller;

import lv.venta.model.Author;
import lv.venta.repo.IAuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private IAuthorRepo authorRepo;

    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorRepo.findAll());
        return "authors";
    }

    @GetMapping("/add")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "add-author";
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute Author author) {
        authorRepo.save(author);
        return "redirect:/authors";
    }
}
