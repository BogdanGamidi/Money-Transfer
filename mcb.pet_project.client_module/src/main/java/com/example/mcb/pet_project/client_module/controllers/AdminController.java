package com.example.mcb.pet_project.client_module.controllers;

import com.example.mcb.pet_project.client_module.response.ClientResponse;
import com.example.mcb.pet_project.client_module.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/admin")
    public String findAllClients(Model model) {
        List<ClientResponse> list = clientService.findAllClientsWithAccounts();
        model.addAttribute("accounts", list);
        return "clients-admin-account";
    }

    @GetMapping("/admin/{id}")
    public String getClientById(@PathVariable String id, Model model) {
        ClientResponse client = clientService.findClientWithAccountById(id);
        model.addAttribute("accounts", client);
        return "clients-admin-account";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteClient(@PathVariable String id, Model model) {
        clientService.deleteClient(id);
        return "redirect:/admin";
    }
}