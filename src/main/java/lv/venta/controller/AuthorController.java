package lv.venta.controller;

import lv.venta.model.Author;
import lv.venta.model.Genre;
import lv.venta.service.AuthorCRUDService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorController {

    
    @Autowired
    private AuthorCRUDService authorService;

    @GetMapping("/show/all/")
    public String getAllAuthors(Model model) {
        try {
            ArrayList<Author> drivers = authorService.selectAllAuthors();
            model.addAttribute("drivers", drivers);
            return "author-show-all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/show/all/{idA}")
    public String getAuthorById(@PathVariable("idA") Long idA, Model model) {
        try {
            Author author = authorService.selectAuthorById(idA);
            model.addAttribute("author", author);
            return "author-one-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/remove/{idP}")
    public String removeAuthorById(@PathVariable("idA") Long idA, Model model) {
        try {
            authorService.deleteAuthorById(idA);
            return "redirect:/author/show/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/add")
    public String getAuthorForm(Model model) {
        model.addAttribute("Author", new Author());
        return "author-add-page";
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute String name, String surname, Genre genre, Model model) {
        try {
            authorService.insertNewAuthor(name, surname, genre);
            return "redirect:/driver/show/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

   
    @GetMapping("/update/{idP}")
    public String getUpdateDriverForm(@PathVariable("idA") Long idA, Model model) {
        try {
            Author author = authorService.selectAuthorById(idA);
            model.addAttribute("Author", author);
            return "driver-update-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/update/{idA}")
    public String updateAuthorById(@PathVariable("idA") Long idA, String name, String surname, Genre genre, Model model) {
        try {
            authorService.updateAuthorById(idA, name,  surname, genre);
            return "redirect:/driver/show/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
     
        }
}}

