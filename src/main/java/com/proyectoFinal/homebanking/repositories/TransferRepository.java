package com.proyectoFinal.homebanking.repositories;

import com.proyectoFinal.homebanking.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer,Long>{
    
}
