package mk.ukim.finki.wp.emt_lab.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.emt_lab.dto.Book.CreateBookDto;
import mk.ukim.finki.wp.emt_lab.model.domain.Author;
import mk.ukim.finki.wp.emt_lab.model.domain.User;
import mk.ukim.finki.wp.emt_lab.model.enumerations.Category;
import mk.ukim.finki.wp.emt_lab.model.domain.Country;
import mk.ukim.finki.wp.emt_lab.repository.UserRepository;
import mk.ukim.finki.wp.emt_lab.service.application.AuthorService;
import mk.ukim.finki.wp.emt_lab.service.application.BookService;
import mk.ukim.finki.wp.emt_lab.service.application.CountryService;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Component;

import java.util.Collections;
import org.springframework.security.crypto.password.PasswordEncoder;
import mk.ukim.finki.wp.emt_lab.model.enumerations.Role;

@Component
public class DataInitializer {

    private final BookService bookService;
    private final AuthorService authorService;
    private final CountryService countryService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public DataInitializer(BookService bookService, AuthorService authorService, CountryService countryService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.countryService = countryService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        bookService.save(new CreateBookDto("The Shining", Category.THRILER, Collections.singletonList(king.getId()), 0, false, false));
        bookService.save(new CreateBookDto("Kafka on the Shore", Category.CLASSICS, Collections.singletonList(murakami.getId()), 505, false, false));
        bookService.save(new CreateBookDto("Harry Potter and the Philosopher's Stone", Category.FANTASY, Collections.singletonList(rowling.getId()), 223, false, false));
        bookService.save(new CreateBookDto("One Hundred Years of Solitude", Category.HISTORY, Collections.singletonList(marquez.getId()), 417, false, false));
        bookService.save(new CreateBookDto("Americanah", Category.THRILER, Collections.singletonList(adichie.getId()), 477, false, false));

        userRepository.save(new User(
                "librarian",
                passwordEncoder.encode("librarian"),
                "Artan",
                "Ebibi",
                Role.ROLE_LIBRARIAN
        ));

    }
}
