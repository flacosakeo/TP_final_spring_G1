package com.proyectoFinal.homebanking.controllers;

import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.services.UserService;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.proyectoFinal.homebanking.tools.ErrorMessage;
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
        if( !dniNumberDigitsIsValid(dto.getDni()) ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body( ErrorMessage.dniNotFound(dto.getDni()) );
        }

        //TODO corregir status
        if(!emailIsValid(dto.getEmail()))
            return ResponseEntity.status(HttpStatus.CREATED).body( ErrorMessage.invalidEmail(dto.getEmail()) );

        if(!passwordIsValid(dto.getPassword())) {
            //TODO corregir status
            return ResponseEntity.status(HttpStatus.CREATED).body( ErrorMessage.invalidPassword() );
        }

        if(!nameIsValid(dto.getName())){
            //TODO corregir status
            return ResponseEntity.status(HttpStatus.CREATED).body( ErrorMessage.invalidName() );
        }

        if(!usernameIsValid(dto.getUsername())){
            //TODO corregir status
            return ResponseEntity.status(HttpStatus.CREATED).body( ErrorMessage.usernameInvalid() );
        }

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

    //TODO: REFACTORIZAR, MOVER TODOS ESTOS METODOS A CLASE VALIDATIONS Y VOLVERLOS ESTATICOS
    //TODO: REFACTORIZAR, para tener en cuenta si lo que se pasa es nulo... porque ahi se rompe el matcher
    // Valida que el dni tenga 8 digitos
    public Boolean dniNumberDigitsIsValid(String dni) {
        // DNI a verificar viene por el parametro.

        // Patrón para verificar que el DNI contenga exactamente 8 dígitos
        String regex = "\\d{8}";

        // Compilar el patrón
        Pattern pattern = Pattern.compile(regex);

        // Crear un Matcher con el DNI
        Matcher matcher = pattern.matcher(dni);

        // Verificar si el DNI cumple con el patrón y retornar el resultado
        return matcher.matches();
    }

    public Boolean emailIsValid(String email){
        Pattern pattern = Pattern.compile("[^@ ]+@[^@ .]+\\.[a-z]{2,}(\\.[a-z]{2})?");
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public Boolean passwordIsValid(String password){
        Pattern pattern = Pattern.compile(".{8}");
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    // El name debe comenzar en mayuscula y tener al menos 2 letras. Acepta nombres compuestos(hasta 3 palabras)
    public Boolean nameIsValid(String name){
        Pattern pattern = Pattern.compile("([A-Z][a-z]+ ?){1,3}");
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }

    // Valida que el username tenga al entre 4 y 8 caracteres. Solo admite letras o numeros.
    public Boolean usernameIsValid(String username){
        Pattern pattern = Pattern.compile("\\w{4,8}");
        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }
}
