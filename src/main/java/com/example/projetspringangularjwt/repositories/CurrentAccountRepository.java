package com.example.projetspringangularjwt.repositories;

import com.example.projetspringangularjwt.entities.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentAccountRepository extends JpaRepository<CurrentAccount,Long> {
}
