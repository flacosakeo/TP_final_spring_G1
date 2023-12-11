package com.proyectoFinal.homebanking.services;

import com.proyectoFinal.homebanking.exceptions.*;
import com.proyectoFinal.homebanking.mappers.TransferMapper;
import com.proyectoFinal.homebanking.models.Account;
import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.models.Transfer;
import com.proyectoFinal.homebanking.repositories.AccountRepository;
import com.proyectoFinal.homebanking.repositories.TransferRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.proyectoFinal.homebanking.tools.NotificationMessage;
import com.proyectoFinal.homebanking.tools.validations.serviceValidations.AccountServiceValidation;
import com.proyectoFinal.homebanking.tools.validations.serviceValidations.TransferServiceValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    
    public TransferService(TransferRepository transferRepository, AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    public List<TransferDTO> getTransfers() {
        List<Transfer> transfers = transferRepository.findAll();

        return transfers.stream()
                .map(TransferMapper::transferToDto)                
                .collect(Collectors.toList());
    }
    
    public TransferDTO getTransferById(Long id) throws EntityNotFoundException {
        Transfer transfer = TransferServiceValidation.findTransferById(id);
        return TransferMapper.transferToDto(transfer);
    }

    @Transactional
    public TransferDTO createTransfer(TransferDTO dto) throws AccountsAreTheSameException, EntityNotFoundException,
            InsufficientFoundsException {

        Long originAccountId = dto.getOriginAccountId();
        Long targetAccountId = dto.getTargetAccountId();

        // Verifica si ambas cuentas son iguales
        if(Objects.equals(originAccountId, targetAccountId)) {
            throw new AccountsAreTheSameException( NotificationMessage.equalAccounts(originAccountId, targetAccountId) );
        }

        //verificar que las cuentas origen y destino existan
        Account originAccount = AccountServiceValidation.findOriginAccountById(originAccountId);
        Account destinationAccount = AccountServiceValidation.findTargetAccountById(targetAccountId);

        // Verifica si la cuenta origen tiene fondos
        if (originAccount.getAmount().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFoundsException( NotificationMessage.insufficientFounds( originAccountId ));
        }

        // Se hace la transferencia, se resta de la cuenta origen y se suma en la cuenta destino
        originAccount.setAmount( originAccount.getAmount().subtract(dto.getAmount()) );
        destinationAccount.setAmount( destinationAccount.getAmount().add( dto.getAmount()) );

        // Guarda las cuentas actualizadas
        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        // Crea la transferencia y la guarda en base de datos
        dto.setDateTime( LocalDateTime.now() );
        Transfer transfer = TransferMapper.dtoToTransfer(dto);

        Transfer transferSaved = transferRepository.save(transfer);
        return TransferMapper.transferToDto(transferSaved);
    }

    public String deleteTransfer(Long id) throws EntityNotFoundException {
        if ( !TransferServiceValidation.existTransferById(id) ) {
            throw new EntityNotFoundException( NotificationMessage.transferNotFound(id) );
        }

        transferRepository.deleteById(id);
        return NotificationMessage.transferSuccessfullyDeleted(id);
    }

    // Creemos que una transferencia no deberia ser modificable, por el hecho de que queremos que quede registrado cada
    // movimiento. Nos parece poco etico que se pueda modificar una transferencia.
    // Si hace falta realizar una modificacion, por ejemplo si se transfirio erroneamente, deberia realizarse otra
    // transferencia con el mismo monto y id de las cuentas de origen y destino de forma invertida.
    // TODO (#Ref. 2): agregar atributo en entidad TRANSFER que indique quien realiza o el estado de la transferencia.
    public TransferDTO updateAllTransfer(Long id, TransferDTO dto) throws FatalErrorException, EntityNotFoundException,
            EntityNullAttributesException {

        // Primero verifico si existe un usuario con ese id en la BD
        // Y tambien se valida que todos los datos del "dto" no vienen en null
        TransferServiceValidation.validateUpdateAllTransfer(dto);

        // Si llega hasta aquí es porque ya se valido el dto. Entonces consigo la transferencia a modificar desde la BD
        Transfer transferToModify = TransferServiceValidation.findTransferById(id);

        // LÓGICA DEL PUT
        transferToModify.setAmount(dto.getAmount());
        transferToModify.setOriginAccountId(dto.getOriginAccountId());
        transferToModify.setTargetAccountId(dto.getTargetAccountId());

        // Persistimos la modificacion del usuario en la BD
        Transfer transferModified = transferRepository.save(transferToModify);

        return TransferMapper.transferToDto(transferModified);
    }

    public TransferDTO updateTransfer(Long id, TransferDTO dto) throws EntityNotFoundException {
        if( !TransferServiceValidation.existTransferById(id) ) {
            throw new EntityNotFoundException( NotificationMessage.transferNotFound(id) );
        }

        Long originAccountId = dto.getOriginAccountId();
        Long targetAccountId = dto.getTargetAccountId();

        Transfer transferToModify = TransferServiceValidation.findTransferById(id);

        // LÓGICA DEL PATCH
        if(dto.getAmount() != null)
            transferToModify.setAmount(dto.getAmount());

        if(originAccountId != null)
            transferToModify.setOriginAccountId(originAccountId);

        if (targetAccountId != null)
            transferToModify.setTargetAccountId(targetAccountId);

        if (dto.getDateTime() != null)
            transferToModify.setDateTime(dto.getDateTime());

        Transfer transferModified = transferRepository.save(transferToModify);
        return TransferMapper.transferToDto(transferModified);
    }
}

