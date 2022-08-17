package com.betpawa.wallet;

import com.betpawa.wallet.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.balance = :balance + u.balance WHERE u.id = :id")
    void debitBalance(Long id, BigDecimal balance);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.balance = :balance - u.balance WHERE u.id = :id")
    void creditBalance(Long id, BigDecimal balance);

    @Query("SELECT id FROM User u WHERE u.email LIKE %:email% AND u.password LIKE %:password% ")
    Long findId(String email, String password);

    @Query("SELECT balance FROM User u WHERE u.email LIKE %:email% AND u.password LIKE %:password% ")
    BigDecimal findBalance(String email, String password);
}