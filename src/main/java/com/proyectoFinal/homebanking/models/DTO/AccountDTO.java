
package com.proyectoFinal.homebanking.models.DTO;
import com.proyectoFinal.homebanking.models.Enum.AccountAlias;
import com.proyectoFinal.homebanking.models.Enum.AccountType;
import com.proyectoFinal.homebanking.models.User;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private AccountType tipo;
    private String dueño;
    private String cbu;
    private AccountAlias alias;
    private BigDecimal monto;
    //private Long user_id;
}
