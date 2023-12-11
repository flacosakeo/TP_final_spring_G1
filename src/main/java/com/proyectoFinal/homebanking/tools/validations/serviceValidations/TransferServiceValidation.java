package com.proyectoFinal.homebanking.tools.validations.serviceValidations;

import com.proyectoFinal.homebanking.exceptions.EntityAttributeExistsException;
import com.proyectoFinal.homebanking.exceptions.EntityNotFoundException;
import com.proyectoFinal.homebanking.exceptions.EntityNullAttributesException;
import com.proyectoFinal.homebanking.exceptions.FatalErrorException;
import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.models.Transfer;
import com.proyectoFinal.homebanking.repositories.TransferRepository;
import com.proyectoFinal.homebanking.tools.NotificationMessage;
import org.springframework.stereotype.Component;

@Component
public class TransferServiceValidation {

    public static TransferRepository repository;

    public TransferServiceValidation(TransferRepository repository) {
        TransferServiceValidation.repository = repository;
    }

    public static void validateTrasferDto(TransferDTO dto) throws EntityAttributeExistsException {

    }

    public static void validateUpdateAllTransfer(TransferDTO dto) throws FatalErrorException, EntityNotFoundException,
            EntityNullAttributesException {

        if(!existTransferById(dto.getId()) && !validateTransferDtoAttributes(dto))
            throw new FatalErrorException( NotificationMessage.transferNotFoundAndNullAttributes(dto.getId()) );

        if(!existTransferById(dto.getId()))
            throw new EntityNotFoundException( NotificationMessage.transferNotFound(dto.getId()) );

        if(!validateTransferDtoAttributes(dto)) // TODO: creo q el metodo userNullAttributes() cambio de nombre (+ general), actualizarlo con lo de flor
            throw new EntityNullAttributesException( NotificationMessage.userNullAttributes() );
    }

    public static Transfer findTransferById(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow( () ->
                new EntityNotFoundException( NotificationMessage.transferNotFound(id)) );
    }

    public static Boolean existTransferById(Long id) {
        return repository.existsById(id);
    }

    //TODO: cambiar nombre de este metodo segun como esta puesto en user. Flor tenia que pushear...
    public static boolean validateTransferDtoAttributes(TransferDTO dto) {
        return dto.getAmount() != null &&
                dto.getOriginAccountId() != null &&
                dto.getTargetAccountId() != null;
    }
}
