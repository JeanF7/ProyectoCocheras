package org.jordonez.springboot.security.authserver.repositories;

import org.jordonez.springboot.security.authserver.models.entities.User;
import org.springframework.data.repository.CrudRepository;

/*
NUEVO
REPOSITORIO DE USUARIO PARA PODER BUSCAR LOS NOMBRES POR USUARIO
AL REALIZAR LA AUTENTICACIÓN
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
