package com.proyectoFinal.homebanking.tools.validations.serviceValidations;

import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.repositories.UserRepository;
import com.proyectoFinal.homebanking.tools.NotificationMessage;
import org.springframework.stereotype.Component;

@Component
public class UserServiceValidation {
    public static UserRepository repository;

    public UserServiceValidation(UserRepository repository) {
        UserServiceValidation.repository = repository;
    }

    public static String validateCreateUserDto(UserDTO dto) {
        if(UserServiceValidation.existUserByEmail(dto.getEmail()) )
            return NotificationMessage.userEmailAttributeExists(dto.getEmail());

        if( UserServiceValidation.existUserByUsername(dto.getUsername()) )
            return NotificationMessage.userUsernameExists(dto.getUsername());

        if( UserServiceValidation.existUserByDni(dto.getDni()) )
            return NotificationMessage.userDniExists(dto.getDni());

        return "OK";
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
