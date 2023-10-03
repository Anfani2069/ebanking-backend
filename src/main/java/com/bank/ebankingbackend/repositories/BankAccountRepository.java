package com.bank.ebankingbackend.repositories;

import com.bank.ebankingbackend.entities.BankAccount;
import com.bank.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
