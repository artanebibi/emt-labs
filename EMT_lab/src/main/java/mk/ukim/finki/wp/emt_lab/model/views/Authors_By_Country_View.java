package mk.ukim.finki.wp.emt_lab.model.views;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Data
@Entity
@Subselect("SELECT * FROM public.authors_per_country")
@Immutable

public class Authors_By_Country_View {
    @Id
    @Column(name = "country_id")
    private Long country_id;

    @Column(name = "author_count")
    private Integer author_count;
}
