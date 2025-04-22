package mk.ukim.finki.wp.emt_lab.jobs;

import mk.ukim.finki.wp.emt_lab.service.domain.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final BookService bookService;

    public ScheduledTasks(BookService bookService) {
        this.bookService = bookService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void refreshMaterializedView() {
        this.bookService.refreshMaterialized();
    }
}