package com.proyectoFinal.homebanking.models.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferDTO {
    private Long id;
    private BigDecimal amount;
    private Long originAccountId;
    private Long targetAccountId;
    private LocalDateTime dateTime;
}
