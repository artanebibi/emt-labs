package mk.ukim.finki.wp.emt_lab.web;


import mk.ukim.finki.wp.emt_lab.model.Book;
import mk.ukim.finki.wp.emt_lab.model.dto.BookDto;
import mk.ukim.finki.wp.emt_lab.service.AuthorService;
import mk.ukim.finki.wp.emt_lab.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String listAll(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "home";
    }

}
