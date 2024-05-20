package com.example.projetspringangularjwt.dtos;


import com.example.projetspringangularjwt.entities.OperationType;
import lombok.Data;

import java.util.Date;

@Data
public class OperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}