package mk.ukim.finki.wp.emt_lab.model.exceptions;

public class InvalidBookIdException extends RuntimeException{
    public InvalidBookIdException(){
        super("Invalid book ID");
    }
}
