package mk.ukim.finki.wp.emt_lab.dto.User;


import mk.ukim.finki.wp.emt_lab.model.domain.User;
import mk.ukim.finki.wp.emt_lab.model.domain.Wishlist;
import mk.ukim.finki.wp.emt_lab.model.enumerations.Role;

public record CreateUserDto(
        String username,
        String password,
        String repeatPassword,
        String name,
        String surname,
        Role role,
        Wishlist wishlist
) {

    /*
        todo: add repeat password logic
     */
    public User toUser() {
        return new User(username, password, name, surname, role, wishlist);
    }
}
