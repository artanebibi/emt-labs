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

public class BookRESTController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookRESTController(BookService bookService, AuthorService authorService) {
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
    @DeleteMapping("/delete/{id}")
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


    @GetMapping("/searchBook")
    public ResponseEntity<List<Book>> search(@RequestParam(required = false) String author_name, @RequestParam(required = false) String book_name) {
//        if (!Objects.equals(author_name, "")) author_name = author_name.toLowerCase();
//        if (!Objects.equals(book_name, "")) book_name = book_name.toLowerCase();


        List<Book> books = bookService.findByAuthorAndBookName(author_name, book_name);

        if (!books.isEmpty()) {
            return ResponseEntity.ok(books);
        }

        return ResponseEntity.notFound().build();
    }

}
