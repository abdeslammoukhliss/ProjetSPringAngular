package com.example.projetspringangularjwt.services;

import com.example.projetspringangularjwt.entities.*;
import com.example.projetspringangularjwt.repositories.BankAccountRepository;
import com.example.projetspringangularjwt.repositories.CostumerRepository;
import com.example.projetspringangularjwt.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Transactional
public class BankService {
    @Autowired
    private CostumerRepository costumerRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private  OperationRepository operationRepository;
    @Autowired
    private BankAccountService bankAccountService;

    public void consulter(){
        System.out.println("Hello World");
        Stream.of("C1","C2","C3").forEach(c->{
            Costumer costumer= new Costumer();
            costumer.setName(c);
            costumer.setEmail(c+"@gmail.com");
            costumerRepository.save(costumer);
            costumerRepository.findAll().forEach(costumer1 -> {
                Stream.of("CC1","CC2","CC3").forEach(b->{
                    CurrentAccount currentAccount = new CurrentAccount();
                    currentAccount.setUuid( UUID.randomUUID().toString());
                    currentAccount.setBalance(1000D);
                    currentAccount.setCostumer(costumer1);
                    currentAccount.setOverDraft(1000D);
                    currentAccount.setCurrency("EURO");
                    currentAccount.setStatus(AccountStatus.SUSPENDED);
                    bankAccountRepository.save(currentAccount);
                });
                Stream.of("SA1","SA2","SA3").forEach(b->{
                    SavingAccount savingAccount = new SavingAccount();
                    savingAccount.setUuid( UUID.randomUUID().toString());
                    savingAccount.setBalance(1000D);
                    savingAccount.setCostumer(costumer1);
                    savingAccount.setInterestRate(3.5D);
                    savingAccount.setCurrency("EURO");
                    savingAccount.setStatus(AccountStatus.ACTIVE);
                    bankAccountRepository.save(savingAccount);
                });
                bankAccountRepository.findAll().forEach(bankAccount -> {
                   // System.out.println(bankAccount.toString());

                    for (int i= 0; i < 10; i++) {
                        Operation accountOperation = new Operation();
                        accountOperation.setType(Math.random()>0.5?OperationType.CREDIT:OperationType.DEBIT);
                        accountOperation.setBankAccount(bankAccount);
                        accountOperation.setDate(new Date());
                        accountOperation.setAmount(1000D*Math.random());
                        operationRepository.save(accountOperation);

                    }
                });
            });
        });
    }
    public void consulter2(){
        System.out.println("Hello World");
        Stream.of("C1","C2","C3").forEach(c->{
            Costumer costumer= new Costumer();
            costumer.setName(c);

            costumer.setEmail(c+"@gmail.com");
            costumerRepository.save(costumer);
            List<Costumer> costumers=costumerRepository.findAll();

            costumerRepository.findAll().forEach(costumer1 -> {
                Stream.of("CC1","CC2","CC3").forEach(b->{
                    CurrentAccount currentAccount = new CurrentAccount();
                    currentAccount.setUuid( UUID.randomUUID().toString());
                    currentAccount.setBalance(1000D);
                    currentAccount.setCostumer(costumer1);
                    currentAccount.setOverDraft(1000D);
                    currentAccount.setCreatedAt(new Date());
                    currentAccount.setCurrency("EURO");
                    currentAccount.setStatus(AccountStatus.SUSPENDED);
                    bankAccountRepository.save(currentAccount);
                });
                Stream.of("SA1","SA2","SA3").forEach(b->{
                    SavingAccount savingAccount = new SavingAccount();
                    savingAccount.setUuid( UUID.randomUUID().toString());
                    savingAccount.setBalance(1000D);
                    savingAccount.setCostumer(costumer1);
                    savingAccount.setInterestRate(3.5D);
                    savingAccount.setCurrency("EURO");
                    savingAccount.setCreatedAt(new Date());
                    savingAccount.setStatus(AccountStatus.ACTIVE);
                    bankAccountRepository.save(savingAccount);
                });
                bankAccountRepository.findAll().forEach(bankAccount -> {
                    // System.out.println(bankAccount.toString());

                    for (int i= 0; i < 10; i++) {
                        Operation accountOperation = new Operation();
                        accountOperation.setType(Math.random()>0.5?OperationType.CREDIT:OperationType.DEBIT);
                        accountOperation.setBankAccount(bankAccount);
                        accountOperation.setDate(new Date());
                        accountOperation.setAmount(1000D*Math.random());
                        operationRepository.save(accountOperation);

                    }
                });
            });
        });
    }
}
