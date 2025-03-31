package mk.ukim.finki.wp.emt_lab.dto.Wishlist;

import mk.ukim.finki.wp.emt_lab.model.domain.Book;
import mk.ukim.finki.wp.emt_lab.model.domain.Wishlist;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record UpdateWishlistDto(
        String title,
        List<Long> bookIds
) {
    public static UpdateWishlistDto from(Wishlist wishlist) {
        return new UpdateWishlistDto(
                wishlist.getTitle(),
                wishlist.getBooks().stream().map(Book::getId).toList()
        );
    }

    public static List<UpdateWishlistDto> from(List<Wishlist> wishlists) {
        return wishlists.stream().map(UpdateWishlistDto::from).collect(Collectors.toList());
    }
}