package com.betpawa.bet.service;

import com.betpawa.bet.dto.BetAcceptData;
import com.betpawa.bet.dto.BetAcceptResult;
import com.betpawa.bet.dto.BetStatus;
import com.betpawa.bet.model.ActiveBets;
import com.betpawa.bet.model.BetSlips;
import com.betpawa.bet.repository.ActiveBetRepository;
import com.betpawa.bet.repository.BetRepository;
import com.betpawa.bet.rest.request.BetActionRequest;
import com.betpawa.wallet.api.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BetDefaultService implements BetService{

    private static final Logger logger = Logger.getLogger(BetDefaultService.class.getName());

    private static WalletGrpcServiceGrpc.WalletGrpcServiceBlockingStub init() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(
                "localhost", 4565).usePlaintext().build();

        return WalletGrpcServiceGrpc.newBlockingStub(channel);
    }

    @Autowired
    BetRepository betRepository;

    @Autowired
    ActiveBetRepository activeBetRepository;

    @Override
    public BetAcceptResult acceptBet(BetAcceptData betData){
        AccountDetailsRequest accountDetailsRequest = AccountDetailsRequest.newBuilder().setAccountId(betData.accountId()).build();
        try {
            Optional<BetSlips> bet = betRepository.findById(betData.betId());
            if(bet.isEmpty()) {
                return new BetAcceptResult("Not found bet with following id: "
                        + betData.betId(), BetStatus.ERROR.toString());
            }

            BalanceResponse balanceResponse = init().balance(accountDetailsRequest);
            if (balanceResponse.getAmount() < betData.amount().longValue()) {
                return new BetAcceptResult("Bet is not accepted", BetStatus.NOT_ENOUGH_MONEY.toString());
            }

            MoneyTransferRequest moneyTransferRequest = MoneyTransferRequest.newBuilder()
                    .setAccountId(betData.accountId())
                    .setAmount(betData.amount().longValue())
                    .setReference("Bet")
                    .build();

            MoneyTransferResponse moneyTransferResponse = init().reserveMoney(moneyTransferRequest);
            BigDecimal possibleWin = calculatePossibleWin(bet, betData);

            activeBetRepository.save(new ActiveBets(
                    betData.accountId(),
                    bet.get().getBetId(),
                    bet.get().getOdd(),
                    bet.get().getEventDate(),
                    bet.get().getEventName(),
                    bet.get().getOutcome(),
                    possibleWin,
                    BetStatus.PENDING));

            return new BetAcceptResult("Bet was successful accepted!", BetStatus.PENDING.toString());

        } catch (Exception e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e);
            return new BetAcceptResult("Bet is not accepted due to following error: " +
                    e, BetStatus.ERROR.toString());
        }
    }

    @Override
    public String betAction(BetActionRequest betActionRequest) {
        ActiveBets targetBet = activeBetRepository.findActiveBetsByAccountIdAndBetId(betActionRequest.accountId(), betActionRequest.betId());

        switch (betActionRequest.action()) {
            case "WIN":
                MoneyTransferRequest winMoneyTransferRequest = MoneyTransferRequest.newBuilder().setAccountId(betActionRequest.accountId()).setAmount(targetBet.getPossibleWin().longValue()).setReference("WIN").build();
                MoneyTransferResponse winMoneyTransferResponse = init().addMoney(winMoneyTransferRequest);
                break;
            case "LOSE":
                MoneyTransferRequest loseMoneyTransferRequest = MoneyTransferRequest.newBuilder().setAccountId(betActionRequest.accountId()).setAmount(0L).setReference("LOSE").build();
                MoneyTransferResponse loseMoneyTransferResponse = init().addMoney(loseMoneyTransferRequest);
                break;
            default:
                return null;

        }

        activeBetRepository.deleteById(targetBet.getActiveBetId());
        return "Success";
    }


    private BigDecimal calculatePossibleWin(Optional<BetSlips> odd, BetAcceptData amount) {
        double possibleWin = amount.amount().doubleValue() * odd.get().getOdd().doubleValue();

        return new BigDecimal(possibleWin);
    }
}