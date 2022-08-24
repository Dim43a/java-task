package com.betpawa.bet.rest.response;

import com.betpawa.bet.dto.BetStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ActiveBet(LocalDateTime eventDate,
                        String eventName, BigDecimal odd, BigDecimal possibleWin,
                        BetStatus status) {
}