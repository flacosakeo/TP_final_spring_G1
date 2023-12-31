package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.*;
import com.proyectoFinal.homebanking.mappers.UserMapper;
import com.proyectoFinal.homebanking.models.DTO.AccountDTO;
import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.models.Enum.AccountType;
import com.proyectoFinal.homebanking.models.User;
import com.proyectoFinal.homebanking.repositories.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

import com.proyectoFinal.homebanking.tools.NotificationMessage;
import com.proyectoFinal.homebanking.tools.validations.serviceValidations.UserServiceValidation;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final AccountService serviceAccount;

    public UserService(UserRepository repository, AccountService serviceAccount) {
        this.repository = repository;
        this.serviceAccount = serviceAccount;
    }

    public List<UserDTO> getUsers() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(UserMapper::userToDto)                
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) throws EntityNotFoundException {
        User entity = UserServiceValidation.findUserById(id);
        return UserMapper.userToDto(entity);
    }

    public UserDTO createUser(UserDTO dto) throws EntityAttributeExistsException {
        UserServiceValidation.validateCreateUserDto(dto);

        // Si llega hasta este punto es porque no existe ningún usuario con el mismo email, dni, y username. Puedo crearlo.
        User userSaved = repository.save(UserMapper.dtoToUser(dto));

        AccountDTO accountDto = new AccountDTO();
        accountDto.setAccountType(AccountType.SAVINGS_BANK);
        accountDto.setOwnerId(userSaved.getId());
        serviceAccount.createAccount(accountDto);

        return UserMapper.userToDto(userSaved);
    }

    public String deleteUser(Long id) throws EntityNotFoundException {
        if ( !UserServiceValidation.existUserById(id) ){
            throw new EntityNotFoundException( NotificationMessage.userNotFound(id) );
        }

        repository.deleteById(id);
        return NotificationMessage.userDeleted();
    }
    
    public UserDTO updateAllUser(Long id, UserDTO dto) throws FatalErrorException, EntityNotFoundException,
            EntityNullAttributesException, InvalidAttributeException {

        // Primero verifico si existe un usuario con ese id en la BD
        UserServiceValidation.validateUpdateAllUser(id, dto);

        // Si llega hasta aquí es porque ya se valido el dto. Entonces consigo el usuario a modificar desde la BD
        User userToModify = UserServiceValidation.findUserById(id);

        // LÓGICA DEL PUT
        userToModify.setEmail(dto.getEmail());
        userToModify.setPassword(dto.getPassword());
        userToModify.setName(dto.getName());
        userToModify.setUsername(dto.getUsername());
        userToModify.setDni(dto.getDni());

        // Persistimos la modificacion del usuario en la BD
        User userModified = repository.save(userToModify);

        return UserMapper.userToDto(userModified);
    }

    public UserDTO updateUser(Long id, UserDTO dto) throws EntityNotFoundException {
        if( !UserServiceValidation.existUserById(id) ) {
            throw new EntityNotFoundException( NotificationMessage.userNotFound(id) );
        }

        User userToModify = UserServiceValidation.findUserById(id);

        // LÓGICA DEL PATCH
        if(dto.getName() != null)
            userToModify.setName(dto.getName());

        if(dto.getUsername() != null)
            userToModify.setUsername(dto.getUsername());

        if(dto.getDni() != null)
            userToModify.setDni(dto.getDni());

        if(dto.getEmail() != null)
            userToModify.setEmail(dto.getEmail());

        if(dto.getPassword() != null)
            userToModify.setPassword(dto.getPassword());

        User userModified = repository.save(userToModify);
        return UserMapper.userToDto(userModified);
    }
}
