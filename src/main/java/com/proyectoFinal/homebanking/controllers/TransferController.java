package com.proyectoFinal.homebanking.controllers;

import com.proyectoFinal.homebanking.exceptions.EntityNotFoundException;
import com.proyectoFinal.homebanking.exceptions.FatalErrorException;
import com.proyectoFinal.homebanking.exceptions.InvalidAttributeException;
import com.proyectoFinal.homebanking.exceptions.RequiredAttributeException;
import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.services.TransferService;
import java.util.List;

import com.proyectoFinal.homebanking.tools.validations.ControllerValidation;
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

        } catch(FatalErrorException | RequiredAttributeException | InvalidAttributeException | IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteTransfer(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteTransfer(id));
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateTransfer(@PathVariable Long id, @RequestBody TransferDTO transfer) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateTransfer(id, transfer));
    }
    
}
