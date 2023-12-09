package com.proyectoFinal.homebanking.tools.validations.serviceValidations;

import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidation {
    public static UserRepository repository;

    public UserValidation(UserRepository repository) {
        UserValidation.repository = repository;
    }

    // Valida que existan usuarios unicos por mail
    public static Boolean existUserByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public static Boolean existUserByDni(String dni) {
        return repository.existsByDni(dni);
    }

    public static Boolean existUserByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public static boolean validateUserDtoAttributes(UserDTO dto) {
        return dto.getEmail() != null &&
                dto.getPassword() != null &&
                dto.getName() != null &&
                dto.getUsername() != null &&
                dto.getDni() != null;
    }
}
