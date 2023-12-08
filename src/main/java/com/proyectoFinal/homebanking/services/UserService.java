
package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.EntityNotFoundException;
import com.proyectoFinal.homebanking.exceptions.EntityAttributeExistsException;
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
        if( repository.existsByEmail(dto.getEmail()) )
            throw new EntityAttributeExistsException("¡Email " + dto.getEmail() + " ya registrado!");

        if( repository.existsByDni(dto.getDni()) )
            throw new EntityAttributeExistsException("¡Ya existe un usuario con el DNI: " + dto.getDni() + "!");

        if( repository.existsByUsername(dto.getUsername()) )
            throw new EntityAttributeExistsException("¡Ya existe un usuario con el USERNAME " + dto.getUsername() + "!");

        // Si llega hasta este punto es porque no existe ningún usuario con el mismo email, dni, y username. Puedo crearlo.
        User userSaved = repository.save(UserMapper.dtoToUser(dto));
        return UserMapper.userToDto(userSaved);
    }
    
    public UserDTO getUserById(Long id){
        User entity = repository.findById(id).orElseThrow( () ->
                new EntityNotFoundException("¡El usuario con ID " + id + " NO fue encontrado!"));
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
            User userToModify=repository.findById(id).get();
            //logica del patch
            if (dto.getName()!=null){
                userToModify.setName(dto.getName());
            }
            if (dto.getUsername()!=null){
                userToModify.setUsername(dto.getUsername());
            }
            if (dto.getEmail()!=null){
                userToModify.setEmail(dto.getEmail());
            }
            if (dto.getDni()!=null){
                userToModify.setDni(dto.getDni());
            }
            if (dto.getPassword()!=null){
                userToModify.setPassword(dto.getPassword());
            }
            repository.save(userToModify);
            return UserMapper.userToDto(userToModify);
        }
        return null;
    }
    
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
