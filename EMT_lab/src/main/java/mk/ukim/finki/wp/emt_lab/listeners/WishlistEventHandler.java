package mk.ukim.finki.wp.emt_lab.listeners;

import mk.ukim.finki.wp.emt_lab.events.AuthorCreatedEvent;
import mk.ukim.finki.wp.emt_lab.model.domain.Wishlist;
import mk.ukim.finki.wp.emt_lab.service.application.WishlistService;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;

public class WishlistEventHandler {

    private final WishlistService wishlistService;

    public WishlistEventHandler(WishlistService wishlistService) {
        this.wishlistService = wishlistService;

    }

    @EventListener
    public void onProductCreated(AuthorCreatedEvent event) {
        this.wishlistService.refreshMaterialized();
    }

}
