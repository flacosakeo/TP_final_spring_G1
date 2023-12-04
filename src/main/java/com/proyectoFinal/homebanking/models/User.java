package com.proyectoFinal.homebanking.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {
    
    @Id//establece que este campo es PK
    @GeneratedValue(strategy=GenerationType.IDENTITY)//establece que el campo es autoincremenatl
    @Column(name="user_id")
    private Long id;
    
    @Column(name="correo")//le cambia el nombre a la columna en la tabla
    private String email;
    
    @Column(name="contraseña")
    private String password;
    
    @Column(name="nombre")
    private String name;
    
    @Column(name="usuario")
    private String username;
    
    @Column(name="documento")
    private String dni;
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;
}
