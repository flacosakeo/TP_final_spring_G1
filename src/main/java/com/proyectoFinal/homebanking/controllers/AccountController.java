
package com.proyectoFinal.homebanking.controllers;

import com.proyectoFinal.homebanking.models.DTO.AccountDTO;
import com.proyectoFinal.homebanking.services.AccountService;
import java.util.List;
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
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private final AccountService service;
    
    public AccountController(AccountService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccount(){
        List<AccountDTO> lista=service.getAccount();
        //llamar al servicio de usuarios para obtener la lista de usuarios
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id){//el pathvariable guarda el id de la request
                                                   //en la variable id de la funcion
        return ResponseEntity.status(HttpStatus.OK).body(service.getAccountById(id));
    }
    
    @PostMapping()
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO account){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(account));
    }
    
    //@PutMapping(value="/{id}")
    //public void updateAllUser(@PathVariable Long id){}
    
    @PutMapping(value="/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO account){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateAccount(id, account));
    }
    
    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteAccount(id));
    }
}
