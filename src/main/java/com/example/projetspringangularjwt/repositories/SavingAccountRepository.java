package com.example.projetspringangularjwt.repositories;

import com.example.projetspringangularjwt.entities.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccount,Long > {
}
