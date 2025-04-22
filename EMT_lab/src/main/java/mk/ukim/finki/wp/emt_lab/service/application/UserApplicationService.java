package mk.ukim.finki.wp.emt_lab.service.application;

import mk.ukim.finki.wp.emt_lab.dto.User.CreateUserDto;
import mk.ukim.finki.wp.emt_lab.dto.User.DisplayUserDto;
import mk.ukim.finki.wp.emt_lab.dto.User.LoginResponseDto;
import mk.ukim.finki.wp.emt_lab.dto.User.LoginUserDto;

import java.util.Optional;

public interface UserApplicationService {
    Optional<DisplayUserDto> register(CreateUserDto createUserDto);

    Optional<LoginResponseDto> login(LoginUserDto loginUserDto);

    Optional<DisplayUserDto> findByUsername(String username);

}

