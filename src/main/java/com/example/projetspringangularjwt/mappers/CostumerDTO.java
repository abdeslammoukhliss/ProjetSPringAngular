package com.example.projetspringangularjwt.mappers;

import com.example.projetspringangularjwt.entities.BankAccount;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CostumerDTO {
    private Long id;
    private String name;
    private String email;
}
