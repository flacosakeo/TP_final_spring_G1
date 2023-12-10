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
import com.proyectoFinal.homebanking.tools.validations.serviceValidations.UserValidation;
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
    
    public UserDTO createUser(UserDTO dto){
        if(UserValidation.existUserByEmail(dto.getEmail()) )
            throw new EntityAttributeExistsException( NotificationMessage.userEmailAttributeExists(dto.getEmail()));

        if( UserValidation.existUserByDni(dto.getDni()) )
            throw new EntityAttributeExistsException( NotificationMessage.userDniExists(dto.getDni()) );

        if( UserValidation.existUserByUsername(dto.getUsername()) )
            throw new EntityAttributeExistsException( NotificationMessage.userUsernameExists(dto.getDni()) );

        // Si llega hasta este punto es porque no existe ningún usuario con el mismo email, dni, y username. Puedo crearlo.
        User userSaved = repository.save(UserMapper.dtoToUser(dto));

        AccountDTO accountDTO1 = new AccountDTO();
        accountDTO1.setTipo(AccountType.CAJA_DE_AHORRO);
        //accountDTO1.setUser_id(userDTO.getId());
        servicioCuenta.createAccount( accountDTO1);

        return UserMapper.userToDto(userSaved);
    }
    
    public UserDTO getUserById(Long id){
        User entity = repository.findById(id).orElseThrow( () ->
                new EntityNotFoundException( NotificationMessage.userNotFound(id)) );
        return UserMapper.userToDto(entity);
    }

    public String deleteUser(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return NotificationMessage.userDeleted();
        }else{
            //TODO: retornar una excepcion(? Extrapolacion para todas las demas entidades...
            return NotificationMessage.userNotFound(id);
        }
    }
    
    public UserDTO updateUser(Long id, UserDTO dto){
        if(repository.existsById(id)){
            User userToModify = repository.findById(id).orElseThrow( () ->
                    new EntityNotFoundException( NotificationMessage.userNotFound(id)) );

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

    public UserDTO updateAllUser(Long id, UserDTO dto) {
        // Primero verifico si existe un usuario con ese id en la BD
        // Y tambien se valida que todos los datos del "dto" no vienen en null
        //TODO: refactor.. bajar esta logica del if debajo de todas las validaciones y
        // eliminar el IF, ya que parece que es innecesario al hacer tal cambio.
        // y se eliminaria el return null tambien.
        if(repository.existsById(id) && UserValidation.validateUserDtoAttributes(dto)) {

            // Consigo el usuario a modificar desde la BD
            User userToModify = repository.findById(id).orElseThrow( () ->
                    new EntityNotFoundException( NotificationMessage.userNotFound(id) ));

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

        if(!repository.existsById(id) && !UserValidation.validateUserDtoAttributes(dto))
            throw new FatalErrorException(NotificationMessage.userNotFoundAndNullAttributes(id));

        if(!repository.existsById(id))
            throw new EntityNotFoundException( NotificationMessage.userNotFound(id) );

        if(!UserValidation.validateUserDtoAttributes(dto))
            throw new EntityNullAttributesException( NotificationMessage.userNullAttributes());

        return null;
    }
}
