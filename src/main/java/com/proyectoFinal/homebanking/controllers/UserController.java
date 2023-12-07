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

        if(!validaEmail(user)){
            //TODO corregir status
            return ResponseEntity.status(HttpStatus.CREATED).body("Email incorrecto");
        }
        if(!validaPassword(user)){
            //TODO corregir status
            return ResponseEntity.status(HttpStatus.CREATED).body("password incorrecta. debe contener al menos 8 caracteres ");

        }
        if(!validaName(user)){
            //TODO corregir status
            return ResponseEntity.status(HttpStatus.CREATED).body("name incorrecto. debe comenzar en mayuscula y tener al menos 2 letras. acepta nombres compuestos(hasta 3 palabras) ");

        }
        if(!validaUsername(user)){
            //TODO corregir status
            return ResponseEntity.status(HttpStatus.CREATED).body("username incorrecto. debe tener entre 4 y 8 caracteres. solo admite letras o numeros");

        }
        if(!validaDni(user)){
            //TODO corregir status
            return ResponseEntity.status(HttpStatus.CREATED).body("dni incorrecto. debe ser de 8 digitos");

        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(user));


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


    public Boolean validaEmail(UserDTO userdto){
        String text= userdto.getEmail();

        Pattern pattern = Pattern.compile("[^@ ]+@[^@ .]+\\.[a-z]{2,}(\\.[a-z]{2})?");
        Matcher matcher = pattern.matcher(text);

        Boolean resultado=(matcher.matches());

        return resultado;
    }

    public Boolean validaPassword(UserDTO userdto){
        String text= userdto.getPassword();

        Pattern pattern = Pattern.compile(".{8}");
        Matcher matcher = pattern.matcher(text);

        Boolean resultado=(matcher.matches());

        return resultado;
    }

    //el name debe comenzar en mayuscula y tener al menos 2 letras. acepta nombres compuestos(hasta 3 palabras)
    public Boolean validaName(UserDTO userdto){
        String text= userdto.getName();

        Pattern pattern = Pattern.compile("([A-Z][a-z]+ ?){1,3}");
        Matcher matcher = pattern.matcher(text);

        Boolean resultado=(matcher.matches());

        return resultado;
    }

    //valida que elusername tenga al entre 4 y 8 caracteres. solo admite letras o numeros
    public Boolean validaUsername(UserDTO userdto){
        String text= userdto.getUsername();

        Pattern pattern = Pattern.compile("\\w{4,8}");
        Matcher matcher = pattern.matcher(text);

        Boolean resultado=(matcher.matches());

        return resultado;
    }

    //valida que el dni sean 8 digitos
    public Boolean validaDni(UserDTO userdto){
        String text= userdto.getDni();

        Pattern pattern = Pattern.compile("\\d{8}");
        Matcher matcher = pattern.matcher(text);

        Boolean resultado=(matcher.matches());

        return resultado;
    }




}
