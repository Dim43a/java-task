package com.betpawa.wallet.dto.wallet;

import java.math.BigDecimal;

public record Balance(Long accountId, BigDecimal amount) {
}
