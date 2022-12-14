package com.betpawa.wallet.repository;

import com.betpawa.wallet.model.wallet.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.balance = u.balance + :balance WHERE u.id = :id")
    void debitBalance(Long id, BigDecimal balance);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.balance = u.balance - :balance WHERE u.id = :id")
    void creditBalance(Long id, BigDecimal balance);

    @Query("SELECT id FROM Users u WHERE u.email LIKE %:email% AND u.password LIKE %:password% ")
    Long findId(String email, String password);

    @Query("SELECT balance FROM Users u WHERE u.email LIKE %:email% AND u.password LIKE %:password% ")
    BigDecimal findBalance(String email, String password);
}