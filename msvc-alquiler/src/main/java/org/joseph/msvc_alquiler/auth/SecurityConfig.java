package org.joseph.msvc_alquiler.auth;

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

@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Extraemos el claim 'roles' del JWT
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
                        // Permite acceso a todos a la ruta "/authorized"
                        .requestMatchers(HttpMethod.GET, "/authorized").permitAll()

                        // Solo los usuarios con el scope "SCOPE_read" pueden acceder a "/list"
                        // Descomentado como ejemplo para permisos de scope
//                .requestMatchers(HttpMethod.GET, "/user/listar").hasAuthority("SCOPE_read")

                        // Solo los usuarios con el scope "SCOPE_write" pueden acceder a "/create"
                        // Descomentado como ejemplo para permisos de scope
//                .requestMatchers(HttpMethod.POST, "/admin/crearAlquiler").hasAuthority("SCOPE_write")

                        // Solo los usuarios con el role "ADMIN" pueden acceder a "/admin"
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority("ROLE_ADMIN")

                        // Solo los usuarios con el role "USER" pueden acceder a "/user"
                        .requestMatchers(HttpMethod.GET, "/user/**").hasAuthority("ROLE_USER")

                        // Cualquier otra solicitud debe estar autenticada
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Configura sesión sin estado
                .oauth2Login(login -> login
                        .loginPage("/auth2/authorization/client-cochera")  // Página de login personalizada
                )
                .oauth2Client(withDefaults())  // Configura el cliente OAuth2
                .oauth2ResourceServer(resourceServer ->
                        resourceServer.jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))  // Aplica el convertidor de JWT
                );

        return httpSecurity.build();
    }

}
