package com.betpawa.wallet.service;

import com.betpawa.wallet.model.Operation;
import com.betpawa.wallet.model.OperationType;
import com.betpawa.wallet.repository.OperationRepository;
import com.betpawa.wallet.repository.UserRepository;
import com.betpawa.wallet.dto.TransferStatusType;
import com.betpawa.wallet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.betpawa.wallet.dto.Balance;
import com.betpawa.wallet.dto.MoneyTransferData;
import com.betpawa.wallet.dto.MoneyTransferResult;
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
        MoneyTransferResult moneyTransferResult = new MoneyTransferResult(transferData.accountId(), TransferStatusType.OK, transferData.reference());
        //Check for non-null amount. We can not debit negative amount
        if(transferData.amount().intValue() < 0) {
            return new MoneyTransferResult(transferData.accountId(),TransferStatusType.FAIL, "Not allowed to credit below zero value" );
        }

        Optional<User> user = userRepository.findById(transferData.accountId());

        Operation operation = new Operation(OperationType.DEPOSIT.toString(), LocalDateTime.now(), transferData.amount(), transferData.accountId());
        operationRepository.save(operation);

        if(user.get().getBalance().intValue() < transferData.amount().intValue()) {
            return new MoneyTransferResult(transferData.accountId(),TransferStatusType.NOT_ENOUGH_MONEY, "Not enough money to withdraw " + transferData.amount() + "$");
        }

        userRepository.creditBalance(transferData.accountId(), transferData.amount());

        return moneyTransferResult;
//        throw new IllegalStateException("Implement");
    }

    @Override
    public MoneyTransferResult debitAccount(MoneyTransferData transferData) {
        MoneyTransferResult moneyTransferResult = new MoneyTransferResult(transferData.accountId(), TransferStatusType.OK, transferData.reference());
        //Check for non-null amount. We can not debit negative amount

        Operation operation = new Operation(OperationType.DEPOSIT.toString(), LocalDateTime.now(), transferData.amount(), transferData.accountId());
        operationRepository.save(operation);

        if(transferData.amount().intValue() < 0) {
            return new MoneyTransferResult(transferData.accountId(), TransferStatusType.FAIL, "Not allowed to debit below zero value");
        }
        userRepository.debitBalance(transferData.accountId(), transferData.amount());
        return moneyTransferResult;
    }

    @Override
    public Balance balance(Long id) {
        return new Balance(id, userRepository.findById(id).get().getBalance());
    }

//    @Override
//    public Optional<User> findById(Long id) {
//        return userRepository.findById(id);
//    }
}
