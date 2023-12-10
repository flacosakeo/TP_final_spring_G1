
package com.proyectoFinal.homebanking.models.DTO;
import com.proyectoFinal.homebanking.models.Enum.AccountAlias;
import com.proyectoFinal.homebanking.models.Enum.AccountType;
import com.proyectoFinal.homebanking.models.User;
import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    private Long id;
    private AccountType accountType;
    private Long ownerId;
    private String cbu;
    private AccountAlias alias;
    private BigDecimal amount;
    //private Long user_id;
}
