package com.github.matheuswwwp.dinenow.conf.web;

import com.github.matheuswwwp.dinenow.conf.jwt.JwtTokenFilter;
import com.github.matheuswwwp.dinenow.conf.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Bean
    PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> enconders = new HashMap<>();
        Pbkdf2PasswordEncoder pbkdf2Enconder = new Pbkdf2PasswordEncoder("", 8, 18500, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);
        enconders.put("pbkdf2", pbkdf2Enconder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", enconders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Enconder);
        return passwordEncoder;
    }

    @Bean
    AuthenticationManager authenticationManagerBean(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);

        return http.httpBasic(basic -> basic.disable()).
                csrf(csrf -> csrf.disable()).
                addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class).
                sessionManagement((
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )).
                authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers("/dish/createDish").hasAuthority("ADMIN")
                                .requestMatchers("/dish/updateDish").hasAuthority("ADMIN")
                                .requestMatchers("/dish/deleteDish").hasAuthority("ADMIN")
                                .requestMatchers("/dish/getAll").permitAll()
                                .requestMatchers("/dish/getById").permitAll()

                                .requestMatchers("/user/auth/**").permitAll()
                                .requestMatchers("/admin/auth/**").permitAll()
                                .requestMatchers("/image/**").permitAll()
                ).
                cors(cors -> {}).
                build();
    }
}
