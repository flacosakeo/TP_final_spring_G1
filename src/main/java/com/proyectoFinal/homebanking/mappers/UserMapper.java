package com.proyectoFinal.homebanking.mappers;

import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.models.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
   // Métodos para transformar objetos
    public static User dtoToUser(UserDTO dto){
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .username(dto.getUsername())
                .dni(dto.getDni())
                .build();
    }
    
    public static UserDTO userToDto(User user){
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .username(user.getUsername())
                .dni(user.getDni())
                .build();
    }
}