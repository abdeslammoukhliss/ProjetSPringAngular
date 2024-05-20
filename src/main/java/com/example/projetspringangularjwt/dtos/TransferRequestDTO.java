package com.example.projetspringangularjwt.dtos;

import lombok.Data;


@Data
public class TransferRequestDTO {
    private String accountSource;
    private String accountDestination;
    private double amount;
    private String description;
}