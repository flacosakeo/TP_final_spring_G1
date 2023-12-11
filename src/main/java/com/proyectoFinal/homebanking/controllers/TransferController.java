package com.proyectoFinal.homebanking.controllers;

import com.proyectoFinal.homebanking.exceptions.*;
import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.services.TransferService;
import java.util.List;

import com.proyectoFinal.homebanking.tools.validations.ControllerValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final TransferService service;
    
    public TransferController(TransferService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<TransferDTO>> getTransfers() {
        List<TransferDTO> transfersDtoList = service.getTransfers();
        // Llamar al servicio de usuarios para obtener la lista de usuarios
        return ResponseEntity.status(HttpStatus.OK).body(transfersDtoList);
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<?> getTransferById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getTransferById(id));

        } catch(EntityNotFoundException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createTransfer(@RequestBody TransferDTO dto) {
        try {
            ControllerValidation.validateTransferDto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createTransfer(dto));

        } catch(FatalErrorException | RequiredAttributeException | InvalidAttributeException | IllegalArgumentException
                | AccountsAreTheSameException | EntityNotFoundException | InsufficientFoundsException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteTransfer(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.deleteTransfer(id));

        } catch(EntityNotFoundException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateAllTransfer(@PathVariable Long id, @RequestBody TransferDTO dto) {
        try {
           return ResponseEntity.status(HttpStatus.OK).body(service.updateAllTransfer(id, dto));

        } catch(FatalErrorException | EntityNotFoundException | EntityNullAttributesException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }

    @PatchMapping(value="/{id}")
    public ResponseEntity<?> updateTransfer(@PathVariable Long id, @RequestBody TransferDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.updateTransfer(id, dto));

        } catch(EntityNotFoundException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }
}
