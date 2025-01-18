package org.renato.sprincloud.msvc.espacio.msvc_espacio.auth;

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
 se implementó en cada uno de los microservicios la autenticación
*/
@Configuration
public class SecurityConfig {

    /**
     * Convierte los claims de roles del JWT en autoridades reconocidas por Spring Security.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Extraemos el claim 'roles' del JWT
            List<String> roles = jwt.getClaimAsStringList("roles");
            if (roles == null) {
                return List.of(); // Si no hay roles, devuelve una lista vacía
            }
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Agregamos el prefijo 'ROLE_' si es necesario
                    .collect(Collectors.toList());
        });
        return converter;
    }

    /**
     * Configura las reglas de seguridad para las rutas del microservicio.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        // Permite acceso público a la ruta "/status" (para verificar el estado del servicio)
                        .requestMatchers(HttpMethod.GET, "/api/espacios/status").permitAll()

                        // Solo los usuarios con el rol "ADMIN" pueden acceder a las rutas administrativas
                        .requestMatchers(HttpMethod.GET, "/api/espacios/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/espacios/admin/**").hasAuthority("ROLE_ADMIN")

                        // Solo los usuarios con el rol "USER" pueden acceder a las rutas de usuario
                        .requestMatchers(HttpMethod.GET, "/api/espacios/user/**").hasAuthority("ROLE_USER")

                        // Cualquier otra solicitud debe estar autenticada
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF para simplificar la integración con JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesiones de servidor
                .oauth2ResourceServer(resourceServer -> resourceServer
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())) // Valida JWT con el convertidor personalizado
                );

        return httpSecurity.build();
    }
}
