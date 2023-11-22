
package com.proyectoFinal.homebanking.models.DTO;
import com.proyectoFinal.homebanking.models.Enum.AccountType;
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
    private Long cbu;
    private String alias;
    private Double monto;
}
