package mk.ukim.finki.wp.emt_lab.service.application.impl;

import mk.ukim.finki.wp.emt_lab.model.domain.Book;
import mk.ukim.finki.wp.emt_lab.model.domain.User;
import mk.ukim.finki.wp.emt_lab.model.domain.Wishlist;
import mk.ukim.finki.wp.emt_lab.model.exceptions.BookNotFoundException;
import mk.ukim.finki.wp.emt_lab.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wp.emt_lab.repository.BookRepository;
import mk.ukim.finki.wp.emt_lab.repository.UserAuthorRentedBooksRepository;
import mk.ukim.finki.wp.emt_lab.repository.UserRepository;
import mk.ukim.finki.wp.emt_lab.repository.WishlistRepository;
import mk.ukim.finki.wp.emt_lab.service.application.WishlistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserAuthorRentedBooksRepository userAuthorRentedBooksRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, UserRepository userRepository, BookRepository bookRepository, UserAuthorRentedBooksRepository userAuthorRentedBooksRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userAuthorRentedBooksRepository = userAuthorRentedBooksRepository;
    }

    @Override
    public Optional<Wishlist> getWishlistByUsername(String username) {
        return wishlistRepository.findByUser_Username(username);
    }

    @Transactional
    @Override
    public Optional<Wishlist> addBookToWishlist(String username, Long bookId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if (book.getAvailableCopies() <= 0) {
            return Optional.empty();
        }

        Wishlist wishlist = wishlistRepository.findByUser_Username(username)
                .orElse(new Wishlist(null, "My Wishlist", new java.util.ArrayList<>(), user));

        if (!wishlist.getBooks().contains(book)) {
            wishlist.getBooks().add(book);
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            wishlist = wishlistRepository.save(wishlist);
        }

        return Optional.of(wishlist);
    }

    @Transactional
    @Override
    public Optional<Wishlist> removeBookFromWishlist(String username, Long bookId) {
        Wishlist wishlist = wishlistRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        wishlist.getBooks().remove(book);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        wishlistRepository.save(wishlist);

        return Optional.of(wishlist);
    }

    @Override
    public void refreshMaterialized() {
        this.userAuthorRentedBooksRepository.refreshMaterialized();
    }
}
