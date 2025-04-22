package mk.ukim.finki.wp.emt_lab.events;


import lombok.Getter;
import mk.ukim.finki.wp.emt_lab.model.domain.Author;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class AuthorCreatedEvent extends ApplicationEvent {

    private LocalDateTime creationDate;

    public AuthorCreatedEvent(Author source) {
        super(source);
        creationDate = LocalDateTime.now();
    }

    public AuthorCreatedEvent(Author source, LocalDateTime creationDate) {
        super(source);
        this.creationDate = creationDate;
    }
}
