
package com.proyectoFinal.homebanking.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Entity
@Table(name="transfers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transfer {
    @Id // Establece que este campo es PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Establece que el campo es autoincremental
    @Column(name = "id_transfer")
    private Long id;
    
    private BigDecimal amount;
    
    @Column(name = "origin_account")
    private Long originAccount;
    
    @Column(name = "target_account")
    private Long targetAccount;

    @Column(name = "date_time")
    private LocalDateTime dateTime;
}
