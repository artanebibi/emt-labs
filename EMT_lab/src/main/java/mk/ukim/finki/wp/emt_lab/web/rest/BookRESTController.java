package mk.ukim.finki.wp.emt_lab.web.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.wp.emt_lab.dto.Book.CreateBookDto;
import mk.ukim.finki.wp.emt_lab.dto.Book.UpdateBookDto;
import mk.ukim.finki.wp.emt_lab.model.domain.Author;
import mk.ukim.finki.wp.emt_lab.model.domain.Book;
import mk.ukim.finki.wp.emt_lab.model.domain.User;
import mk.ukim.finki.wp.emt_lab.service.application.BookService;
import io.swagger.v3.oas.annotations.Operation;
import mk.ukim.finki.wp.emt_lab.service.domain.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book API", description = "Endpoints for CRUD in book") // Swagger tag
public class BookRESTController {

    private final BookService bookService;
    private final UserService userService;

    // In-memory statistics trackers
    private Map<String, Integer> rentedBooksCount = new HashMap<>(); // Key: Book ID, Value: Number of times rented
    private Map<String, Integer> authorRentedBooksCount = new HashMap<>(); // Key: Author ID, Value: Number of books rented by the author

    public BookRESTController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
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

    @PostMapping("/rent/{bookId}")
    public ResponseEntity<Book> rentBook(@PathVariable Long bookId) {
        Optional<Book> book = bookService.findById(bookId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.findByUsername(authentication.getName());

        if (book.isEmpty() || book.get().isRented() || book.get().getAvailableCopies() <= 0) {
            return ResponseEntity.notFound().build();
        }

        // Rent the book
        Book rentedBook = book.get();
        rentedBook.setRented(true);
        rentedBook.setAvailableCopies(rentedBook.getAvailableCopies() - 1);
        rentedBook.setRentedBy(user);

        updateRentingStatistics(rentedBook);

        // Create UpdateBookDto to send back
        UpdateBookDto bookDto = new UpdateBookDto(bookId, rentedBook.getName(), rentedBook.getCategory(),
                (List<Long>) rentedBook.getAuthors().stream().map(Author::getId).toList(),
                rentedBook.getAvailableCopies(), true, false);

        return bookService.update(bookId, bookDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private void updateRentingStatistics(Book book) {
        // Increment the total rented books counter in the in-memory map
        rentedBooksCount.put(book.getName(), rentedBooksCount.getOrDefault(book.getId(), 0) + 1);

        // Increment rented books per author
        for (Author author : book.getAuthors()) {
            authorRentedBooksCount.put(author.getName(),
                    authorRentedBooksCount.getOrDefault(author.getId(), 0) + 1);
        }
    }

    // Optionally, you can add some endpoints to view these statistics
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Integer>> getRentedBooksStatistics() {
        return ResponseEntity.ok(rentedBooksCount);
    }

    @GetMapping("/author-statistics")
    public ResponseEntity<Map<String, Integer>> getAuthorRentedBooksStatistics() {
        return ResponseEntity.ok(authorRentedBooksCount);
    }
}
