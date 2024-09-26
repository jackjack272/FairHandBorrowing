package com.example.fairhandborrowing.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableGlobalAuthentication
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService userService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                authorizeRequests -> {
                    try {
                        authorizeRequests
                            .requestMatchers("/login", "/register", "/css/**", "/js/**", "/layout/**", "legal/**")
                            .permitAll()
                            .and()
                            .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/home").loginProcessingUrl("/login").failureUrl("/login?error=true").permitAll())
                            .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}
