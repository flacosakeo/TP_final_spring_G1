package com.proyectoFinal.homebanking.models.DTO;

import com.proyectoFinal.homebanking.models.Account;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String username;
    private String dni;
   
}
