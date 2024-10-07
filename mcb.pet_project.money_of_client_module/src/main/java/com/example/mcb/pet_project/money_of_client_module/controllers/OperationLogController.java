package com.example.mcb.pet_project.money_of_client_module.controllers;

import com.example.mcb.pet_project.money_of_client_module.response.OperationLogResponse;
import com.example.mcb.pet_project.money_of_client_module.services.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OperationLogController {

    private final OperationLogService operationLogService;

    public OperationLogController(@Autowired OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @PostMapping("/user/getinfo/{authid}")
    public ResponseEntity<List<OperationLogResponse>> getAllLogs(@PathVariable("authid") String authid) {
        List<OperationLogResponse> list = operationLogService.getAllOperation(authid);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}