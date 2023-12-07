
package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.UserNotExistsException;
import com.proyectoFinal.homebanking.mappers.UserMapper;
import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.models.User;
import com.proyectoFinal.homebanking.repositories.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    //private UserMapper mapper;

    
    public List<UserDTO> getUsers(){
        List<User> users = repository.findAll();
        List<UserDTO> usersDTO=users.stream()
                .map(UserMapper::userToDto)                
                .collect(Collectors.toList());
        return usersDTO;
    }
    
    public Object createUser(UserDTO userDTO){
        User userValidate = repository.findByEmail(userDTO.getEmail());
        User userValidateDni = repository.findByDni(userDTO.getDni());
        if(userValidate==null){
            if(userValidateDni==null){
                //User userValidateDni = repository.findByDni(userDTO.getDni());
                //if(userValidateDni==null){
                User user = repository.save(UserMapper.dtoToUser(userDTO));
                return UserMapper.userToDto(user);
            }else{
                return ("Dni ya existen: "+userValidateDni.getDni());
                //String mensaje="Dni ya existe";
                //return new Mensaje(mensaje);
            }
        }else{
            return("Email ya existen: "+userValidate.getEmail());
        }        
    }
    
    public UserDTO getUserById(Long id){
        User entity = repository.findById(id).get();
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
