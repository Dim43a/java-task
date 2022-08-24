package com.betpawa.bet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ActiveAccountBets(BigDecimal odd, LocalDateTime eventDate, String eventName, String outCome, BigDecimal possibleWin, BetStatus status) {
}