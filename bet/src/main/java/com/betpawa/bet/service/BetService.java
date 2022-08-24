package com.betpawa.bet.service;

import com.betpawa.bet.dto.BetAcceptData;
import com.betpawa.bet.dto.BetAcceptResult;

public interface BetService {

    BetAcceptResult acceptBet(BetAcceptData betData);
}