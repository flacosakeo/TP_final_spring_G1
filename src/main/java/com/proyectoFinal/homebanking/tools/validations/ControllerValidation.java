package com.proyectoFinal.homebanking.tools.validations;

import com.proyectoFinal.homebanking.exceptions.*;
import com.proyectoFinal.homebanking.models.DTO.AccountDTO;
import com.proyectoFinal.homebanking.models.DTO.TransferDTO;
import com.proyectoFinal.homebanking.models.DTO.UserDTO;
import com.proyectoFinal.homebanking.tools.NotificationMessage;

import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerValidation {

    // region ------------------  GENERAL VALIDATIONS  ------------------
    /**
     * @author Florencia
     * Método genérico que verifica que el número pasado por párametro sea positivo.
     * @param number de tipo <N>. El parametro number acepta objetos que hereden de la clase Number
     * @return boolean. Retorna True si el número es positivo.
     */
    public static <N extends Number> boolean isPositive(N number) {
        if (number instanceof Double || number instanceof Float || number instanceof Integer || number instanceof Long) {
            return  number.doubleValue() > 0;
        } else if (number instanceof BigDecimal) {
            return (((BigDecimal) number).compareTo(BigDecimal.ZERO) > 0);
        } else {
            throw new IllegalArgumentException( NotificationMessage.illegalArgument() );
        }
    }
    // endregion

    // region ------------------  USER CONTROLLER VALIDATIONS  ------------------
    public static void validateUserDTO(UserDTO dto) throws InvalidAttributeException, EntityNullAttributesException {

        if(allAttributesDTOAreNull(dto))
            throw new EntityNullAttributesException(NotificationMessage.allAttributesAreNull());

        if(!emailIsValid(dto.getEmail()))
            throw new InvalidAttributeException( NotificationMessage.invalidEmail(dto.getEmail()) );

        if(!passwordIsValid(dto.getPassword()))
            throw new InvalidAttributeException( NotificationMessage.invalidPassword() );

        if(!nameIsValid(dto.getName()))
            throw new InvalidAttributeException( NotificationMessage.invalidName() );

        if(!usernameIsValid(dto.getUsername()))
            throw new InvalidAttributeException( NotificationMessage.usernameInvalid() );

        // Verificar si el DNI es válido y asi con cada atributo
        if( !dniNumberDigitsIsValid(dto.getDni()) )
            throw new InvalidAttributeException( NotificationMessage.dniNotFound(dto.getDni()) );
    }

    public static boolean allAttributesDTOAreNull(UserDTO dto) {
        return dto.getEmail() == null &&
                dto.getPassword() == null &&
                dto.getName() == null &&
                dto.getUsername() == null &&
                dto.getDni() == null;
    }

    // Valida que el dni tenga 8 digitos
    public static boolean dniNumberDigitsIsValid(String dni) {
        // DNI a verificar viene por el parametro. Si no se pasa tal atributo, no se analiza mas nada.
        // Es decir que si se pasara un null se romperia en el matcher si se deja pasar tal atributo.
        if(dni == null) return false;

        // Patrón para verificar que el DNI contenga exactamente 8 dígitos
        String regex = "\\d{8}";

        // Compilar el patrón
        Pattern pattern = Pattern.compile(regex);

        // Crear un Matcher con el DNI
        Matcher matcher = pattern.matcher(dni);

        // Verificar si el DNI cumple con el patrón y retornar el resultado
        return matcher.matches();
    }

    public static boolean emailIsValid(String email){
        if(email == null) return false;

        Pattern pattern = Pattern.compile("[^@ ]+@[^@ .]+\\.[a-z]{2,}(\\.[a-z]{2})?");
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean passwordIsValid(String password){
        if(password == null) return false;

        Pattern pattern = Pattern.compile(".{8,}");
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    // El name debe comenzar en mayuscula y tener al menos 2 letras. Acepta nombres compuestos(hasta 3 palabras)
    public static boolean nameIsValid(String name) {
        if(name == null) return false;

        Pattern pattern = Pattern.compile("([A-Z][a-z]+ ?){1,3}");
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }

    // Valida que el username tenga al entre 4 y 8 caracteres. Solo admite letras o numeros.
    public static boolean usernameIsValid(String username) {
        if(username == null) return false;

        Pattern pattern = Pattern.compile("\\w{4,8}");
        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }

    // endregion

    // region ------------------  TRANSFER CONTROLLER VALIDATIONS  ------------------
    public static void validateTransferDto(TransferDTO dto) throws FatalErrorException, RequiredAttributeException,
            InvalidAttributeException, IllegalArgumentException {

        validateTransferAccountIds(dto.getOriginAccountId(), dto.getTargetAccountId() );
        validateAmountTransfer(dto.getAmount());
    }

    public static void validateTransferAccountIds(Long originAccountId, Long targetAccountId)
            throws FatalErrorException, RequiredAttributeException, InvalidAttributeException, IllegalArgumentException {

        validateIfTransferAccountsIdsAreNull(originAccountId, targetAccountId);
        validateIfTransferAccountsIdsArePositive( originAccountId, targetAccountId);
    }

    public static void validateIfTransferAccountsIdsAreNull(Long originAccountId, Long targetAccountId)
            throws FatalErrorException, RequiredAttributeException {

        if(originAccountId == null && targetAccountId == null) {
            throw new FatalErrorException( NotificationMessage.requiredAccounts() );

        } else if(originAccountId == null) {
            throw new RequiredAttributeException( NotificationMessage.originAccountRequired() );

        } else if(targetAccountId == null) {
            throw new RequiredAttributeException( NotificationMessage.targetAccountRequired());
        }
    }

    public static void validateIfTransferAccountsIdsArePositive(Long originAccountId, Long targetAccountId)
            throws FatalErrorException, InvalidAttributeException, IllegalArgumentException {

        if(!isPositive(originAccountId) && !isPositive(targetAccountId)) {
            throw new FatalErrorException( NotificationMessage.invalidAccounts() );

        } else if( !isPositive(originAccountId) ) {
            throw new InvalidAttributeException( NotificationMessage.invalidOriginAccount() );

        } else if( !isPositive(targetAccountId) ) {
            throw new InvalidAttributeException( NotificationMessage.invalidTargetAccount() );
        }
    }

    public static void validateAmountTransfer(BigDecimal amount) throws RequiredAttributeException,
            InvalidAttributeException, IllegalArgumentException {
        if (amount == null)
            throw new RequiredAttributeException( NotificationMessage.requiredAmount() );

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
        if(!ControllerValidation.isPositive(amount)) {
            throw new InvalidAttributeException( NotificationMessage.invalidAmount(amount) );
        }
    }
    // endregion

    // region ------------------  ACCOUNT CONTROLLER VALIDATIONS  ------------------
    public static void validateAccountDTO(AccountDTO dto) throws EntityNullAttributesException, RequiredAttributeException,
            InvalidAttributeException, AttributeNotRequiredException {

        if(allAttributesDTOAreNull(dto))
            throw new EntityNullAttributesException( NotificationMessage.allAttributesAreNull() );

        validateRequiredDTOAttributesExist(dto);

        validateAttributes(dto);

        validateNonRequiredAttributesDTO(dto);
    }

    public static void validateAttributes(AccountDTO dto) throws InvalidAttributeException {
        if( !isPositive(dto.getOwnerId()) )
            throw new InvalidAttributeException( NotificationMessage.invalidOwnerId() );

        // NO SE VALIDA. Porque nunca llegaria hasta este punto con la estructura del metodo que se ejecuta con la solicituid http.
        // Romperia dentro de SPRING al desearealizar el objeto. Entonces se deberia cambiar el parametro AccountDTO del controlador
        // por un Map<String, String> y mapearlo manualmente y ahi si podremos validar este atributo.
//        if(!accountTypeIsValid( dto.getAccountType()) )
//            throw new InvalidAttributeException( "Tipo de cuenta invalido..." );

        // Verificar si el ownerId existe se hace en el AccountServiceValidation: Validación del servicio de cuentas
    }

    public static void validateRequiredDTOAttributesExist(AccountDTO dto) throws RequiredAttributeException {
        if(dto.getOwnerId() == null)
            throw new RequiredAttributeException( NotificationMessage.ownerIdRequired() );

        if(dto.getAccountType() == null)
            throw new RequiredAttributeException( NotificationMessage.accountTypeRequired() );
    }

    public static boolean allAttributesDTOAreNull(AccountDTO dto) {
        return dto.getAccountType() == null &&
                dto.getOwnerId() == null &&
                dto.getCbu() == null &&
                dto.getAlias() == null &&
                dto.getAmount() == null;
    }

    public static void validateNonRequiredAttributesDTO(AccountDTO dto) throws AttributeNotRequiredException {
        if(dto.getCbu() != null)
            throw new AttributeNotRequiredException( NotificationMessage.attributeNotRequired("CBU") );

        if(dto.getAlias() != null)
            throw new AttributeNotRequiredException( NotificationMessage.attributeNotRequired("ALIAS") );

        if(dto.getAmount() != null)
            throw new AttributeNotRequiredException( NotificationMessage.attributeNotRequired("AMOUNT") );
    }

    public static void validateAccountDtoToModify(AccountDTO dto) throws EntityNullAttributesException,
            RequiredAttributeException, AttributeNotRequiredException {

        if(allAttributesDTOAreNull(dto))
            throw new EntityNullAttributesException( NotificationMessage.allAttributesAreNull() );

        if(dto.getAlias() == null) {
            throw new RequiredAttributeException( NotificationMessage.requiredAlias() );
        }

        validateNonRequiredAttributesDtoToModify(dto);
    }

    public static void validateNonRequiredAttributesDtoToModify(AccountDTO dto) throws AttributeNotRequiredException {
        if(dto.getAccountType() != null)
            throw new AttributeNotRequiredException( NotificationMessage.attributeNotRequired("accountType") );

        if(dto.getOwnerId() != null)
            throw new AttributeNotRequiredException( NotificationMessage.attributeNotRequired("ownerId") );

        if(dto.getCbu() != null)
            throw new AttributeNotRequiredException( NotificationMessage.attributeNotRequired("CBU") );

        if(dto.getAmount() != null)
            throw new AttributeNotRequiredException( NotificationMessage.attributeNotRequired("amount") );
    }

    public static BigDecimal validateAmountAttribute(Map<String, BigDecimal> requestBody) throws RequiredAttributeException,
            InvalidAttributeException {

        BigDecimal amount = requestBody.get("amount");

        if(amount == null) {
            throw new RequiredAttributeException( NotificationMessage.requiredAmount() );
        }

        if(!isPositive(amount))
            throw new InvalidAttributeException( NotificationMessage.invalidAmount(amount) );

        return amount;
    }
    //public static boolean accountTypeIsValid(AccountType accountType) {
//        if(accountType == null) return false;
//
//    }
    // endregion
}
