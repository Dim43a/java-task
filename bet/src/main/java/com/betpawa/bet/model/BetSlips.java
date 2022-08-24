package com.betpawa.bet.model;

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
public class BetSlips {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long betId;

    private BigDecimal odd;
    private LocalDateTime eventDate;
    private String eventName;
    private String outcome;
}