package com.helpet.service.pet.config;

import com.helpet.security.config.DefaultSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig extends DefaultSecurityConfig {
    private final String[] openApiPaths = { "/swagger-ui/**", "/api-docs/**" };

    @Override
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.requestMatchers(openApiPaths)
                                                                                                     .permitAll());

        return super.securityFilterChain(http);
    }
}
