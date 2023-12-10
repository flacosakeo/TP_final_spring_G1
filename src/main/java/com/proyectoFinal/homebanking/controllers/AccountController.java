
package com.proyectoFinal.homebanking.controllers;

import com.proyectoFinal.homebanking.models.DTO.AccountDTO;
import com.proyectoFinal.homebanking.services.AccountService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO account){
        if(account.getOwnerId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id user es requerido");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(account));

    }
    
    //@PutMapping(value="/{id}")
    //public void updateAllUser(@PathVariable Long id){}
    
    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody AccountDTO account){
        if(account.getAlias() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Alias es requerido");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.updateAccount(id, account));

    }
    
    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteAccount(id));
    }

    @PatchMapping(value="/{id}/deposit")
    public ResponseEntity<?> depositMoney(@PathVariable Long id, @RequestBody Map<String, BigDecimal> requestBody){
        BigDecimal amount = requestBody.get("monto");
        if(amount == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Monto es requerido");
        }
        return ResponseEntity.status(HttpStatus.OK).body(service.depositMoney(id, amount));
    }

    @PatchMapping(value="/{id}/extract")
    public ResponseEntity<?> extractMoney(@PathVariable Long id, @RequestBody Map<String, BigDecimal> requestBody){
        BigDecimal amount = requestBody.get("monto");
        if(amount == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Monto es requerido");
        }
        return ResponseEntity.status(HttpStatus.OK).body(service.extractMoney(id, amount));
    }
}
