package com.proyectoFinal.homebanking.models.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferDTO {
    private Long id_transfer;
    private BigDecimal monto;
    private Long id_cta_origen;
    private Long id_cta_destino;
    private Date fecha;
}
