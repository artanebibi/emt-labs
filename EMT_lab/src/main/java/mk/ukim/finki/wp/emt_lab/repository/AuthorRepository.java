package mk.ukim.finki.wp.emt_lab.repository;


import mk.ukim.finki.wp.emt_lab.model.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
