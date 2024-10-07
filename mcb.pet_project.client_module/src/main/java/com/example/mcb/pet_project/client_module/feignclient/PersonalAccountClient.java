package com.example.mcb.pet_project.client_module.feignclient;

import com.example.mcb.pet_project.client_module.response.MoneyOfClientResponse;
import com.example.mcb.pet_project.client_module.response.OperationLogResponse;
import com.example.mcb.pet_project.client_module.response.TransferResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "clients", url = "http://localhost:8081", path = "/clients")
public interface PersonalAccountClient {

    @PostMapping("/admin")
    ResponseEntity<List<MoneyOfClientResponse>> getAllAccounts();

    @PostMapping("/admin/{id}")
    ResponseEntity<MoneyOfClientResponse> getMoneyOfClientById(@PathVariable("id") String id);

    @PostMapping("/user/getinfo/{authid}")
    ResponseEntity<List<OperationLogResponse>> getAllLogs(@PathVariable("authid") String authid);

    @PostMapping("/account/registration")
    void createAccount(@RequestBody MoneyOfClientResponse moneyOfClientResponse);

    @PostMapping("/transfer")
    ResponseEntity<Boolean> updateAccount(@RequestBody TransferResponse transferResponse);
}