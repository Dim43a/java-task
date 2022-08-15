package com.betpawa.wallet.service;

import com.betpawa.wallet.UserRepository;
import com.betpawa.wallet.dto.TransferStatusType;
import com.betpawa.wallet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.betpawa.wallet.dto.Balance;
import com.betpawa.wallet.dto.MoneyTransferData;
import com.betpawa.wallet.dto.MoneyTransferResult;
import java.util.Optional;

@Service
public class WalletDefaultService implements WalletService {

    @Autowired
    UserRepository userRepository;

    @Override
    public MoneyTransferResult creditAccount(MoneyTransferData transferData) {
        MoneyTransferResult test = new MoneyTransferResult(transferData.accountId(), TransferStatusType.OK, transferData.reference());

        userRepository.creditBalance(transferData.accountId(), transferData.amount());

        return test;
//        throw new IllegalStateException("Implement");
    }

    @Override
    public MoneyTransferResult debitAccount(MoneyTransferData transferData) {
        MoneyTransferResult test = new MoneyTransferResult(transferData.accountId(), TransferStatusType.OK, transferData.reference());
        userRepository.debitBalance(transferData.accountId(), transferData.amount());
        return test;
    }

    @Override
    public Balance balance(Long id) {
        return new Balance(id, userRepository.findById(id).get().getBalance());
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
