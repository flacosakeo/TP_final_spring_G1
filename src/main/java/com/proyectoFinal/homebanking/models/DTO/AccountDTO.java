
package com.proyectoFinal.homebanking.models.DTO;
import com.proyectoFinal.homebanking.models.Enum.AccountAlias;
import com.proyectoFinal.homebanking.models.Enum.AccountType;
import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    private Long id;
    private AccountType tipo;
    private String dueño;
    private String cbu;
    private AccountAlias alias;
    private BigDecimal monto;
}
