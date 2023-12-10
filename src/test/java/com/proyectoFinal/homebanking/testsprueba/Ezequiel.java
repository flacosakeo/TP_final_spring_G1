package com.proyectoFinal.homebanking.testsprueba;
public class Ezequiel {
    public static void main(String[] args) {



    }

/*
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserDTO dto) {
        try {
            String responseValidation = ControllerValidation.validateCreateUserDto(dto);
            if (!responseValidation.equals("OK")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseValidation);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(dto));
        } catch (EntityAttributeExistsException ex) {
            // Manejar la excepción específica lanzada desde el servicio
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (OtraExcepcionEspecifica ex) {
            // Manejar otras excepciones específicas si es necesario
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error interno");
        } catch (Exception ex) {
            // Manejar otras excepciones no esperadas
            ex.printStackTrace(); // Manejo básico, imprimir la traza en la consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error interno");
        }
    }
*/




}