package com.example.projetspringangularjwt.repositories;

import com.example.projetspringangularjwt.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,String>{
    List<BankAccount> findBankAccountsByCostumer_Id(Long costumer_id);
}
