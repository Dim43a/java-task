package com.betpawa.wallet.model.wallet;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private BigDecimal balance;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    Set<Operation> operations = new HashSet<Operation>();
}