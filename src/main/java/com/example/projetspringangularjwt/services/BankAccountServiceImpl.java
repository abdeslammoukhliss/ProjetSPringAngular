package com.example.projetspringangularjwt.services;

import com.example.projetspringangularjwt.dtos.*;
import com.example.projetspringangularjwt.entities.*;
import com.example.projetspringangularjwt.exceptions.BankAccountNotFoundException;
import com.example.projetspringangularjwt.exceptions.CostumerNotFound;
import com.example.projetspringangularjwt.exceptions.InsufficientBalanceException;
import com.example.projetspringangularjwt.mappers.BankAccountMapperImpl;
import com.example.projetspringangularjwt.mappers.CostumerDTO;
import com.example.projetspringangularjwt.repositories.BankAccountRepository;
import com.example.projetspringangularjwt.repositories.CostumerRepository;
import com.example.projetspringangularjwt.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private CostumerRepository costumerRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private BankAccountMapperImpl mapper;
    Logger logger;

    BankAccountServiceImpl() {
        logger = LoggerFactory.getLogger(this.getClass().getName());
        logger.info("BankAccountServiceImpl created");
    }

    @Override
    public CostumerDTO saveCostumer(CostumerDTO costumer) {
        logger.info("saveCostumer");
        Costumer savedCostumer = costumerRepository.save(mapper.fromCustomerDTO(costumer));
        System.out.println(mapper.fromCostumer(savedCostumer));
        return mapper.fromCostumer(savedCostumer);
    }

//    @Override
//    public BankAccount saveBankAccount(double initialBalance, String accountType, Long costumerId) throws CostumerNotFound {
//        BankAccount bankAccount;
//        Costumer costumer = costumerRepository.findById(costumerId).orElse(null);
//        if (costumer == null) {
//            logger.error("Costumer not found");
//            throw new CostumerNotFound("Costumer not found");
//        }
//        if (accountType.equals("SA")) {
//            bankAccount = new SavingAccount();
//
//        } else {
//            bankAccount = new CurrentAccount();
//        }
//        bankAccount.setUuid(UUID.randomUUID().toString());
//        bankAccount.setBalance(initialBalance);
//        bankAccount.setCostumer(costumer);
//        bankAccount.setCreatedAt(new Date());
//        bankAccountRepository.save(bankAccount);
//
//        return null;
//    }

    @Override
    public CurrentBankAccountDTO saveCurrentAccount(double initialBalance, double overDraft, Long costumerId) throws CostumerNotFound{
        Costumer costumer = costumerRepository.findById(costumerId).orElse(null);
        if (costumer == null) {
            logger.error("Costumer not found");
            throw new CostumerNotFound("Costumer not found");
        }
        CurrentAccount bankAccount = new CurrentAccount();
        bankAccount.setUuid(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCostumer(costumer);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setOverDraft(overDraft);
        bankAccountRepository.save(bankAccount);
        return mapper.fromCurrentBankAccount(bankAccount);
    }



    @Override
    public SavingBankAccountDTO saveSavingAccount(double initialBalance, double rate, Long costumerId) throws CostumerNotFound{
        Costumer costumer = costumerRepository.findById(costumerId).orElse(null);
        if (costumer == null) {
            logger.error("Costumer not found");
            throw new CostumerNotFound("Costumer not found");
        }
        SavingAccount bankAccount = new SavingAccount();
        bankAccount.setUuid(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCostumer(costumer);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setInterestRate(rate);
        bankAccountRepository.save(bankAccount);
        return mapper.fromSavingBankAccount(bankAccount);
    }



    @Override
    public List<BankAccountDTO> findBankAccountByCostumerId(Long costumerId) {
        List<BankAccount> bankAccounts = bankAccountRepository.findBankAccountsByCostumer_Id(costumerId);
        List<BankAccountDTO> bankAccountDTOS = new ArrayList<>();
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                bankAccountDTOS.add(mapper.fromSavingBankAccount(savingAccount));
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                bankAccountDTOS.add(mapper.fromCurrentBankAccount(currentAccount));
            }
        }
        return bankAccountDTOS;
    }

    @Override
    public List<CostumerDTO> listCostumers() {
        List<Costumer> costumers= costumerRepository.findAll();
        List<CostumerDTO> costumerDTOS=costumers.stream().map(costumer -> {
            return mapper.fromCostumer(costumer);
        }).toList();
        return costumerDTOS;
    }

    @Override
    public List<CostumerDTO> listCostumersByKeyword(String keyword) {
        List<Costumer> costumers= costumerRepository.searchCostumer(keyword);
        List<CostumerDTO> costumerDTOS=costumers.stream().map(costumer -> {
            return mapper.fromCostumer(costumer);
        }).toList();
        return costumerDTOS;
    }

    @Override
    public void debit(double amount, String bankAccountId, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(bankAccountId).orElseThrow(()->
                new BankAccountNotFoundException("Bank account not found"));
        if(bankAccount.getBalance()<amount){
            throw new InsufficientBalanceException("Insufficient balance");
        }
        Operation operation=new Operation();
        operation.setAmount(amount);
        operation.setDate(new Date());
        operation.setBankAccount(bankAccount);
        operation.setDescription(description);
        operation.setType(OperationType.DEBIT);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);

    }

    @Override
    public void credit(double amount, String bankAccountId, String description) {
        BankAccount bankAccount=bankAccountRepository.findById(bankAccountId).orElseThrow(()->
                new BankAccountNotFoundException("Bank account not found"));
        if(bankAccount.getBalance()<amount){
            throw new InsufficientBalanceException("Insufficient balance");
        }
        Operation operation=new Operation();
        operation.setAmount(amount);
        operation.setDate(new Date());
        operation.setBankAccount(bankAccount);
        operation.setDescription(description);
        operation.setType(OperationType.CREDIT);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
    }

    @Override
    public void transfer(double amount, String bankAccountId1, String bankAccountId2) {
        transfer(amount,bankAccountId1,bankAccountId2);
        debit(amount,bankAccountId1,"Transfer to "+bankAccountId2);
        credit(amount,bankAccountId2,"Transfer from "+bankAccountId1);
    }

    @Override
    public BankAccountDTO getBankAccount(String bankAccountId) {
       BankAccount bankAccount= bankAccountRepository.findById(bankAccountId).orElseThrow(()->
                new RuntimeException("Bank account not found"));
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount= (SavingAccount) bankAccount;
            return mapper.fromSavingBankAccount(savingAccount);
        } else {
            CurrentAccount currentAccount= (CurrentAccount) bankAccount;
            return mapper.fromCurrentBankAccount(currentAccount);
        }
    }

    @Override
    public CostumerDTO getCostumer(Long id) {
        Costumer costumer= costumerRepository.findById(id).orElseThrow(()->
                new CostumerNotFound(
                        "Costumer not found"));
        return mapper.fromCostumer(costumer);
    }

    @Override
    public CostumerDTO updateCostumer(CostumerDTO costumerDTO) {
        Costumer costumer= costumerRepository.findById(costumerDTO.getId()).orElseThrow(()->
                new CostumerNotFound(
                        "Costumer not found"));
        costumer.setName(costumerDTO.getName());
        costumer.setEmail(costumerDTO.getEmail());
        costumerRepository.save(costumer);
        return mapper.fromCostumer(costumer);
    }

    @Override
    public void deleteCostumer(Long id) {
        Costumer costumer= costumerRepository.findById(id).orElseThrow(()->
                new CostumerNotFound(
                        "Costumer not found"));
        costumerRepository.deleteById(id);
    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        List<BankAccount> bankAccounts= bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS=new ArrayList<>();
        for (BankAccount bankAccount:bankAccounts){
            if(bankAccount instanceof SavingAccount){
                SavingAccount savingAccount= (SavingAccount) bankAccount;
                bankAccountDTOS.add(mapper.fromSavingBankAccount(savingAccount));
            } else {
                CurrentAccount currentAccount= (CurrentAccount) bankAccount;
                bankAccountDTOS.add(mapper.fromCurrentBankAccount(currentAccount));
            }
        }
        return bankAccountDTOS;
    }
    @Override
    public List<OperationDTO> accountHistory(String accountId){
        List<Operation> accountOperations = operationRepository.findByBankAccount_Uuid(accountId);
        return accountOperations.stream().map(op->mapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<Operation> accountOperations = operationRepository.findByBankAccount_UuidOrderByDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<OperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> mapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getUuid());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CostumerDTO> searchCustomers(String keyword) {
        List<Costumer> costumers= costumerRepository.searchCostumer(keyword);
        return costumers.stream().map(costumer -> mapper.fromCostumer(costumer)).collect(Collectors.toList());
    }


}
