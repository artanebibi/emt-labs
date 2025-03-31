package mk.ukim.finki.wp.emt_lab.model.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long bookId) {
        super("Book with ID " + bookId + " not found.");
    }
}