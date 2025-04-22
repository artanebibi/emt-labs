package mk.ukim.finki.wp.emt_lab.model.views;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Data
@Entity
@Subselect("SELECT * FROM public.user_author_rentedBooks")
@Immutable
public class User_Author_RentedBooks_View {
    @Id
    @Column(name = "user_username")
    String username;

    @Column(name = "author_name")
    String author_name;

    @Column(name = "number_books_rented")
    Integer number_books_rented;
}
