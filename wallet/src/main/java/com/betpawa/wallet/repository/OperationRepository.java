package com.betpawa.wallet.repository;

import com.betpawa.wallet.model.Operation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Long> {
    List<Operation> findOperationsByUserId(Long id);

}