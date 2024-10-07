package com.example.mcb.pet_project.money_of_client_module.kafka;

import com.example.mcb.pet_project.money_of_client_module.entities.MoneyOfClient;
import com.example.mcb.pet_project.money_of_client_module.response.TransferResponse;
import com.example.mcb.pet_project.money_of_client_module.services.MoneyOfClientService;
import com.example.mcb.pet_project.money_of_client_module.services.OperationLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final Logger log = LogManager.getLogger(getClass());
    @Autowired
    private MoneyOfClientService moneyOfClientService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private ModelMapper modelMapper;

    @KafkaListener(topics = "transfer_money", groupId = "transfer_consumer", properties = {"spring.json.value.default.type=com.example.mcb.pet_project.money_of_client_module.response.TransferResponse"})
    public void listen(TransferResponse transferResponse) {

        MoneyOfClient senderClient =  moneyOfClientService.getMoneyOfClientRepository().getMoneyOfClientById(transferResponse.getAuthId());
        MoneyOfClient recipientClient = moneyOfClientService.getMoneyOfClientRepository().getMoneyOfClientByPhone(transferResponse.getClientRecipientPhone());
        log.info("Client {} transfer {} rub to client {}", senderClient.getClientId(), transferResponse.getTransfer(), recipientClient.getClientId());

        operationLogService.insertInfoLog(modelMapper.map(transferResponse, TransferResponse.class));
    }
}