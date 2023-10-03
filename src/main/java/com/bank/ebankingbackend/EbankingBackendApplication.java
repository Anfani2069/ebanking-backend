package com.bank.ebankingbackend;

import com.bank.ebankingbackend.dtos.BankAccountDTO;
import com.bank.ebankingbackend.dtos.CurrentBankAccountDTO;
import com.bank.ebankingbackend.dtos.CustomerDTO;
import com.bank.ebankingbackend.dtos.SavingBankAccountDTO;
import com.bank.ebankingbackend.entities.*;
import com.bank.ebankingbackend.enums.AccountStatus;
import com.bank.ebankingbackend.enums.OperationType;
import com.bank.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.bank.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.bank.ebankingbackend.exceptions.CustomerNotFoundException;
import com.bank.ebankingbackend.repositories.AccountOperationRepository;
import com.bank.ebankingbackend.repositories.BankAccountRepository;
import com.bank.ebankingbackend.repositories.CustomerRepository;
import com.bank.ebankingbackend.service.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {


    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);


    }

	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
		return  args -> {
			Stream.of("Hassan", "Iman", "Mohamed").forEach(name->{
				CustomerDTO customer = new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");

				bankAccountService.saveCustomer(customer);
			});

			bankAccountService.listCustomer().forEach(customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random()*90000,90000, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random()*120000, 5.5, customer.getId());



				} catch (CustomerNotFoundException e) {

					//Afficher la trace de l'exception
					e.printStackTrace();
				}
            });
			List<BankAccountDTO> bankAccountDTOS = bankAccountService.bankAccountList();
			for (BankAccountDTO bankAccount : bankAccountDTOS) {
				for (int i = 0; i<10; i++){
					String accountId;
					if (bankAccount instanceof SavingBankAccountDTO){
						accountId=((SavingBankAccountDTO) bankAccount).getId();
					}else {
						accountId=((CurrentBankAccountDTO) bankAccount).getId();
					}
					bankAccountService.credit(accountId, 1000+Math.random()*12000,"Credit");
					bankAccountService.debit(accountId, 1000+Math.random()*9000, "Debit");
				}

			}
		};

	}
	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository){

		return args -> {
				Stream.of("Hassan", "Yassine", "Aicha").forEach(name -> {
					Customer customer = new Customer();
					customer.setName(name);
					customer.setEmail(name + "@gmail.com");
					customerRepository.save(customer);
				});

				customerRepository.findAll().forEach(cust->{
					CurrentAccount currentAccount = new CurrentAccount();

					currentAccount.setId(UUID.randomUUID().toString());
					currentAccount.setBalance(Math.random()*900000);
					currentAccount.setCreatedAt(new Date());
					currentAccount.setStatus(AccountStatus.CREATED);
					currentAccount.setCustomer(cust);
					currentAccount.setOverDraft(9000);
					bankAccountRepository.save(currentAccount);

					SavingAccount savingAccount = new SavingAccount();
					savingAccount.setId(UUID.randomUUID().toString());
					savingAccount.setBalance(Math.random()*90000);
					savingAccount.setCreatedAt(new Date());
					savingAccount.setStatus(AccountStatus.CREATED);
					savingAccount.setCustomer(cust);
					savingAccount.setInterestRate(5.5);
					bankAccountRepository.save(savingAccount);
				});

				bankAccountRepository.findAll().forEach(acc->{
					for(int i = 0; i<10; i++){
						AccountOperation accountOperation = new AccountOperation();
						accountOperation.setOperationDate(new Date());
						accountOperation.setAmount(Math.random()*12000);
						accountOperation.setType(Math.random()>0.5 ? OperationType.DEBIT:OperationType.CREDIT);
						accountOperation.setBankAccount(acc);
						accountOperationRepository.save(accountOperation);
					}
						}

				);
		};
	}

}


