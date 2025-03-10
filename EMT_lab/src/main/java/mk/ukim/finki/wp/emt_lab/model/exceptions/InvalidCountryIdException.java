package mk.ukim.finki.wp.emt_lab.model.exceptions;

public class InvalidCountryIdException extends RuntimeException {
    public InvalidCountryIdException() {
        super("Invalid country ID");
    }
}
