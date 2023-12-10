
package com.proyectoFinal.homebanking.repositories;

import com.proyectoFinal.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{
    Account findByCbu(String cbu);
    boolean existsByCbu(String cbu);
}
