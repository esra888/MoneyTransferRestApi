package com.example.reporsitory;

import com.example.entity.AccountTransactionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionrepository extends CrudRepository<AccountTransactionEntity, Integer> {
    List<AccountTransactionEntity> findAll();
}
