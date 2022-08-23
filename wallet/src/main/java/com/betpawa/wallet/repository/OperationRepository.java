package com.betpawa.wallet.repository;

import com.betpawa.wallet.model.wallet.Operations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OperationRepository extends CrudRepository<Operations, Long> {
    List<Operations> findOperationsByUserId(Long id);

}