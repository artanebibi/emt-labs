package mk.ukim.finki.wp.emt_lab.repository;

import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.emt_lab.model.views.User_Author_RentedBooks_View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthorRentedBooksRepository extends JpaRepository<User_Author_RentedBooks_View, String> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "REFRESH MATERIALIZED VIEW public.user_author_rentedBooks", nativeQuery = true)
    void refreshMaterialized();
}


