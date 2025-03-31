package mk.ukim.finki.wp.emt_lab.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.wp.emt_lab.dto.Author.CreateAuthorDto;
import mk.ukim.finki.wp.emt_lab.dto.Author.UpdateAuthorDto;
import mk.ukim.finki.wp.emt_lab.model.domain.Author;
import mk.ukim.finki.wp.emt_lab.service.application.AuthorService;
import mk.ukim.finki.wp.emt_lab.service.application.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Author API", description = "Endpoints for CRUD operations on authors") // Swagger tag
public class AuthorRESTController {
    private final AuthorService authorService;
    private final CountryService countryService;

    public AuthorRESTController(AuthorService authorService, CountryService countryService) {
        this.authorService = authorService;
        this.countryService = countryService;
    }

    @Operation(summary = "List all authors", description = "Retrieve a list of all authors in the system.")
    @GetMapping
    public List<Author> listAll() {
        return authorService.findAll();
    }

    @Operation(summary = "Find an author by ID", description = "Retrieve details of a specific author by their ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable Long id) {
        return authorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new author", description = "Create and add a new author to the system.")
    @PostMapping("/add")
    public ResponseEntity<Author> add(@RequestBody CreateAuthorDto createAuthorDto) {
        return authorService.save(createAuthorDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Edit an existing author", description = "Update the details of an existing author using their unique ID.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Author> edit(@PathVariable Long id, @RequestBody UpdateAuthorDto updateAuthorDto) {
        return authorService.update(id, updateAuthorDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an author", description = "Delete an existing author from the system by their unique ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (authorService.findById(id).isPresent()) {
            authorService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
