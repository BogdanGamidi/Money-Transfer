package com.example.mcb.pet_project.client_module.controllers;

import com.example.mcb.pet_project.client_module.dao.ClientDao;
import com.example.mcb.pet_project.client_module.dto.ClientDto;
import com.example.mcb.pet_project.client_module.entities.Client;
import com.example.mcb.pet_project.client_module.kafka.KafkaProducer;
import com.example.mcb.pet_project.client_module.response.ClientResponse;
import com.example.mcb.pet_project.client_module.response.LogResponse;
import com.example.mcb.pet_project.client_module.response.TransferResponse;
import com.example.mcb.pet_project.client_module.response.UserInfoResponse;
import com.example.mcb.pet_project.client_module.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final ClientService clientService;
    private final KafkaProducer kafkaProducer;

    public UserController(@Autowired ClientService clientService, @Autowired KafkaProducer kafkaProducer) {
        this.clientService = clientService;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/user/{id}")
    public String getClientDtoById(@PathVariable String id, Model model) {
        UserInfoResponse userInfoResponse = clientService.findUserInfo(id);
        model.addAttribute("userInfo", userInfoResponse);
        return "user-info";
    }

    @GetMapping("/user/getinfo/{authid}")
    public String getOperationInfo(@PathVariable String authid, Model model) {
        LogResponse list = clientService.allLogs(authid);
        model.addAttribute("operationLog", list);
        return "operation-log";
    }

    @GetMapping("/registration")
    public String registrationClient(Model model) {
        model.addAttribute("client", new Client());
        return "registration";
    }

    @PostMapping("/createClient")
    public String createClient(Client client) {
        clientService.createClient(client);
        return "redirect:/user";
    }

    @GetMapping("/user/update/{authid}/{id}")
    public String updateClient(@PathVariable String authid, @PathVariable String id, Model model) {
        Client client = clientService.getClientById(id);
        ClientDto clientDto = new ClientDto(
                authid,
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getPhoneNumber()
        );
        model.addAttribute("client", clientDto);
        return "edit";
    }

    @PostMapping("/user/updateClient")
    public String updateClient(String id, ClientDto clientDto) {
        ClientDao clientDao = new ClientDao();
        clientDao.firstName = clientDto.getFirstName();
        clientDao.lastName = clientDto.getLastName();
        clientService.updateClient(id, clientDao);
        return "redirect:/user/" + clientDto.getAuthId();
    }

    @GetMapping("/user/transferMoney/{authid}/{recipientid}")
    public String updateAccountClient(@PathVariable String authid, @PathVariable String recipientid, Model model) {
        Client authorClient = clientService.getClientById(authid);
        Client recipientClient = clientService.getClientById(recipientid);

        ClientResponse info = clientService.findClientWithAccountById(authid);

        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setAuthId(authid);
        transferResponse.setAuthAccount(info.getPersonalAccount());
        transferResponse.setAuthAmount(info.getAmountOfMoney());
        transferResponse.setClientSenderPhone(clientService.getClientById(authid).getPhoneNumber());
        transferResponse.setClientRecipientPhone(recipientClient.getPhoneNumber());

        model.addAttribute("account", transferResponse);
        return "transfer";
    }

    @PostMapping("/transfer")
    public String updateAccountClient(TransferResponse transferResponse) {
        Boolean result = clientService.updateAccountClient(transferResponse);
        if (result) {
            kafkaProducer.sendInfo(transferResponse);
        }
        return "redirect:/user/" + transferResponse.getAuthId();
    }
}