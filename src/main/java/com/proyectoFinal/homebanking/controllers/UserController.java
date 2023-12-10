package com.proyectoFinal.homebanking.controllers;

import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.services.UserService;
import java.util.List;

import com.proyectoFinal.homebanking.tools.NotificationMessage;
import com.proyectoFinal.homebanking.tools.validations.ControllerValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    
    //definir la url de origen para cada solicitud
    //para cada metodo http permitido debemos realizar una acion
    //definir el dto y el service (inyeccion de dependencia)
    //crud:crear,leer,mod,eliminar
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<UserDTO> lista=service.getUsers();
        //llamar al servicio de usuarios para obtener la lista de usuarios
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){ // El @PathVariable guarda el id de la request
                                                                       // en la variable id de la funcion
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(id));
    }
    
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserDTO dto){

        // Verificar si el DNI es válido y asi con cada atributo
        if( !ControllerValidation.dniNumberDigitsIsValid(dto.getDni()) )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( NotificationMessage.dniNotFound(dto.getDni()) );

        if(!ControllerValidation.emailIsValid(dto.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( NotificationMessage.invalidEmail(dto.getEmail()) );

        if(!ControllerValidation.passwordIsValid(dto.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( NotificationMessage.invalidPassword() );

        if(!ControllerValidation.nameIsValid(dto.getName()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( NotificationMessage.invalidName() );

        if(!ControllerValidation.usernameIsValid(dto.getUsername()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( NotificationMessage.usernameInvalid() );

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(dto));
    }
        
    @PutMapping(value="/{id}")
    public ResponseEntity<UserDTO> updateAllUser(@PathVariable Long id, @RequestBody UserDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateAllUser(id, dto));
    }

    @PatchMapping(value="/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateUser(id, dto));
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteUser(id));
    }
}
