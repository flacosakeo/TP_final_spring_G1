package com.proyectoFinal.homebanking.models.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {
    private Long id;
    private BigDecimal amount;
    private Long sourceAccount;
    private Long targetAccount;
    private LocalDateTime dateTime;
}
