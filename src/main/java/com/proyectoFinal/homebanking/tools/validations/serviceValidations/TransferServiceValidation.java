package com.proyectoFinal.homebanking.tools.validations.serviceValidations;

import com.proyectoFinal.homebanking.exceptions.EntityAttributeExistsException;
import com.proyectoFinal.homebanking.exceptions.EntityNotFoundException;
import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.models.Transfer;
import com.proyectoFinal.homebanking.repositories.TransferRepository;
import com.proyectoFinal.homebanking.tools.NotificationMessage;
import org.springframework.stereotype.Component;

@Component
public class TransferServiceValidation {

    public static void validateTrasferDto(TransferDTO dto) throws EntityAttributeExistsException {

    }

    public static TransferRepository repository;
    public TransferServiceValidation(TransferRepository repository) {
        TransferServiceValidation.repository = repository;
    }
    public static Boolean existTransferById(Long id) {
        return repository.existsById(id);
    }

    public static Transfer findTransferById(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow( () ->
                new EntityNotFoundException( NotificationMessage.userNotFound(id)) );
    }

}
