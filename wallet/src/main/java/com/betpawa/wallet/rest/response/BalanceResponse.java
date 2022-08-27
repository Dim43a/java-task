package com.betpawa.wallet.rest.response;

import java.math.BigDecimal;

public record BalanceResponse(String status, Long accountId, BigDecimal amount) {
}
