package org.joseph.msvc_alquiler.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests((http) -> http
                // Permite acceso a todos a la ruta "/authorized"
                .requestMatchers(HttpMethod.GET, "/authorized").permitAll()

                // Solo los usuarios con los scopes "SCOPE_read" o "SCOPE_write" pueden acceder a "/list"
//                .requestMatchers(HttpMethod.GET, "/user/listar").hasAnyAuthority("SCOPE_read")

                // Solo los usuarios con el scope "SCOPE_write" pueden acceder a "/create"
//                .requestMatchers(HttpMethod.POST, "/admin/crearAlquiler").hasAuthority("SCOPE_write")

                // Solo los usuarios con el scope "SCOPE_admin" pueden acceder a "/admin"
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority("SCOPE_admin")

                // Solo los usuarios con el scope "SCOPE_user" pueden acceder a "/user"
                        .requestMatchers(HttpMethod.GET, "/user/**").hasAuthority("SCOPE_user")

                // Cualquier otra solicitud debe estar autenticada
                .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Configura sesión sin estado
                .oauth2Login(login -> login
                        .loginPage("/auth2/authorization/client-cochera")  // Página de login personalizada
                )
                .oauth2Client(withDefaults())  // Configura el cliente OAuth2
                .oauth2ResourceServer(resourceServer ->
                        resourceServer.jwt(withDefaults())  // Configura el servidor de recursos con JWT
                );

        return httpSecurity.build();
    }
}
