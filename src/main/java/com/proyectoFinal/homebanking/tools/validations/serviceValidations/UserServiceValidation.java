package com.proyectoFinal.homebanking.tools.validations.serviceValidations;

import com.proyectoFinal.homebanking.exceptions.EntityAttributeExistsException;
import com.proyectoFinal.homebanking.exceptions.EntityNotFoundException;
import com.proyectoFinal.homebanking.exceptions.EntityNullAttributesException;
import com.proyectoFinal.homebanking.exceptions.FatalErrorException;
import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.models.User;
import com.proyectoFinal.homebanking.repositories.UserRepository;
import com.proyectoFinal.homebanking.tools.NotificationMessage;
import org.springframework.stereotype.Component;

@Component
public class UserServiceValidation {
    public static UserRepository repository;

    public UserServiceValidation(UserRepository repository) {
        UserServiceValidation.repository = repository;
    }

    public static void validateCreateUserDto(UserDTO dto) throws EntityAttributeExistsException {
        if(UserServiceValidation.existUserByEmail(dto.getEmail()) )
            throw new EntityAttributeExistsException( NotificationMessage.userEmailAttributeExists(dto.getEmail()) );

        if( UserServiceValidation.existUserByUsername(dto.getUsername()) )
            throw new EntityAttributeExistsException( NotificationMessage.userUsernameExists(dto.getUsername()) );

        if( UserServiceValidation.existUserByDni(dto.getDni()) )
            throw new EntityAttributeExistsException( NotificationMessage.userDniExists(dto.getDni()) );
    }

    public static void validateUpdateAllUser(UserDTO dto) throws FatalErrorException, EntityNotFoundException,
            EntityNullAttributesException {

        if(!UserServiceValidation.existUserById(dto.getId()) && !UserServiceValidation.validateUserDtoAttributes(dto))
            throw new FatalErrorException( NotificationMessage.userNotFoundAndNullAttributes(dto.getId()) );

        if(!repository.existsById(dto.getId()))
            throw new EntityNotFoundException( NotificationMessage.userNotFound(dto.getId()) );

        if(!UserServiceValidation.validateUserDtoAttributes(dto))
            throw new EntityNullAttributesException( NotificationMessage.userNullAttributes() );
    }

    public static User findUserById(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow( () ->
                new EntityNotFoundException( NotificationMessage.userNotFound(id)) );
    }

    public static Boolean existUserById(Long id) {
        return repository.existsById(id);
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
