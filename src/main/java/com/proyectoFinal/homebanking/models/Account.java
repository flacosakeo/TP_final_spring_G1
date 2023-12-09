
package com.proyectoFinal.homebanking.models;

import com.proyectoFinal.homebanking.models.Enum.AccountAlias;
import com.proyectoFinal.homebanking.models.Enum.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

import lombok.*;

@Entity
@Table(name="accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_cuenta")
    private Long id_account;
    
    @Column(name="tipo_de_cuenta")
    private AccountType tipo;
    
    @Column(name="dueño")
    private String dueño;
    
    @Column(name="CBU")

    private String cbu;
    
    @Column(name="alias")
    private AccountAlias alias;
    
    @Column(name="importe")
    private BigDecimal monto;
    
    @ManyToOne
    @JoinColumn(name = "user_id")    
    private User id; 
}
