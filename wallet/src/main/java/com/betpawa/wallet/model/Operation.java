package com.betpawa.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="operations")
public class Operation {

//    List of operations made for the account
//    Operation type: deposit, withdrawal, bet, win
//    When it was
//    Amount transferred
    @Id
    @Column(name="operation_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "id")
//    private User user;

    @Column(name="operation_Type")
    private String operationType;

    @Column(name="operation_date")
    private LocalDateTime operationDate;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="user_id")
    private Long userId;

    public Operation(String operationType, LocalDateTime operationDate, BigDecimal amount, Long userId) {
        this.operationType = operationType;
        this.operationDate = operationDate;
        this.amount = amount;
        this.userId = userId;
    }
}