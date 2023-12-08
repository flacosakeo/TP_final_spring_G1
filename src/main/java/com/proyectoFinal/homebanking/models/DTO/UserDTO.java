package com.proyectoFinal.homebanking.models.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String username;
    private String dni;
}
