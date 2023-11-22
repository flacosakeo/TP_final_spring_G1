
package com.proyectoFinal.homebanking.models;

import com.proyectoFinal.homebanking.models.Enum.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="cuentas")
@Getter
@Setter
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
    private Long cbu;
    
    @Column(name="alias")
    private String alias;
    
    @Column(name="importe")
    private Double monto;
    
    
}
