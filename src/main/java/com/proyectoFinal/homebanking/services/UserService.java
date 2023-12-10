package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.EntityNotFoundException;
import com.proyectoFinal.homebanking.exceptions.EntityAttributeExistsException;
import com.proyectoFinal.homebanking.exceptions.EntityNullAttributesException;
import com.proyectoFinal.homebanking.exceptions.FatalErrorException;
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
    private final AccountService servicioCuenta;

    public UserService(UserRepository repository, AccountService servicioCuenta) {
        this.repository = repository;
        this.servicioCuenta = servicioCuenta;

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
        servicioCuenta.createAccount(accountDto);

        return UserMapper.userToDto(userSaved);
    }

    public String deleteUser(Long id) throws EntityNotFoundException {
        if (UserServiceValidation.existUserById(id)){
            repository.deleteById(id);
            return NotificationMessage.userDeleted();
        }else{
            throw new EntityNotFoundException( NotificationMessage.userNotFound(id) );
        }
    }
    
    public UserDTO updateAllUser(Long id, UserDTO dto) throws FatalErrorException, EntityNotFoundException,
            EntityNullAttributesException {

        // Primero verifico si existe un usuario con ese id en la BD
        // Y tambien se valida que todos los datos del "dto" no vienen en null
        UserServiceValidation.validateUpdateAllUser(dto);

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
        if(UserServiceValidation.existUserById(id)){
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
        throw new EntityNotFoundException( NotificationMessage.userNotFound(id) );
    }
}
