package com.example.projetspringangularjwt.services;

import com.example.projetspringangularjwt.dtos.AccountHistoryDTO;
import com.example.projetspringangularjwt.dtos.BankAccountDTO;
import com.example.projetspringangularjwt.dtos.CurrentBankAccountDTO;
import com.example.projetspringangularjwt.dtos.OperationDTO;
import com.example.projetspringangularjwt.entities.BankAccount;
import com.example.projetspringangularjwt.entities.Costumer;
import com.example.projetspringangularjwt.entities.CurrentAccount;
import com.example.projetspringangularjwt.entities.SavingAccount;
import com.example.projetspringangularjwt.exceptions.BankAccountNotFoundException;
import com.example.projetspringangularjwt.mappers.CostumerDTO;

import java.util.List;

public interface BankAccountService {
    CostumerDTO saveCostumer(CostumerDTO costumer);
   // SavingAccount saveBankAccount(double initialBalance, String accountType, Long costumerId);
    CurrentBankAccountDTO saveCurrentAccount(double initialBalance, double overDraft, Long costumerId);
    BankAccountDTO saveSavingAccount(double initialBalance, double rate, Long costumerId);
    List<BankAccountDTO> findBankAccountByCostumerId(Long costumerId);
    List<CostumerDTO> listCostumers();
   List<CostumerDTO> listCostumersByKeyword(String keyword);
    void debit(double amount, String bankAccountId,String description) throws BankAccountNotFoundException;
    void credit(double amount, String bankAccountId,String description) throws  BankAccountNotFoundException;
    void transfer(double amount, String bankAccountId1, String bankAccountId2);

    BankAccountDTO getBankAccount(String bankAccountId) throws BankAccountNotFoundException    ;
    CostumerDTO  getCostumer(Long id);
    CostumerDTO updateCostumer(CostumerDTO costumerDTO);
    void deleteCostumer(Long id);
    List<BankAccountDTO> bankAccountList();
    List<OperationDTO> accountHistory(String accountId);

 AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

 List<CostumerDTO> searchCustomers(String keyword);
}
