package mk.ukim.finki.wp.emt_lab.dto.User;

import mk.ukim.finki.wp.emt_lab.model.domain.User;
import mk.ukim.finki.wp.emt_lab.model.enumerations.Role;

public record DisplayUserDto(String username, String name, String surname, Role role,
                             mk.ukim.finki.wp.emt_lab.model.domain.Wishlist wishlist) {

    public static DisplayUserDto from(User user) {
        return new DisplayUserDto(
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getRole(),
                user.getWishlist()
        );
    }

    public User toUser() {
        return new User(username, name, surname, role, wishlist);
    }
}
