package com.proyectoFinal.homebanking.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
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
    
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //@JoinColumn(name="user_id", nullable=false)
    private List<Account> accounts=new ArrayList<>();
}
