package com.example.computershop.config;


import com.example.computershop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/login.html").permitAll()
                        .requestMatchers("/register.html").permitAll()
                        .requestMatchers("/customer-index.html").permitAll()
                        .requestMatchers("/customer-cart.html").permitAll()
                        .requestMatchers("/admin-index.html").permitAll()
                        .requestMatchers("/admin-laptop.html").permitAll()
                        .requestMatchers("/admin-pc.html").permitAll()
                        .requestMatchers("/admin-laptop-table.html").permitAll()
                        .requestMatchers("/admin-pc-table.html").permitAll()
                        .requestMatchers("/admin-printer.html").permitAll()
                        .requestMatchers("/admin-printer-table.html").permitAll()
                        .requestMatchers("/customer-index.html").permitAll()
                        .requestMatchers("/customer-pc.html").permitAll()
                        .requestMatchers("/customer-pc-table.html").permitAll()
                        .requestMatchers("/customer-laptop.html").permitAll()
                        .requestMatchers("/customer-laptop-table.html").permitAll()
                        .requestMatchers("/customer-printer-table.html").permitAll()
                        .requestMatchers("/customer-printer.html").permitAll()
                        .requestMatchers("/images/customer.jpg").permitAll()
                        .requestMatchers("/images/навоз.png").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()


                        .anyRequest().permitAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}