package com.example.projetspringangularjwt.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Data
public class Costumer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
     @OneToMany(mappedBy = "costumer",cascade = CascadeType.ALL)
     @JsonBackReference
     private List<BankAccount> bankAccounts;
}
