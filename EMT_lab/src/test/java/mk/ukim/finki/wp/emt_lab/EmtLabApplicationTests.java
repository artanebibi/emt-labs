package mk.ukim.finki.wp.emt_lab;

import mk.ukim.finki.wp.emt_lab.model.projections.AuthorProjection;
import mk.ukim.finki.wp.emt_lab.repository.AuthorRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EmtLabApplicationTests {

    private final AuthorRepository authorRepository;

    EmtLabApplicationTests(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testProjectUsernameAndNameAndSurname() {
//        List<AuthorProjection> a = this.authorRepository.getAuthorsByProjection();
//        Assert.assertEquals("user", .getName());
//        Assert.assertEquals("user", userProjection.getSurname());
    }


}
