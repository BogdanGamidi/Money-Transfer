package com.example.mcb.pet_project.money_of_client_module.controllers;

import com.example.mcb.pet_project.money_of_client_module.entities.MoneyOfClient;
import com.example.mcb.pet_project.money_of_client_module.response.MoneyOfClientResponse;
import com.example.mcb.pet_project.money_of_client_module.response.TransferResponse;
import com.example.mcb.pet_project.money_of_client_module.services.MoneyOfClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class MoneyOfClientController {

    private final MoneyOfClientService moneyOfClientService;

    public MoneyOfClientController(@Autowired MoneyOfClientService moneyOfClientService) {
        this.moneyOfClientService = moneyOfClientService;
    }

    @PostMapping("/admin")
    public ResponseEntity<List<MoneyOfClientResponse>> getAllAccounts() {
        List<MoneyOfClientResponse> moneyOfClientResponses = moneyOfClientService.getAllAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(moneyOfClientResponses);
    }

    @PostMapping("/admin/{id}")
    public ResponseEntity<MoneyOfClientResponse> getMoneyOfClientById(@PathVariable("id") String id) {
        Optional<MoneyOfClientResponse> moneyOfClientResponse = Optional.ofNullable(moneyOfClientService.getMoneyOfClientById(id));
        if (moneyOfClientResponse.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(moneyOfClientResponse.get());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/account/registration")
    public ResponseEntity<String> createAccount(@RequestBody MoneyOfClient moneyOfClientResponse) {
        moneyOfClientService.createAccount(moneyOfClientResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account for client " + moneyOfClientResponse.getClientId() + " created!");
    }

    @PostMapping("/transfer")
    public ResponseEntity<Boolean> updateAccount(@RequestBody TransferResponse transferResponse) {
        Boolean result = moneyOfClientService.updateAccount(transferResponse);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}