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
        // TODO: analizar logica de validacion del dto en el controlador. Porque nunca llega a mostrar estos mensajes.
        if(UserServiceValidation.existUserByEmail(dto.getEmail()) )
            throw new EntityAttributeExistsException( NotificationMessage.userEmailAttributeExists(dto.getEmail()) );

        if( UserServiceValidation.existUserByUsername(dto.getUsername()) )
            throw new EntityAttributeExistsException( NotificationMessage.userUsernameExists(dto.getUsername()) );

        if( UserServiceValidation.existUserByDni(dto.getDni()) )
            throw new EntityAttributeExistsException( NotificationMessage.userDniExists(dto.getDni()) );
    }

    public static void validateUpdateAllUser(Long id, UserDTO dto) throws FatalErrorException, EntityNotFoundException,
            EntityNullAttributesException {

        if(!existUserById(id) && !existsNullAttributes(dto))
            throw new FatalErrorException( NotificationMessage.userNotFoundAndNullAttributes(id) );

        if(!existUserById(id))
            throw new EntityNotFoundException( NotificationMessage.userNotFound(id) );

        if(!existsNullAttributes(dto))
            throw new EntityNullAttributesException( NotificationMessage.nullAttributes() );
    }

    public static boolean existsNullAttributes(UserDTO dto) {
        return dto.getEmail() != null &&
                dto.getPassword() != null &&
                dto.getName() != null &&
                dto.getUsername() != null &&
                dto.getDni() != null;
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

    public static Boolean existUserById(Long id) {
        return repository.existsById(id);
    }

    public static User findUserById(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow( () ->
                new EntityNotFoundException( NotificationMessage.userNotFound(id)) );
    }
}
