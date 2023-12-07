package com.proyectoFinal.homebanking.controllers;

import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.services.TransferService;
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
@RequestMapping("/api/transfer")
public class TransferController {
    private final TransferService service;
    
    public TransferController(TransferService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<TransferDTO>> getTransfer(){
        List<TransferDTO> lista=service.getTransfer();
        //llamar al servicio de usuarios para obtener la lista de usuarios
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<TransferDTO> getTransferById(@PathVariable Long id){//el pathvariable guarda el id de la request
                                                   //en la variable id de la funcion
        return ResponseEntity.status(HttpStatus.OK).body(service.getTransferById(id));
    }

    @PostMapping()
    public ResponseEntity<TransferDTO> createTransfer(@RequestBody TransferDTO transfer){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTransfer(transfer));
    }

    //Decicimos no implementar la modificacion de una transferencia. Explicacion en el servicio.
    @PutMapping(value="/{id}")
    public ResponseEntity<TransferDTO> updateTransfer(@PathVariable Long id, @RequestBody TransferDTO transfer){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateTransfer(id, transfer));
    }
    
    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteTransfer(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteTransfer(id));
    }
}
