package mk.ukim.finki.wp.emt_lab.model.exceptions;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User with username " + username + " not found.");
    }
}