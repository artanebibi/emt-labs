package mk.ukim.finki.wp.emt_lab.repository;


import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.emt_lab.model.views.Books_By_Author_View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksPerAuthorViewRepository extends JpaRepository<Books_By_Author_View, Long> {

//    Books_By_Author_View findByAuthor_id(Long id);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "REFRESH MATERIALIZED VIEW public.books_by_author", nativeQuery = true)
    void refreshMaterialized();
}
