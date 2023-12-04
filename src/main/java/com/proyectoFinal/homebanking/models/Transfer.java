
package com.proyectoFinal.homebanking.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="transferencias")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    @Id//establece que este campo es PK
    @GeneratedValue(strategy=GenerationType.IDENTITY)//establece que el campo es autoincremenatl
    @Column(name="id_transferencia")
    private Long id_transfer;
    
    @Column(name="Importe")
    private BigDecimal monto;
    
    @Column(name="Cta origen")
    private Long id_cta_origen;
    
    @Column(name="Cta destino")
    private Long id_cta_destino;
    
    @Column(name="Fecha")
    private Date fecha;
}
