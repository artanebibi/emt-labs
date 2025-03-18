package mk.ukim.finki.wp.emt_lab.repository;

import mk.ukim.finki.wp.emt_lab.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepository extends JpaSpecificationRepository<Book, Long> {

    // Custom query to find a book by its name and author name
    @Query("select b from Book b " +
            "join b.authors a " +
            "where b.name = :bookName or a.name = :authorName")
    List<Book> findByBookNameAndAuthorName(@Param("bookName") String bookName,
                                           @Param("authorName") String authorName);


    @Query("select b from Book as b where b.isDeleted = false")
    List<Book> findAll();
}
