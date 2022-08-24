package com.betpawa.bet.repository;

import com.betpawa.bet.model.BetSlips;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends CrudRepository<BetSlips, Long> {

}