package com.betpawa.wallet;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {


    @Query("SELECT name FROM User u WHERE u.name LIKE %:userName%")
    String findByEmail(String userName);
}