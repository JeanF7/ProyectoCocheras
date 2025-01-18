package org.rensex.springcloud.msvc_cliente.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.config.Customizer.withDefaults;

/*
NUEVO
NUEVA CLASE PARA QUE CLIENTE TAMBIÉN TENGA AUTENTICACIÓN Y AUTORIZACIÓN Y REQUIERA DE UN TOKEN DE ACCESO
 */

@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> roles = jwt.getClaimAsStringList("roles");
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Agregamos el prefijo 'ROLE_' si es necesario
                    .collect(Collectors.toList());
        });
        return converter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests((http) -> http
                        .requestMatchers(HttpMethod.GET, "/api/cliente/authorized").permitAll()
                        /*
                        NUEVO
                        logramos implementar las correctas uris para cada microservicio
                        */
                        // Solo los usuarios con el role "ADMIN" pueden acceder a "/admin"
                        .requestMatchers(HttpMethod.GET, "/api/cliente/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/cliente/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/cliente/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/cliente/admin/**").hasAuthority("ROLE_ADMIN")

                        // Solo los usuarios con el role "USER" pueden acceder a "/user"
                        .requestMatchers(HttpMethod.GET, "/api/cliente/user/**").hasAuthority("ROLE_USER")

                        // Cualquier otra solicitud debe estar autenticada
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Configura sesión sin estado
                .oauth2Login(login -> login
                        .loginPage("/auth2/authorization/client-cochera")  // Página de login personalizada
                )
                .oauth2Client(withDefaults())
                .oauth2ResourceServer(resourceServer ->
                        resourceServer.jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))  // Aplica el convertidor de JWT
                );

        return httpSecurity.build();
    }

}
