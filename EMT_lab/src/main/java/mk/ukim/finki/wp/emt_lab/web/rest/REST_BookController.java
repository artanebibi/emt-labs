package mk.ukim.finki.wp.emt_lab.web.rest;


import mk.ukim.finki.wp.emt_lab.model.Book;
import mk.ukim.finki.wp.emt_lab.model.dto.BookDto;
import mk.ukim.finki.wp.emt_lab.service.AuthorService;
import mk.ukim.finki.wp.emt_lab.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")

public class REST_BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public REST_BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/")
    public List<Book> listAll(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return books;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        return bookService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/add")
    public ResponseEntity<Book> add(@RequestBody BookDto bookDto) {
        return bookService.save(bookDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/edit/{id}")
    public ResponseEntity<Book> edit(@PathVariable Long id, @RequestBody BookDto bookDto) {
        return bookService.update(id, bookDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/delete/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id) {
        if (bookService.findById(id).isPresent()) {
            bookService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/rent/{id}")
    public ResponseEntity<Book> rent(@PathVariable Long id, @RequestBody BookDto bookDto) {
        return bookService.rentBook(id, bookDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
