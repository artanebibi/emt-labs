package mk.ukim.finki.wp.emt_lab.model.exceptions;

public class InvalidAuthorIdException extends RuntimeException {
    public InvalidAuthorIdException() {
        super("invalid Author ID");
    }
}
