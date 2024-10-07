package com.example.mcb.pet_project.money_of_client_module.services;

import com.example.mcb.pet_project.money_of_client_module.entities.MoneyOfClient;
import com.example.mcb.pet_project.money_of_client_module.repositories.MoneyOfClientRepository;
import com.example.mcb.pet_project.money_of_client_module.response.MoneyOfClientResponse;
import com.example.mcb.pet_project.money_of_client_module.response.TransferResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoneyOfClientService {

    private final Logger logger = LogManager.getLogger(getClass());
    private final MoneyOfClientRepository moneyOfClientRepository;
    private final ModelMapper modelMapper;

    public MoneyOfClientService(@Autowired MoneyOfClientRepository moneyOfClientRepository, @Autowired ModelMapper modelMapper) {
        this.moneyOfClientRepository = moneyOfClientRepository;
        this.modelMapper = modelMapper;
    }

    public MoneyOfClientRepository getMoneyOfClientRepository() {
        return moneyOfClientRepository;
    }

    public List<MoneyOfClientResponse> getAllAccounts() {
        List<MoneyOfClient> moneyOfClients = moneyOfClientRepository.getAllMoneyOfClient();
        List<MoneyOfClientResponse> moneyOfClientResponses = new ArrayList<>();

        for (MoneyOfClient client : moneyOfClients) {
            MoneyOfClientResponse moneyOfClientResponse = modelMapper.map(client, MoneyOfClientResponse.class);
            moneyOfClientResponses.add(moneyOfClientResponse);
        }
        return moneyOfClientResponses;
    }

    public MoneyOfClientResponse getMoneyOfClientById(String id) {
        MoneyOfClient moneyOfClient = null;
        MoneyOfClientResponse moneyOfClientResponse = null;
        try {
            moneyOfClient = moneyOfClientRepository.getMoneyOfClientById(id);
            moneyOfClientResponse = modelMapper.map(moneyOfClient, MoneyOfClientResponse.class);
        } catch (Exception e) {
            logger.error("Client {} not found!", id);
        }
        return moneyOfClientResponse;
    }

    public void createAccount(MoneyOfClient moneyOfClientResponse) {
        MoneyOfClient moneyOfClient = new MoneyOfClient();
        try {
            moneyOfClient.setClientId(moneyOfClientResponse.getClientId());
            moneyOfClient.setPersonalAccount(moneyOfClientResponse.getPersonalAccount());
            moneyOfClient.setAmountOfMoney(moneyOfClientResponse.getAmountOfMoney());
        } catch (Exception e) {
            logger.error("Client {} not found!", moneyOfClientResponse.getClientId());
        }
        moneyOfClientRepository.save(moneyOfClient);
    }

    @Transactional
    public Boolean updateAccount(TransferResponse transferResponse) {
        logger.info("Transaction is start");

        MoneyOfClient clientSender = null;
        MoneyOfClient clientRecipient = null;
        try {
            clientSender = moneyOfClientRepository.getMoneyOfClientById(transferResponse.getAuthId());
            BigDecimal senderMoney = clientSender.getAmountOfMoney().subtract(transferResponse.getTransfer());
            BigDecimal zero = BigDecimal.ZERO;

            clientRecipient = moneyOfClientRepository.getMoneyOfClientByPhone(transferResponse.getClientRecipientPhone());
            BigDecimal recipientMoney = clientRecipient.getAmountOfMoney().add(transferResponse.getTransfer());

            if (senderMoney.compareTo(zero) >= 0) {
                moneyOfClientRepository.updateClientAccount(clientSender.getClientId(), senderMoney);
                moneyOfClientRepository.updateClientAccount(clientRecipient.getClientId(), recipientMoney);
                logger.info("Transaction is end");
                return modelMapper.map(true, Boolean.class);
            } else {
                logger.info("Client {} doesn't have insufficient funds in the account", clientSender.getClientId());
            }
        } catch (Exception e) {
            if(clientSender == null) {
                logger.error("Client whose phone number is {} not found!", transferResponse.getClientSenderPhone());
            }
            if(clientRecipient == null) {
                logger.error("Client whose phone number is {} not found!", transferResponse.getClientRecipientPhone());
            }
        }

        logger.info("Transaction is end");
        return modelMapper.map(false, Boolean.class);
    }
}