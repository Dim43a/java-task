package com.betpawa.wallet.service;

import com.betpawa.wallet.model.wallet.Operations;
import com.betpawa.wallet.model.wallet.OperationType;
import com.betpawa.wallet.model.wallet.Users;
import com.betpawa.wallet.repository.OperationRepository;
import com.betpawa.wallet.repository.UserRepository;
import com.betpawa.wallet.dto.wallet.TransferStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.betpawa.wallet.dto.wallet.Balance;
import com.betpawa.wallet.dto.wallet.MoneyTransferData;
import com.betpawa.wallet.dto.wallet.MoneyTransferResult;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WalletDefaultService implements WalletService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OperationRepository operationRepository;

    @Override
    public MoneyTransferResult creditAccount(MoneyTransferData transferData) {
        Optional <Users> user = userRepository.findById(transferData.accountId());
        if(user.isEmpty()) {
            return new MoneyTransferResult(transferData.accountId(), TransferStatusType.FAIL, "User is not existing with request ID");
        }
        if(transferData.amount().intValue() <= 0) {
            return new MoneyTransferResult(transferData.accountId(),TransferStatusType.FAIL, "Not allowed to credit below zero or 0 value" );
        }
        if(userRepository.findById(transferData.accountId()).get().getBalance().intValue() < transferData.amount().intValue()) {
            return new MoneyTransferResult(transferData.accountId(),TransferStatusType.NOT_ENOUGH_MONEY, "Not enough money to withdraw " + transferData.amount() + "$");
        }
        Operations operation = new Operations(transferData.reference(), LocalDateTime.now(), transferData.amount(), transferData.accountId());
        operationRepository.save(operation);
        userRepository.creditBalance(transferData.accountId(), transferData.amount());

        return new MoneyTransferResult(transferData.accountId(), TransferStatusType.OK, transferData.reference());
    }

    @Override
    public MoneyTransferResult debitAccount(MoneyTransferData transferData) {

        if(transferData.amount().intValue() <= 0) {
            return new MoneyTransferResult(transferData.accountId(), TransferStatusType.FAIL, "Not allowed to debit below zero value");
        }
        userRepository.debitBalance(transferData.accountId(), transferData.amount());

        Operations operation = new Operations(transferData.reference(), LocalDateTime.now(), transferData.amount(), transferData.accountId());
        operationRepository.save(operation);

        return new MoneyTransferResult(transferData.accountId(), TransferStatusType.OK, transferData.reference());
    }

    @Override
    public Balance balance(Long id) {
        Optional<Users> user = userRepository.findById(id);
        if(user.isEmpty()) {
            return null;
        }

        return new Balance(id, userRepository.findById(id).get().getBalance());
    }
}
