package mk.ukim.finki.wp.emt_lab.config.security;

import mk.ukim.finki.wp.emt_lab.security.CustomUsernamePasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Profile("test")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final CustomUsernamePasswordAuthenticationProvider authenticationProvider;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(CustomUsernamePasswordAuthenticationProvider authenticationProvider, PasswordEncoder passwordEncoder) {
        this.authenticationProvider = authenticationProvider;
        this.passwordEncoder = passwordEncoder;
    }

    // CORS Configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:2222"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    // Security Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .headers(AbstractHttpConfigurer::disable) // Allow H2 iframes
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/h2/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/user/register",
                                "/api/user/login"
                        ).permitAll()
                        .anyRequest().authenticated()
                );
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
//                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers(
//                                "api/user/login",
//                                "api/user/register"
//                        ).permitAll()
//                        .requestMatchers("/books/**").hasRole("LIBRARIAN") // Only librarians can manage books
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginProcessingUrl("/api/user/login")
//                        .permitAll()
//                        .failureUrl("/api/user/login?error=BadCredentials")
////                        .defaultSuccessUrl("/swagger-ui/index.html", true)
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/api/user/logout")
//                        .clearAuthentication(true)
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
////                        .logoutSuccessUrl("/api/user/login")
//                )
//                .exceptionHandling(ex -> ex.accessDeniedPage("/access_denied"));

        return http.build();
    }

    // Authentication Manager
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationManagerBuilder.build();
    }

    // UserDetailsService (In-memory User Configuration)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails librarian = User.builder()
                .username("librarian")
                .password(passwordEncoder.encode("librarian"))
                .roles("LIBRARIAN")
                .build();
        UserDetails user = User.builder()
                .username("customer")
                .password(passwordEncoder.encode("customer"))
                .roles("CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(librarian, user);
    }
}
