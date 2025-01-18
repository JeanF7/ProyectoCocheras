package org.jordonez.springboot.security.authserver.services;

import org.jordonez.springboot.security.authserver.models.entities.User;
import org.jordonez.springboot.security.authserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/*
NUEVO
IMPLEMENTACIÓN DE LA INTERFAZ DE USERDETAILSSERVICE
- LA INTERFAZ ESTA DADA POR DEFECTO POR EL SPRING SECURITY
POR ENDE, SOLO ES NECESARIO LA IMPLEMENTACION Y SOBREESCRIBIR UN
MÉTODO YA EXISTENTE
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", ""))
                .build();
    }
}