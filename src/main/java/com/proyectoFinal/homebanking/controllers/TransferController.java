package com.proyectoFinal.homebanking.controllers;

import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.services.TransferService;
import java.util.List;

import com.proyectoFinal.homebanking.tools.ErrorMessage;
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
    public ResponseEntity<List<TransferDTO>> getTransfers(){
        List<TransferDTO> lista=service.getTransfers();
        //llamar al servicio de usuarios para obtener la lista de usuarios
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<TransferDTO> getTransferById(@PathVariable Long id){ //Pathvariable guarda el id de la request
                                                                               //en la variable id de la funcion
        return ResponseEntity.status(HttpStatus.OK).body(service.getTransferById(id));
    }

    @PostMapping()
    public ResponseEntity<?> createTransfer(@RequestBody TransferDTO transfer){

        Long originAccountId = transfer.getOriginAccountId();
        Long targetAccountId = transfer.getTargetAccountId();

        if(originAccountId == null || targetAccountId == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.requiredAccount());
        }

        if (transfer.getAmount() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.requiredAmount());
        }

        if(!ControllerValidation.isPositive(originAccountId) || (!ControllerValidation.isPositive(targetAccountId))){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ControllerValidation.validateTransferAccountId(originAccountId,
                        targetAccountId));
        }

        // region Validar si amount es un número
        /*Cuando en el BODY se pasa un string en el amount, al realizarse la deserialización para crear el objeto transfer
        * se lanza una excepción, ya que se espera que el amount de transfer sea un big decimal, y en este proceso
        * se intenta convertir el campo amount a BigDecimal pero no es posible. Esta excepción la lanza Jackson

        * Se encontro una posible solución para realizar la validación, mapeando el body "manualmente" con un map<string,string>
        * en el parametro de este método para extraer el amount como string y luego convertirlo a BigDecimal y atrapar la
        *  excepción si no es posible realizar la conversión con un try-catch pero no me parece una buena solución,
        * por lo que por ahora se dejara el metodo sin validar si es un bigdecimal o no*/
//        Método anterior que no funciona
//        try {
//            transfer.getAmount().doubleValue();
//        } catch (NumberFormatException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Monto debe ser un número válido.");
//        }
        // endregion

        if(!ControllerValidation.isPositive(transfer.getAmount())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( ErrorMessage.invalidAmount(transfer.getAmount()) );
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTransfer(transfer));
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<TransferDTO> updateTransfer(@PathVariable Long id, @RequestBody TransferDTO transfer){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateTransfer(id, transfer));
    }
    
    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteTransfer(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteTransfer(id));
    }

}
