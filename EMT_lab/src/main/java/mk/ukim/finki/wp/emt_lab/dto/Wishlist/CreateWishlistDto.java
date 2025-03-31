package mk.ukim.finki.wp.emt_lab.dto.Wishlist;

import mk.ukim.finki.wp.emt_lab.model.domain.Book;
import mk.ukim.finki.wp.emt_lab.model.domain.Wishlist;

import java.util.List;
import java.util.stream.Collectors;


public record CreateWishlistDto(
        String title,
        List<Long> bookIds,
        String username
) {
    public static CreateWishlistDto from(Wishlist wishlist) {
        return new CreateWishlistDto(
                wishlist.getTitle(),
                wishlist.getBooks().stream().map(Book::getId).toList(),
                wishlist.getUser().getUsername()
        );
    }

    public static List<CreateWishlistDto> from(List<Wishlist> wishlists) {
        return wishlists.stream().map(CreateWishlistDto::from).collect(Collectors.toList());
    }
}