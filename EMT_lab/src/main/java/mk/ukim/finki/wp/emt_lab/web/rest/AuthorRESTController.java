package mk.ukim.finki.wp.emt_lab.web.rest;


import mk.ukim.finki.wp.emt_lab.model.Author;
import mk.ukim.finki.wp.emt_lab.model.Book;
import mk.ukim.finki.wp.emt_lab.model.dto.AuthorDto;
import mk.ukim.finki.wp.emt_lab.model.dto.BookDto;
import mk.ukim.finki.wp.emt_lab.service.AuthorService;
import mk.ukim.finki.wp.emt_lab.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/authors")
public class AuthorRESTController {
    public final AuthorService authorService;
    public final CountryService countryService;

    public AuthorRESTController(AuthorService authorService, CountryService countryService) {
        this.authorService = authorService;
        this.countryService = countryService;
    }


    @GetMapping("/")
    public List<Author> listAll() {
        return authorService.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable Long id) {
        return authorService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Author> add(@RequestBody AuthorDto authorDto) {
        return authorService.save(authorDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/edit/{id}")
    public ResponseEntity<Author> edit(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        return authorService.update(id, authorDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id) {
        if (authorService.findById(id).isPresent()) {
            authorService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
