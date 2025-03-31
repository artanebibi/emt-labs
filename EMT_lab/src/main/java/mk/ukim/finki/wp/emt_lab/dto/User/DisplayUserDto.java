package mk.ukim.finki.wp.emt_lab.dto.User;

import mk.ukim.finki.wp.emt_lab.model.domain.User;
import mk.ukim.finki.wp.emt_lab.model.enumerations.Role;

public record DisplayUserDto(String username, String name, String surname, Role role) {

    public static DisplayUserDto from(User user) {
        return new DisplayUserDto(
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getRole()
        );
    }

    public User toUser() {
        return new User(username, name, surname, role.name());
    }
}
