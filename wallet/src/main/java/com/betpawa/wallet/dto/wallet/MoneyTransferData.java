package com.betpawa.wallet.dto.wallet;

import java.math.BigDecimal;

public record MoneyTransferData(Long accountId, BigDecimal amount, String reference) {
}
