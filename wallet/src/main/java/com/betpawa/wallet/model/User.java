package com.betpawa.wallet.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="balance")
    private BigDecimal balance = new BigDecimal("0.0");
}