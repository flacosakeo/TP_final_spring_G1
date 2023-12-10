
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
    @Column(name="id")
    private Long id;
    
    @Column(name="account_type")
    private AccountType accountType;
    
    @Column(name="id_owner")
    private Long ownerId;
    
    @Column(name="CBU")

    private String cbu;
    
    @Column(name="alias")
    private AccountAlias alias;
    
    @Column(name="amount")
    private BigDecimal amount;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User idUser;
}
