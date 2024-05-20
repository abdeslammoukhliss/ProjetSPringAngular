package com.example.projetspringangularjwt.dtos;

import com.example.projetspringangularjwt.entities.AccountStatus;
import com.example.projetspringangularjwt.mappers.CostumerDTO;
import lombok.Data;

import java.util.Date;
@Data
public class SavingBankAccountDTO extends BankAccountDTO{
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CostumerDTO customerDTO;
    private double interestRate;}
