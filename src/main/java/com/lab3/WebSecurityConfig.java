
package com.lab3;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
        @Value("${security.logins}")
        private String[] logins;
        @Value("${security.passwords}")
        private String[] passwords;
        @Value("${security.roles}")
        private String[] roles;
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests((requests) -> requests
                                                .requestMatchers("/", "/login.html").permitAll()
                                                .requestMatchers("/projects/change/**").hasRole("ADMIN")
                                                .anyRequest().hasAnyRole("USER", "ADMIN"))
                                .formLogin((form) -> form
                                                .loginPage("/login.html")
                                                .loginProcessingUrl("/login")
                                                .defaultSuccessUrl("/projects", true)
                                                .failureUrl("/login.html#error")
                                                .permitAll())
                                .logout(logout -> logout
                                                .permitAll() 
                                );

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public UserDetailsService userDetailsService() {
                List<UserDetails> list = new ArrayList<>();
                for(int i = 0; i<logins.length; i++)
                {
                        UserDetails user = User.withUsername(logins[i])
                        .password(passwordEncoder().encode(passwords[i]))
                        .roles(roles[i])
                        .build();
                        list.add(user);
                }
                return new InMemoryUserDetailsManager(list);
        }
}
