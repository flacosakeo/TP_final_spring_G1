package com.proyectoFinal.homebanking.controllers;

import com.proyectoFinal.homebanking.exceptions.UserNotExistsException;
import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.models.User;
import com.proyectoFinal.homebanking.services.UserService;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    //prueba branch marcelo
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
    public ResponseEntity<?> createUser(@RequestBody UserDTO user){

        String dni = user.getDni(); // DNI a verificar

        // Patrón para verificar que el DNI contenga exactamente 8 dígitos
        String regex = "\\d{8}";

        // Compilar el patrón
        Pattern pattern = Pattern.compile(regex);

        // Crear un Matcher con el DNI
        Matcher matcher = pattern.matcher(dni);

        // Verificar si el DNI cumple con el patrón
        if (matcher.matches()) {
            //System.out.println("El número de DNI es válido.");
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(user));
        } else {
            //System.out.println("El número de DNI no es válido.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El DNI no es valido "+user.getDni()+" debe contener 8 caracteres numericos");
        }
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
}
