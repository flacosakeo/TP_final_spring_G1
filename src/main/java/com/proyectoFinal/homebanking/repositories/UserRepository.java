
package com.proyectoFinal.homebanking.repositories;

import com.proyectoFinal.homebanking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);

    Boolean existsByEmail(String email);

    User findByDni(String dni);

    Boolean existsByDni(String dni);

    Boolean existsByUsername(String username);
}
