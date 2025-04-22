package mk.ukim.finki.wp.emt_lab.listeners;


import mk.ukim.finki.wp.emt_lab.events.AuthorCreatedEvent;
import mk.ukim.finki.wp.emt_lab.service.domain.AuthorService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookEventHandler {
    private final AuthorService authorService;
    public BookEventHandler(AuthorService authorService) {
        this.authorService = authorService;
    }

    @EventListener
    public void onProductCreated(AuthorCreatedEvent event) {
        this.authorService.refreshMaterialized();
    }

}
