package mk.ukim.finki.wp.emt_lab.web.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.wp.emt_lab.dto.Book.CreateBookDto;
import mk.ukim.finki.wp.emt_lab.dto.Book.UpdateBookDto;
import mk.ukim.finki.wp.emt_lab.model.domain.Book;
import mk.ukim.finki.wp.emt_lab.service.application.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book API", description = "Endpoints for CRUD in book") // Swagger tag

public class BookRESTController {
    private final BookService bookService;

    public BookRESTController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "List all books", description = "Retrieve a list of all books in the system.")
    @GetMapping("/")
    public List<Book> listAll() {
        return bookService.findAll();
    }

    @Operation(summary = "Find a book by ID", description = "Retrieve details of a specific book using its unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @Operation(summary = "Add a new book", description = "Create and add a new book to the system. Only accessible by users with the 'LIBRARIAN' role.")
    @PostMapping("/add")
    public ResponseEntity<Book> add(@RequestBody CreateBookDto bookDto) {
        return bookService.save(bookDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @Operation(summary = "Edit an existing book", description = "Update the details of an existing book using its unique ID. Only accessible by users with the 'LIBRARIAN' role.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Book> edit(@PathVariable Long id, @RequestBody UpdateBookDto bookDto) {
        return bookService.update(id, bookDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @Operation(summary = "Delete a book", description = "Delete a specific book from the system using its unique ID. Only accessible by users with the 'LIBRARIAN' role.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (bookService.findById(id).isPresent()) {
            bookService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Search books", description = "Search for books based on author name and book name.")
    @GetMapping("/search")
    public ResponseEntity<List<Book>> search(@RequestParam(required = false) String authorName,
                                             @RequestParam(required = false) String bookName) {
        List<Book> books = bookService.findByAuthorAndBookName(authorName, bookName);

        return books.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(books);
    }
}
