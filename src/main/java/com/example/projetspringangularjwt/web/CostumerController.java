package com.example.projetspringangularjwt.web;

import com.example.projetspringangularjwt.entities.Costumer;
import com.example.projetspringangularjwt.mappers.CostumerDTO;
import com.example.projetspringangularjwt.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CostumerController {
    private BankAccountService bankAccountService;
    @GetMapping("/costumers")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CostumerDTO> listCostumers(){
        return bankAccountService.listCostumers();
    }

    @GetMapping("/costumers/search")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<CostumerDTO> listCostumers(@RequestParam(name="keyword",defaultValue = "") String keyword){
        return bankAccountService.listCostumersByKeyword(keyword);
    }

    @GetMapping("/costumers/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public CostumerDTO getCostumer(@PathVariable String id){
        return bankAccountService.getCostumer(Long.parseLong(id));
    }
    @PostMapping("/costumers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CostumerDTO saveCostumer(@RequestBody CostumerDTO costumer){
        return bankAccountService.saveCostumer(costumer);
    }
    @PutMapping("/costumers/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    CostumerDTO updatedCostumer(@PathVariable Long id ,@RequestBody CostumerDTO costumer){
        costumer.setId(id);
        return bankAccountService.updateCostumer(costumer);
    }
    @DeleteMapping("/costumers/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteCostumer(@PathVariable Long id){
        bankAccountService.deleteCostumer(id);
    }
}
