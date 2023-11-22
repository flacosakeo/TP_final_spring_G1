
package com.proyectoFinal.homebanking.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="transferencias")
@Getter
@Setter
public class Transfer {
    @Id//establece que este campo es PK
    @GeneratedValue(strategy=GenerationType.IDENTITY)//establece que el campo es autoincremenatl
    @Column(name="id_transferencia")
    private Long id_transfer;
    
    @Column(name="Importe")
    private Double monto;
    
    @Column(name="Cta origen")
    private Long id_cta_origen;
    
    @Column(name="Cta destino")
    private Long id_cta_destino;
    
    @Column(name="Fecha")
    private Date fecha;
}
