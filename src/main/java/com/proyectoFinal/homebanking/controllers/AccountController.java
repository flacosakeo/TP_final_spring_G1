
package com.proyectoFinal.homebanking.controllers;

import com.proyectoFinal.homebanking.exceptions.*;
import com.proyectoFinal.homebanking.models.DTO.AccountDTO;
import com.proyectoFinal.homebanking.services.AccountService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.proyectoFinal.homebanking.tools.validations.ControllerValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService service;
    
    public AccountController(AccountService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccount(){
        List<AccountDTO> accountDtoList = service.getAccount();
        return ResponseEntity.status(HttpStatus.OK).body(accountDtoList);
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAccountById(id));

        } catch(EntityNotFoundException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }
    
    @PostMapping()
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO dto) {
        try {
            ControllerValidation.validateAccountDTO(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(dto));

        } catch(EntityNullAttributesException | RequiredAttributeException | InvalidAttributeException |
                AttributeNotRequiredException | EntityNotFoundException error) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.deleteAccount(id));

        } catch(EntityNotFoundException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }

    //@PutMapping(value="/{id}")
    //public void updateAllUser(@PathVariable Long id){}
    
    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody AccountDTO dto) {
        try {
            ControllerValidation.validateAccountDtoToModify(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.updateAccount(id, dto));

        } catch(EntityNullAttributesException | RequiredAttributeException | AttributeNotRequiredException |
                EntityNotFoundException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }

    }

    @PatchMapping(value="/deposit/{id}")
    public ResponseEntity<?> depositMoney(@PathVariable Long id, @RequestBody Map<String, BigDecimal> requestBody) {
        try {
            BigDecimal amount = ControllerValidation.validateAmountAttribute(requestBody);
            return ResponseEntity.status(HttpStatus.OK).body(service.depositMoney(id, amount));

        } catch(RequiredAttributeException | InvalidAttributeException | EntityNotFoundException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }

    @PatchMapping(value="/extract/{id}")
    public ResponseEntity<?> extractMoney(@PathVariable Long id, @RequestBody Map<String, BigDecimal> requestBody) {
        try {
            BigDecimal amount = ControllerValidation.validateAmountAttribute(requestBody);
            return ResponseEntity.status(HttpStatus.OK).body(service.extractMoney(id, amount));

        } catch(RequiredAttributeException | InvalidAttributeException | InsufficientFoundsException |
                EntityNotFoundException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }
}
