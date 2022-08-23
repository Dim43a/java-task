package com.betpawa.wallet.model.wallet;

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
public class Operations {

//    List of operations made for the account
//    Operation type: deposit, withdrawal, bet, win
//    When it was
//    Amount transferred
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long operationId;

//    @ManyToOne
//    @JoinColumn(name = "id")
//    private User user;

    private String operationType;
    private LocalDateTime operationDate;
    private BigDecimal amount;
    private Long userId;

    public Operations(String operationType, LocalDateTime operationDate, BigDecimal amount, Long userId) {
        this.operationType = operationType;
        this.operationDate = operationDate;
        this.amount = amount;
        this.userId = userId;
    }
}