package com.betpawa.wallet.rest;

import java.math.BigDecimal;
import java.util.List;
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

        //Total balance after deposit
        final Balance balance = service.balance(account);
        return new OperationResponse(moneyTransferResult.status().name(), moneyTransferResult.message(), balance.amount());
    }

    //OK
    @PostMapping("withdraw/{account}")
    public OperationResponse withdraw(@PathVariable("account") Long account, WithdrawRequest request) {
        final MoneyTransferResult moneyTransferResult = service.creditAccount(new MoneyTransferData(account, BigDecimal.valueOf(request.getAmount()), request.getReference()));
        //Total balance after withdraw
        final Balance balance = service.balance(account);
        return new OperationResponse(moneyTransferResult.status().name(), moneyTransferResult.message(), balance.amount());
    }

    //OK
    @PostMapping("account")
    public BalanceResponse create(NewAccountRequest request) {

        Users users = new Users();
        users.setEmail(request.getEmail());
        users.setPassword(request.getPassword());

        try {
            userRepository.save(users);
            return new BalanceResponse(users.getId(), new BigDecimal(0));

        } catch(Exception e)  {
            e.printStackTrace();
        }
        return null;
    }

    //OK
    @PostMapping("find-account")
    public BalanceResponse findAccount(LoginAccountRequest request) {
        Long accountId = userRepository.findId(request.getEmail(), request.getPassword());
        BigDecimal amount = userRepository.findBalance(request.getEmail(), request.getPassword());
        return new BalanceResponse(accountId, amount);
    }

    //OK
    @GetMapping("balance/{account}")
    public BalanceResponse balance(@PathVariable("account") Long account) {
        final Balance balance = service.balance(account);
        return new BalanceResponse(balance.accountId(), balance.amount());
    }

    //OK
    @GetMapping("operations/{account}")
    public OperationsResponse listOperations(@PathVariable("account") Long account, @RequestParam(value = "take", defaultValue = "20") int take,
                                             @RequestParam(value = "skip", defaultValue = "0") int skip) {

        List<Operations> opr = operationRepository.findOperationsByUserId(account);

        return new OperationsResponse(opr, true);
//        throw new IllegalStateException("Not implemented");
    }
}
