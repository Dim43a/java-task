package com.betpawa.bet.service;

import com.betpawa.bet.dto.BetAcceptData;
import com.betpawa.bet.dto.BetAcceptResult;
import com.betpawa.bet.rest.request.BetActionRequest;

public interface BetService {

    BetAcceptResult acceptBet(BetAcceptData betData);

    String betAction(BetActionRequest betActionRequest);
}