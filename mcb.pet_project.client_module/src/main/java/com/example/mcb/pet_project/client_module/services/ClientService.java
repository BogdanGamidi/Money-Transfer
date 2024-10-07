package com.example.mcb.pet_project.client_module.services;

import com.example.mcb.pet_project.client_module.dao.ClientDao;
import com.example.mcb.pet_project.client_module.entities.Client;
import com.example.mcb.pet_project.client_module.feignclient.PersonalAccountClient;
import com.example.mcb.pet_project.client_module.repositories.ClientRepository;
import com.example.mcb.pet_project.client_module.response.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ClientService {

    private Logger logger = LogManager.getLogger(getClass());
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PersonalAccountClient personalAccountClient;

    public ClientRepository getRepository() {
        return clientRepository;
    }

    //admin
    public List<ClientResponse> findAllClientsWithAccounts() {
        List<Client> clients = clientRepository.findAll();
        List<ClientResponse> clientsResponse = new ArrayList<>();

        ResponseEntity<List<MoneyOfClientResponse>> accounts = personalAccountClient.getAllAccounts();
        for (MoneyOfClientResponse account : Objects.requireNonNull(accounts.getBody())) {
            for (Client client : clients) {
                if (client.getId().equals(account.getClientId())) {
                    ClientResponse clientResponse = modelMapper.map(client, ClientResponse.class);
                    clientResponse.setPersonalAccount(account.getPersonalAccount());
                    clientResponse.setAmountOfMoney(account.getAmountOfMoney());
                    clientsResponse.add(clientResponse);
                }
            }
        }
        return clientsResponse;
    }

    //admin
    public ClientResponse findClientWithAccountById(String id) {
        Optional<Client> client = Optional.ofNullable(clientRepository.getClientById(id));
        ClientResponse clientResponse = null;
        if (client.isPresent()) {
            clientResponse = modelMapper.map(client, ClientResponse.class);

            ResponseEntity<MoneyOfClientResponse> moneyOfClientResponse = personalAccountClient.getMoneyOfClientById(id);
            clientResponse.setPersonalAccount(moneyOfClientResponse.getBody().getPersonalAccount());
            clientResponse.setAmountOfMoney(moneyOfClientResponse.getBody().getAmountOfMoney());
        } else {
            logger.error("Client {} not found!", id);
        }
        return clientResponse;
    }

    //user
    public Client getClientById(String id) {
        Optional<Client> client = Optional.ofNullable(clientRepository.getClientById(id));
        if (!client.isPresent()) {
            logger.error("Client {} not found!", id);
        }
        return client.get();
    }

    //user
    public UserInfoResponse findUserInfo(String id) {
        Client client = clientRepository.getClientById(id);
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setId(client.getId());
        userInfoResponse.setName(client.getFirstName() + " " + client.getLastName());

        ResponseEntity<MoneyOfClientResponse> moneyOfClientResponse = personalAccountClient.getMoneyOfClientById(id);
        userInfoResponse.setPersonalAccount(moneyOfClientResponse.getBody().getPersonalAccount());
        userInfoResponse.setAmountOfMoney(moneyOfClientResponse.getBody().getAmountOfMoney());

        List<Client> clients = clientRepository.findAll();
        List<Client> listForInfo = new ArrayList<>();

        for (Client client1 : clients) {
            if (!client1.getId().equals(client.getId())) {
                listForInfo.add(client1);
            }
        }
        userInfoResponse.setListOfClients(listForInfo);
        return userInfoResponse;
    }

    public LogResponse allLogs(String id) {
        ResponseEntity<List<OperationLogResponse>> result = personalAccountClient.getAllLogs(id);
        LogResponse logResponse = new LogResponse();
        List<OperationLogResponse> lists = new ArrayList<>();

        for (OperationLogResponse operationLogResponse : Objects.requireNonNull(result.getBody())) {
            OperationLogResponse log = new OperationLogResponse();
            log.setDateOfOperation(operationLogResponse.getDateOfOperation());
            log.setDescription(operationLogResponse.getDescription());
            lists.add(log);
        }
        for(OperationLogResponse operationLogResponse : result.getBody()) {
            System.out.println(operationLogResponse.getDescription());
        }
        logResponse.setAuthId(id);
        logResponse.setOperationLogResponses(lists);
        return logResponse;
    }

    public Client createClient(Client client) {
        Client clientSave = new Client();
        clientSave.setFirstName(client.getFirstName());
        clientSave.setLastName(client.getLastName());
        clientSave.setPhoneNumber(client.getPhoneNumber());
        clientSave.setLogin(client.getLogin());
        clientSave.setPassword(passwordEncoder.encode(client.getPassword()));
        if (client.getRole() == null) clientSave.setRole("USER");
        else clientSave.setRole(client.getRole());
        clientRepository.save(clientSave);
        personalAccountClient.createAccount(createAccountClient(client));
        return clientSave;
    }

    public MoneyOfClientResponse createAccountClient(Client client) {
        Client clientId = clientRepository.getClientsByLogin(client.getLogin());
        MoneyOfClientResponse moneyOfClientResponse = new MoneyOfClientResponse();
        moneyOfClientResponse.setClientId(clientId.getId());
        moneyOfClientResponse.setPersonalAccount(getRandom());
        moneyOfClientResponse.setAmountOfMoney(BigDecimal.valueOf(0));
        return moneyOfClientResponse;
    }

    private String getRandom() {
        Random random = new Random();
        Long value = (long) (Math.random() * (999999999 - 100000000) + 100000000);
        String result = value.toString();
        return result;
    }

    public Client updateClient(String id, ClientDao clientDao) {
        Optional<Client> client = Optional.ofNullable(clientRepository.getClientById(id));
        if (client.isPresent()) {
            if (clientDao.firstName != null) client.get().setFirstName(clientDao.firstName);
            if (clientDao.lastName != null) client.get().setLastName(clientDao.lastName);
        } else {
            logger.error("Client {} not found!", id);
        }
        return clientRepository.save(client.get());
    }

    public Boolean updateAccountClient(TransferResponse transferResponse) {

        Optional<Client> clientSender = Optional.ofNullable(clientRepository.getClientById(transferResponse.getAuthId()));
        Optional<Client> clientRecipient = Optional.ofNullable(clientRepository.getClientByPhone(transferResponse.getClientRecipientPhone()));

        boolean result = false;

        if (clientSender.isPresent() && clientRecipient.isPresent()) {
            ResponseEntity<Boolean> res = personalAccountClient.updateAccount(transferResponse);
            return res.getBody();
        } else {
            if (clientSender.isEmpty()) {
                logger.error("Client whose phone number is {} not found!", transferResponse.getClientSenderPhone());
            }
            if (clientRecipient.isEmpty()) {
                logger.error("Client whose phone number is {} not found!", transferResponse.getClientRecipientPhone());
            }
        }
        return result;
    }

    public void deleteClient(String id) {
        getRepository().delete(clientRepository.getClientById(id));
    }
}