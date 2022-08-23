package com.betpawa.wallet.dto.wallet;


public record MoneyTransferResult(Long accountId, TransferStatusType status, String message) {
}
