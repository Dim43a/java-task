package com.betpawa.bet.rest;

import com.betpawa.bet.dto.BetAcceptData;
import com.betpawa.bet.dto.BetAcceptResult;
import com.betpawa.bet.model.BetSlips;
import com.betpawa.bet.repository.ActiveBetRepository;
import com.betpawa.bet.repository.BetRepository;
import com.betpawa.bet.rest.response.ActiveBet;
import com.betpawa.bet.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/bet")
@RequiredArgsConstructor
public class BetController {
    private final BetService service;

    @Autowired
    ActiveBetRepository activeBetRepository;

    @Autowired
    BetRepository betRepository;

    //OK
    @PostMapping("all")
    public Iterable<BetSlips> allBets() {
        return betRepository.findAll();
    }

    @PostMapping("active/{accountId}")
    public List<ActiveBet> findBetsByAccountId(@PathVariable Long accountId) {
        return activeBetRepository.findActiveBetsByAccountId(accountId);

    }

    @PostMapping("acceptBet")
    public BetAcceptResult acceptBet(BetAcceptData betAcceptData) {

        return service.acceptBet(betAcceptData);
    }
}