package org.jordonez.springboot.security.authserver.repositories;

import org.jordonez.springboot.security.authserver.models.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
