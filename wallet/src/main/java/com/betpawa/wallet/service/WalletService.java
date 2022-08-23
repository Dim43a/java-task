package com.betpawa.wallet.service;

import com.betpawa.wallet.dto.wallet.Balance;
import com.betpawa.wallet.dto.wallet.MoneyTransferData;
import com.betpawa.wallet.dto.wallet.MoneyTransferResult;

public interface WalletService {
    MoneyTransferResult creditAccount(MoneyTransferData transferData);

    MoneyTransferResult debitAccount(MoneyTransferData transferData);

    Balance balance(Long accountId);
}
