package com.proyectoFinal.homebanking.controllers;

import com.proyectoFinal.homebanking.exceptions.*;
import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.services.UserService;
import java.util.List;

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
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> usersDtoList = service.getUsers();
        // Llamar al servicio de usuarios para obtener la lista de usuarios
        return ResponseEntity.status(HttpStatus.OK).body(usersDtoList);
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) { // El @PathVariable guarda el id de la request
        try {                                                     // en la variable id de la funcion
            return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(id));
            
        } catch(EntityNotFoundException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }
    
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserDTO dto) {
        try {
            ControllerValidation.validateUserDTO(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(dto));
             
        } catch (EntityAttributeExistsException | InvalidAttributeException | EntityNullAttributesException |
                EntityNotFoundException error) {
            // Manejar la excepción específica lanzada desde el servicio
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }
        
    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.deleteUser(id));
            
        } catch(EntityNotFoundException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }
    
    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateAllUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        try {
            //Se valida que todos los datos del "dto" no vienen en null y que todos los atributos del UserDto
            // sean correctos
            ControllerValidation.validateUserDTO(dto);
            return ResponseEntity.status(HttpStatus.OK).body(service.updateAllUser(id, dto));

        } catch (EntityNotFoundException | EntityNullAttributesException | FatalErrorException | InvalidAttributeException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }

    @PatchMapping(value="/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.updateUser(id, dto));

        } catch(EntityNotFoundException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }
}
