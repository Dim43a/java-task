package com.betpawa.bet.dto;

import java.math.BigDecimal;

public record BetAcceptData(Long accountId, Long betId, BigDecimal amount) {
}