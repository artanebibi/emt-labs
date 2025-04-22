package mk.ukim.finki.wp.emt_lab.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.emt_lab.dto.User.CreateUserDto;
import mk.ukim.finki.wp.emt_lab.dto.User.DisplayUserDto;
import mk.ukim.finki.wp.emt_lab.dto.User.LoginResponseDto;
import mk.ukim.finki.wp.emt_lab.dto.User.LoginUserDto;
import mk.ukim.finki.wp.emt_lab.dto.Wishlist.CreateWishlistDto;
import mk.ukim.finki.wp.emt_lab.dto.Wishlist.UpdateWishlistDto;
import mk.ukim.finki.wp.emt_lab.model.domain.User;
import mk.ukim.finki.wp.emt_lab.model.domain.Wishlist;
import mk.ukim.finki.wp.emt_lab.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wp.emt_lab.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wp.emt_lab.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wp.emt_lab.service.application.UserApplicationService;
import mk.ukim.finki.wp.emt_lab.service.application.WishlistService;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User API", description = "Endpoints for user authentication, registration, and wishlist management")
public class UserController {

    private final UserApplicationService userApplicationService;
    private final WishlistService wishlistService;

    public UserController(UserApplicationService userApplicationService, WishlistService wishlistService) {
        this.userApplicationService = userApplicationService;
        this.wishlistService = wishlistService;
    }


    @Operation(summary = "Register a new user", description = "Creates a new user account")
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully"
            ), @ApiResponse(
                    responseCode = "400", description = "Invalid input or passwords do not match"
            )}
    )
    @PostMapping("/register")
    public ResponseEntity<DisplayUserDto> register(@RequestBody CreateUserDto createUserDto) {
        try {
            return userApplicationService.register(createUserDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "User login", description = "Authenticates a user and generates a JWT")
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200",
                    description = "User authenticated successfully"
            ), @ApiResponse(responseCode = "404", description = "Invalid username or password")}
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            return userApplicationService.login(loginUserDto)
                    .map(ResponseEntity::ok)
                    .orElseThrow(InvalidUserCredentialsException::new);
        } catch (InvalidUserCredentialsException e) {
            return ResponseEntity.notFound().build();
        }
    }


//    @Operation(summary = "User logout", description = "Ends the user's session")
//    @ApiResponse(responseCode = "200", description = "User logged out successfully")
//    @GetMapping("/logout")
//    public void logout(HttpServletRequest request) {
//        request.getSession().invalidate();
//    }

    // Wishlist Management

    @Operation(summary = "Get user's wishlist", description = "Retrieves the wishlist of the authenticated user")
    @ApiResponse(responseCode = "200", description = "Wishlist retrieved successfully")
    @GetMapping("/wishlist")
    public ResponseEntity<Wishlist> getWishlist() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return wishlistService.getWishlistByUsername(authentication.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a book to the wishlist", description = "Adds a book to the user's wishlist if copies are available")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book added to wishlist"),
            @ApiResponse(responseCode = "400", description = "No copies available")
    })


    @PostMapping("/wishlist/add")
    public ResponseEntity<Wishlist> addToWishlist(@RequestParam Long bookId) {
        if (bookId == null || bookId <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return wishlistService.addBookToWishlist(authentication.getName(), bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }


    @Operation(summary = "Remove a book from the wishlist", description = "Removes a book from the user's wishlist")
    @ApiResponse(responseCode = "200", description = "Book removed from wishlist")
    @DeleteMapping("/wishlist/remove")
    public ResponseEntity<Wishlist> removeFromWishlist(@RequestParam Long bookId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        return wishlistService.removeBookFromWishlist(authentication.getName(), bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
