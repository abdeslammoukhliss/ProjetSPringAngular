package com.example.projetspringangularjwt.mappers;

import com.example.projetspringangularjwt.dtos.CurrentBankAccountDTO;
import com.example.projetspringangularjwt.dtos.OperationDTO;
import com.example.projetspringangularjwt.dtos.SavingBankAccountDTO;
import com.example.projetspringangularjwt.entities.Costumer;
import com.example.projetspringangularjwt.entities.CurrentAccount;
import com.example.projetspringangularjwt.entities.Operation;
import com.example.projetspringangularjwt.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CostumerDTO fromCostumer(Costumer costumer){
        CostumerDTO costumerDTO = new CostumerDTO();
        costumerDTO.setId(costumer.getId());
        costumerDTO.setName(costumer.getName());
        costumerDTO.setEmail(costumer.getEmail());
        return costumerDTO;
    }
    public Costumer fromCustomerDTO(CostumerDTO customerDTO){
        Costumer customer=new Costumer();
            customer.setEmail(customerDTO.getEmail());
            customer.setName(customerDTO.getName());
            customer.setId(customerDTO.getId());

        return  customer;
    }

    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){
        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCostumer(savingAccount.getCostumer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDTO;
    }

    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCostumer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
        return savingAccount;
    }

    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){
        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCostumer(currentAccount.getCostumer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;
    }

    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCostumer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }

    public OperationDTO fromAccountOperation(Operation accountOperation){
        OperationDTO accountOperationDTO=new OperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }
}
