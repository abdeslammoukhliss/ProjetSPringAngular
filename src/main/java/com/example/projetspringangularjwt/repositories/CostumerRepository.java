package com.example.projetspringangularjwt.repositories;

import com.example.projetspringangularjwt.entities.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostumerRepository extends JpaRepository<Costumer,Long> {
    @Query("SELECT c FROM Costumer c WHERE c.name LIKE %:name%")
    List<Costumer> searchCostumer(@Param("name") String name);
}
