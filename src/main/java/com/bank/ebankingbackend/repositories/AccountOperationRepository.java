package com.bank.ebankingbackend.repositories;

import com.bank.ebankingbackend.entities.AccountOperation;
import com.bank.ebankingbackend.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByBankAccountId(String accounId);

    Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accounId, Pageable pageable);

}
