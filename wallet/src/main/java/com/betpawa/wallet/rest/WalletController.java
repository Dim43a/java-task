package com.betpawa.wallet.rest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.betpawa.wallet.model.wallet.Operations;
import com.betpawa.wallet.repository.OperationRepository;
import com.betpawa.wallet.repository.UserRepository;
import com.betpawa.wallet.model.wallet.Users;
import com.betpawa.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.betpawa.wallet.dto.wallet.Balance;
import com.betpawa.wallet.dto.wallet.MoneyTransferData;
import com.betpawa.wallet.dto.wallet.MoneyTransferResult;
import com.betpawa.wallet.rest.request.DepositRequest;
import com.betpawa.wallet.rest.request.LoginAccountRequest;
import com.betpawa.wallet.rest.request.NewAccountRequest;
import com.betpawa.wallet.rest.request.WithdrawRequest;
import com.betpawa.wallet.rest.response.BalanceResponse;
import com.betpawa.wallet.rest.response.OperationResponse;
import com.betpawa.wallet.rest.response.OperationsResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationRepository operationRepository;

    //OK
    @PostMapping("deposit/{account}")
    public OperationResponse deposit(@PathVariable("account") Long account, DepositRequest request) {

        final MoneyTransferResult moneyTransferResult = service.debitAccount(new MoneyTransferData(account, BigDecimal.valueOf(request.getAmount()), request.getReference()));
        Optional<Users> user = userRepository.findById(account);
        return new OperationResponse(moneyTransferResult.status(), moneyTransferResult.message(), user.get().getBalance());
    }

    //OK
    @PostMapping("withdraw/{account}")
    public OperationResponse withdraw(@PathVariable("account") Long account, WithdrawRequest request) {

        //Wrong or not existing id handling
        final MoneyTransferResult moneyTransferResult = service.creditAccount(new MoneyTransferData(account, BigDecimal.valueOf(request.getAmount()), request.getReference()));
        Optional<Users> user = userRepository.findById(account);
        return new OperationResponse(moneyTransferResult.status(), moneyTransferResult.message(), user.get().getBalance());
    }

    @PostMapping("account")
    public BalanceResponse create(NewAccountRequest request) {

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        user.setBalance(new BigDecimal("0.0"));

        try {
            userRepository.save(user);
            return new BalanceResponse("User successful created", user.getId(), user.getBalance());

        } catch(Exception e)  {
            return new BalanceResponse("Error "+ e.getMessage(), user.getId(), null);
        }
    }

    @PostMapping("find-account")
    public BalanceResponse findAccount(LoginAccountRequest request) {
        Long accountId = userRepository.findId(request.getEmail(), request.getPassword());

        //Wrong email or password handling
        if(accountId == null) {
            return new BalanceResponse("Account not found, please try again.", accountId, null);
        }
        BigDecimal amount = userRepository.findBalance(request.getEmail(), request.getPassword());
        return new BalanceResponse("Account found", accountId, amount);
    }

    //OK
    @GetMapping("balance/{account}")
    public BalanceResponse balance(@PathVariable("account") Long account) {
        Optional <Users> user = userRepository.findById(account);

        //Wrong or not existing id handling
        if(user.isEmpty()) {
            return new BalanceResponse("Balance with requested id is not found",  null, null);
        }
        final Balance balance = service.balance(account);

        return new BalanceResponse("Current balance", user.get().getId(), balance.amount());
    }

    @GetMapping("operations/{account}")
    public OperationsResponse listOperations(@PathVariable("account") Long account, @RequestParam(value = "take", defaultValue = "20") int take,
                                             @RequestParam(value = "skip", defaultValue = "0") int skip) {

        //Wrong or not existing id handling
        Optional<Users> user = userRepository.findById(account);
        if(user.isEmpty()) {
            return new OperationsResponse("Operations with requested id not found", Collections.emptyList(), false);
        }
        List<Operations> opr = operationRepository.findOperationsByUserId(account);

        return new OperationsResponse("Operations with requested id:", opr, true);
    }
}
