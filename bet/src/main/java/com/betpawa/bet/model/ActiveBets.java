package com.betpawa.bet.model;

import com.betpawa.bet.dto.BetStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ActiveBets {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long activeBetId;

    private Long accountId;
    private Long betId;
    private BigDecimal odd;
    private LocalDateTime eventDate;
    private String eventName;
    private String outcome;
    private BigDecimal possibleWin;
    private BetStatus status;
    public ActiveBets(Long accountId, Long betId,
                      BigDecimal odd, LocalDateTime eventDate, String eventName,
                      String outcome, BigDecimal possibleWin, BetStatus status) {
        this.accountId = accountId;
        this.betId = betId;
        this.odd = odd;
        this.eventDate = eventDate;
        this.eventName = eventName;
        this.outcome = outcome;
        this.possibleWin = possibleWin;
        this.status = status;
    }
}