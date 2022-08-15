package com.betpawa.wallet.service;

import com.betpawa.wallet.dto.Balance;
import com.betpawa.wallet.dto.MoneyTransferData;
import com.betpawa.wallet.dto.MoneyTransferResult;
import com.betpawa.wallet.model.User;
import java.util.Optional;

public interface WalletService {
    MoneyTransferResult creditAccount(MoneyTransferData transferData);

    MoneyTransferResult debitAccount(MoneyTransferData transferData);

    Balance balance(Long accountId);

    Optional<User> findById(Long id);

}
