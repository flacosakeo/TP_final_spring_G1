package com.proyectoFinal.homebanking.controllers;

import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.services.UserService;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
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
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){//el pathvariable guarda el id de la request
                                                   //en la variable id de la funcion
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(id));
    }
    
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserDTO dto){

        // Verificar si el DNI es válido y asi con cada atributo
        if( !dniNumberDigitsIsValid(dto.getDni()) ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El DNI "
                    + dto.getDni() + " no es valido! ¡Debe contener 8 caracteres numericos!");
        }

        //TODO corregir status
        if(!emailIsValid(dto.getEmail())) return ResponseEntity.status(HttpStatus.CREATED).body("¡Email invalido!");

        if(!passwordIsValid(dto.getPassword())) {
            //TODO corregir status
            return ResponseEntity.status(HttpStatus.CREATED).body("¡Contraseña incorrecta! Debe contener al menos 8 caracteres");
        }

        if(!nameIsValid(dto.getName())){
            //TODO corregir status
            return ResponseEntity.status(HttpStatus.CREATED).body("¡Nombre invalido! " +
                    "Debe comenzar en mayúscula y tener al menos 2 letras. Acepta nombres compuestos (hasta 3 palabras)");
        }

        if(!usernameIsValid(dto.getUsername())){
            //TODO corregir status
            return ResponseEntity.status(HttpStatus.CREATED).body("¡Username invalido! Debe tener entre 4 y 8 caracteres." +
                    " Solo admite letras o números");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(dto));
    }
        
    //@PutMapping(value="/{id}")
    //public void updateAllUser(@PathVariable Long id){}
    
    @PutMapping(value="/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO user){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateUser(id, user));
    }
    
    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteUser(id));
    }

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
