package com.example.projetspringangularjwt.repositories;

import com.example.projetspringangularjwt.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation,Long>{
    List<Operation> findByBankAccount_Uuid(String accountId);
    Page<Operation> findByBankAccount_UuidOrderByDateDesc(String accountId, Pageable pageable);
}
