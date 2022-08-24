package com.betpawa.bet.service;

import com.betpawa.bet.dto.BetAcceptData;
import com.betpawa.bet.dto.BetAcceptResult;
import com.betpawa.bet.dto.BetStatus;
import com.betpawa.bet.model.ActiveBets;
import com.betpawa.bet.model.BetSlips;
import com.betpawa.bet.repository.ActiveBetRepository;
import com.betpawa.bet.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BetDefaultService implements BetService{

    @Autowired
    BetRepository betRepository;

    @Autowired
    ActiveBetRepository activeBetRepository;

    @Override
    public BetAcceptResult acceptBet(BetAcceptData betData){

        Optional<BetSlips> bet = betRepository.findById(betData.betId());

        //Need to extract this
        Double amount = betData.amount().doubleValue();

        Double odd = bet.get().getOdd().doubleValue();

        double possibleWin = amount * odd;

        BigDecimal lala = new BigDecimal(possibleWin);


        activeBetRepository.save(new ActiveBets(
                betData.accountId(),
                bet.get().getBetId(),
                bet.get().getOdd(),
                bet.get().getEventDate(),
                bet.get().getEventName(),
                bet.get().getOutcome(),
                lala,
                BetStatus.PENDING));

        return new BetAcceptResult("dsds");
    }
}