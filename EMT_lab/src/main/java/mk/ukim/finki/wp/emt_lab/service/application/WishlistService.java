package mk.ukim.finki.wp.emt_lab.service.application;

import mk.ukim.finki.wp.emt_lab.model.domain.Wishlist;

import java.util.Optional;

public interface WishlistService {
    Optional<Wishlist> getWishlistByUsername(String username);
    Optional<Wishlist> addBookToWishlist(String username, Long bookId);
    Optional<Wishlist> removeBookFromWishlist(String username, Long bookId);

    void refreshMaterialized();
}
