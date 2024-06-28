package ru.otus.hw.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookPageController {

    @GetMapping
    public String listPage() {
        return "books";
    }

    @GetMapping("/books")
    public String createPage() {
        return "book-form";
    }

    @GetMapping("/books/{id}")
    public String updatePage(@PathVariable long id, Model model) {
        model.addAttribute("bookId", id);
        return "book-form";
    }

}