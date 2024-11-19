package org.jordonez.springcloud.msvc.personal.repositories;

import org.jordonez.springcloud.msvc.personal.entites.Personal;
import org.springframework.data.repository.CrudRepository;

public interface PersonalRepository extends CrudRepository<Personal, Long> {
}
