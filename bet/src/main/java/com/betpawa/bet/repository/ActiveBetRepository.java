package com.betpawa.bet.repository;

import com.betpawa.bet.model.ActiveBets;
import com.betpawa.bet.rest.response.ActiveBet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActiveBetRepository extends CrudRepository<ActiveBets, Long> {

    List<ActiveBet> findActiveBetsByAccountId(Long accountId);

    ActiveBets findActiveBetsByAccountIdAndBetId(Long AccountId, Long betId);
}