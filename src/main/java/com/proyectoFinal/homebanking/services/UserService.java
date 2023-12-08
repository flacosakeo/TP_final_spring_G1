package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.EntityNotFoundException;
import com.proyectoFinal.homebanking.exceptions.EntityAttributeExistsException;
import com.proyectoFinal.homebanking.exceptions.EntityNullAttributesException;
import com.proyectoFinal.homebanking.exceptions.FatalErrorException;
import com.proyectoFinal.homebanking.mappers.UserMapper;
import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.models.User;
import com.proyectoFinal.homebanking.repositories.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserDTO> getUsers() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(UserMapper::userToDto)                
                .collect(Collectors.toList());
    }
    
    public UserDTO createUser(UserDTO dto){
        if( existUserByEmail(dto.getEmail()) )
            throw new EntityAttributeExistsException("¡Email " + dto.getEmail() + " ya registrado!");

        if( existUserByDni(dto.getDni()) )
            throw new EntityAttributeExistsException("¡Ya existe un usuario con el DNI: " + dto.getDni() + "!");

        if( existUserByUsername(dto.getUsername()) )
            throw new EntityAttributeExistsException("¡Ya existe un usuario con el USERNAME " + dto.getUsername() + "!");

        // Si llega hasta este punto es porque no existe ningún usuario con el mismo email, dni, y username. Puedo crearlo.
        User userSaved = repository.save(UserMapper.dtoToUser(dto));
        return UserMapper.userToDto(userSaved);
    }
    
    public UserDTO getUserById(Long id){
        User entity = repository.findById(id).orElseThrow( () ->
                new EntityNotFoundException("¡El usuario con ID '" + id + "' NO fue encontrado!"));
        return UserMapper.userToDto(entity);
    }

    public String deleteUser(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "Eliminado";
        }else{
            //throw new  UserNotExistsException("Usuario no existe");
            return "Usuario no existe";
        }
    }
    
    public UserDTO updateUser(Long id, UserDTO dto){
        if(repository.existsById(id)){
            User userToModify = repository.findById(id).get();

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
        throw new EntityNotFoundException("¡El usuario con ID '" + id + "' no fue encontrado!");
    }

    public UserDTO updateAllUser(Long id, UserDTO dto) {
        // Primero verifico si existe un usuario con ese id en la BD
        // Y tambien se valida que todos los datos del "dto" no vienen en null
        if(repository.existsById(id) && validateUserDtoAttributes(dto)) {

            // Consigo el usuario a modificar desde la BD
            User userToModify = repository.findById(id).get();

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

        if(!repository.existsById(id) && !validateUserDtoAttributes(dto))
            throw new FatalErrorException("El usuario con id '" + id +
                    "' NO fue encontrado! Y uno o varios atributos son nulos");

        if(!repository.existsById(id))
            throw new EntityNotFoundException("¡El usuario con id '" + id + "' NO fue encontrado!");

        if(!validateUserDtoAttributes(dto))
            throw new EntityNullAttributesException("¡Uno o varios de los atributos enviados son nulos!");

        return null;
    }

    //region ----------------------------  VALIDACIONES  ----------------------------
    // TODO: refactorizar los metodos de validaciones que estan en los if del createUser(),
    //  modularizarlas de esta forma pero con su logica, y luego moverlos al
    //  package: /tools/validations/userValidationsClass
    // Validar que existan usuarios unicos por mail
    public Boolean existUserByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public Boolean existUserByDni(String dni) {
        return repository.existsByDni(dni);
    }

    public Boolean existUserByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public boolean validateUserDtoAttributes(UserDTO dto) {
        return dto.getEmail() != null &&
                dto.getPassword() != null &&
                dto.getName() != null &&
                dto.getUsername() != null &&
                dto.getDni() != null;
    }
    // endregion

    public class Mensaje{
        private String mensaje;

        public Mensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public Mensaje() {
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }
}
