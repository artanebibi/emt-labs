package mk.ukim.finki.wp.emt_lab.service.application.impl;

import mk.ukim.finki.wp.emt_lab.dto.User.CreateUserDto;
import mk.ukim.finki.wp.emt_lab.dto.User.DisplayUserDto;
import mk.ukim.finki.wp.emt_lab.dto.User.LoginResponseDto;
import mk.ukim.finki.wp.emt_lab.dto.User.LoginUserDto;
import mk.ukim.finki.wp.emt_lab.model.domain.User;
import mk.ukim.finki.wp.emt_lab.security.JwtHelper;
import mk.ukim.finki.wp.emt_lab.service.application.UserApplicationService;
import mk.ukim.finki.wp.emt_lab.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("ApplicationUserApplicationServiceImpl")
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserService userService;
    private final JwtHelper jwtHelper;

    public UserApplicationServiceImpl(UserService userService, JwtHelper jwtHelper) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public Optional<DisplayUserDto> register(CreateUserDto createUserDto) {
        User user = userService.register(
                createUserDto.username(),
                createUserDto.password(),
                createUserDto.repeatPassword(),
                createUserDto.name(),
                createUserDto.surname(),
                createUserDto.role()
        );
        return Optional.of(DisplayUserDto.from(user));
    }

    @Override
    public Optional<LoginResponseDto> login(LoginUserDto loginUserDto) {
        User user = userService.login(
                loginUserDto.username(),
                loginUserDto.password()
        );

        String token = jwtHelper.generateToken(user);

        return Optional.of(new LoginResponseDto(token));
    }

    @Override
    public Optional<DisplayUserDto> findByUsername(String username) {
        return Optional.of(DisplayUserDto.from(userService.findByUsername(username)));
    }
}
