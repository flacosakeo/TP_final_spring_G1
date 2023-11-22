package com.proyectoFinal.homebanking.models.DTO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {
    private Long id_transfer;
    private Double monto;
    private Long id_cta_origen;
    private Long id_cta_destino;
    private Date fecha;
}
