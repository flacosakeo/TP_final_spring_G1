package com.proyectoFinal.homebanking.mappers;

import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.models.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
   //metodos para transformar objetos
    public static User dtoToUser(UserDTO dto){
        User user= new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setDni(dto.getDni());
        return user;
    }
    
    public static UserDTO userToDto(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setDni(user.getDni());
        return dto;
    }
}
