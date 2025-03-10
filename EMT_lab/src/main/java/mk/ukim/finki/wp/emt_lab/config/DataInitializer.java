package mk.ukim.finki.wp.emt_lab.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.emt_lab.model.Author;
import mk.ukim.finki.wp.emt_lab.model.Category;
import mk.ukim.finki.wp.emt_lab.model.Country;
import mk.ukim.finki.wp.emt_lab.model.dto.BookDto;
import mk.ukim.finki.wp.emt_lab.service.AuthorService;
import mk.ukim.finki.wp.emt_lab.service.BookService;
import mk.ukim.finki.wp.emt_lab.service.CountryService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DataInitializer {

    private final BookService bookService;
    private final AuthorService authorService;
    private final CountryService countryService;

    public DataInitializer(BookService bookService, AuthorService authorService, CountryService countryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.countryService = countryService;
    }

    @PostConstruct
    public void initData() {
        Country usa = this.countryService.create("United States", "North America");
        Country uk = this.countryService.create("United Kingdom", "Europe");
        Country japan = this.countryService.create("Japan", "Asia");
        Country colombia = this.countryService.create("Colombia", "South America");
        Country nigeria = this.countryService.create("Nigeria", "Africa");

        Author king = this.authorService.create("Stephen", "King", usa.getId());
        Author murakami = this.authorService.create("Haruki", "Murakami", japan.getId());
        Author rowling = this.authorService.create("J.K.", "Rowling", uk.getId());
        Author marquez = this.authorService.create("Gabriel", "García Márquez", colombia.getId());
        Author adichie = this.authorService.create("Chimamanda", "Ngozi Adichie", nigeria.getId());

        // Using BookDto and save method
        bookService.save(new BookDto("The Shining", Category.THRILER, Collections.singletonList(king.getId()), 447, false));
        bookService.save(new BookDto("Kafka on the Shore", Category.CLASSICS, Collections.singletonList(murakami.getId()), 505, false));
        bookService.save(new BookDto("Harry Potter and the Philosopher's Stone", Category.FANTASY, Collections.singletonList(rowling.getId()), 223, false));
        bookService.save(new BookDto("One Hundred Years of Solitude", Category.HISTORY, Collections.singletonList(marquez.getId()), 417, false));
        bookService.save(new BookDto("Americanah", Category.THRILER, Collections.singletonList(adichie.getId()), 477, false));
    }
}
