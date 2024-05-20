package com.example.projetspringangularjwt;

import com.example.projetspringangularjwt.entities.*;
import com.example.projetspringangularjwt.repositories.*;
import com.example.projetspringangularjwt.services.BankAccountService;
import com.example.projetspringangularjwt.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication

public class ProjetSpringAngularJwtApplication {

    public static void main(String[] args) {



        // Generate the secret key
        SpringApplication.run(ProjetSpringAngularJwtApplication.class, args);
    }
    @Bean
    CommandLineRunner start(BankService bankAccountService){
        return args -> {
               };

    }
}
