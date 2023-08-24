package com.pagamentos.domain.user;

import com.pagamentos.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GeneratorType;

import java.math.BigDecimal;

@Entity(name ="users")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String documento;
    @Column(unique = true)
    private String email;
    private String senha;
    private BigDecimal saldo;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public  User(UserDTO novoUsurious){
        this.firstName = novoUsurious.firstName();
        this.lastName = novoUsurious.lastName();
        this.documento = novoUsurious.documento();
        this.saldo = novoUsurious.saldo();
        this.userType = novoUsurious.userType();
        this.senha = novoUsurious.senha();
        this.email = novoUsurious.email();
    }


}
