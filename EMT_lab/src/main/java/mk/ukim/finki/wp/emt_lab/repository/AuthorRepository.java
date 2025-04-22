package mk.ukim.finki.wp.emt_lab.repository;


import jdk.dynalink.linker.LinkerServices;
import mk.ukim.finki.wp.emt_lab.model.domain.Author;
import mk.ukim.finki.wp.emt_lab.model.projections.AuthorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {


    @Query("select a.name, a.surname from Author as a")
    List<AuthorProjection> getAuthorsByProjection();
}
