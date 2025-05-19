package az.company.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/v1/auth/login").permitAll() // Allow login endpoint without token
                        .anyExchange().authenticated()             // Everything else requires valid Google Bearer token
                )
                .oauth2Login(Customizer.withDefaults())        // For browser-based login, optional
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())            // This will validate Google's JWT access tokens
                );
        return http.build();
    }
}