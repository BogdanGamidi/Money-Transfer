package com.example.mcb.pet_project.money_of_client_module.services;

import com.example.mcb.pet_project.money_of_client_module.entities.MoneyOfClient;
import com.example.mcb.pet_project.money_of_client_module.entities.OperationLog;
import com.example.mcb.pet_project.money_of_client_module.repositories.MoneyOfClientRepository;
import com.example.mcb.pet_project.money_of_client_module.repositories.OperationLogRepository;
import com.example.mcb.pet_project.money_of_client_module.response.OperationLogResponse;
import com.example.mcb.pet_project.money_of_client_module.response.TransferResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationLogService {

    private final Logger logger = LogManager.getLogger(getClass());
    @Autowired
    private OperationLogRepository operationLogRepository;
    @Autowired
    private MoneyOfClientRepository moneyOfClientRepository;
    @Autowired
    private ModelMapper modelMapper;

    public OperationLogRepository getRepository() {
        return operationLogRepository;
    }

    //admin
    public List<OperationLog> getAll() {
        return operationLogRepository.findAll();
    }

    public List<OperationLogResponse> getAllOperation(String authid) {

        List<OperationLog> logs = operationLogRepository.getAllByClientRecipientId(authid);
        List<OperationLogResponse> result = new ArrayList<>();

        try {
            for (OperationLog log : logs) {
                OperationLogResponse operationLogResponse = new OperationLogResponse();
                operationLogResponse.setDateOfOperation(log.getDateOfOperation());
                operationLogResponse.setDescription(log.getDescription());
                OperationLogResponse toLogs = modelMapper.map(operationLogResponse, OperationLogResponse.class);
                result.add(toLogs);
            }
            logger.info("Log was sent");
        } catch (Exception e) {
            logger.error("Log is empty");
        }

        return result;
    }

    public void insertInfoLog(TransferResponse transferResponse) {
        MoneyOfClient clientSender = moneyOfClientRepository.getMoneyOfClientById(transferResponse.getAuthId());
        OperationLog log = new OperationLog();
        log.setClientSenderId(moneyOfClientRepository.getMoneyOfClientById(transferResponse.getAuthId()).getClientId());
        log.setClientRecipientId(moneyOfClientRepository.getMoneyOfClientByPhone(transferResponse.getClientRecipientPhone()).getClientId());
        log.setDescription("Client " + clientSender.getClientId() + " transfer " + transferResponse.getTransfer() + " rub");
        operationLogRepository.save(log);
    }
}