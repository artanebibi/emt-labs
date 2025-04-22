package mk.ukim.finki.wp.emt_lab.model.views;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Data
@Entity
@Subselect("SELECT * FROM public.books_by_author")
@Immutable
public class Books_By_Author_View {
    @Id
    @Column(name = "author_id")
    private Long author_id;

    @Column(name = "book_count")
    private Integer book_count;
}
