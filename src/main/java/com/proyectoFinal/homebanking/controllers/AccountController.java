
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

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(account));

    }
    
    //@PutMapping(value="/{id}")
    //public void updateAllUser(@PathVariable Long id){}
    
    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody AccountDTO account){
        //return ResponseEntity.status(HttpStatus.OK).body(service.updateAccount(id, account));
        //String cbu =account.getCbu(); // cbu a verificar
        // Patrón para verificar que el cbu contenga exactamente 23 dígitos
        //String regex = "\\d{23}";

        // Compilar el patrón
        //Pattern pattern = Pattern.compile(regex);

        // Crear un Matcher con el cbu
        //Matcher matcher = pattern.matcher(cbu);

        // Verificar si el cbu cumple con el patrón
        //if (matcher.matches()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.updateAccount(id, account));
        //} else {
            //return ResponseEntity.status(HttpStatus.CONFLICT).body("El CBU no es valido "+cbu+" debe contener 23 caracteres numericos");
        //}
    }
    
    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteAccount(id));
    }

    @PatchMapping(value="/{id}/deposit")
    public ResponseEntity<AccountDTO> depositMoney(@PathVariable Long id, @RequestBody Map<String, BigDecimal> requestBody){
        BigDecimal amount = requestBody.get("monto");
        return ResponseEntity.status(HttpStatus.OK).body(service.depositMoney(id, amount));
    }

    @PatchMapping(value="/{id}/extract")
    public ResponseEntity<AccountDTO> extractMoney(@PathVariable Long id, @RequestBody Map<String, BigDecimal> requestBody){
        BigDecimal amount = requestBody.get("monto");
        return ResponseEntity.status(HttpStatus.OK).body(service.extractMoney(id, amount));
    }
}
