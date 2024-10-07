package com.example.mcb.pet_project.client_module.response;

import com.example.mcb.pet_project.client_module.entities.Client;

import java.math.BigDecimal;
import java.util.List;

public class UserInfoResponse {

    private String id;
    private String name;
    private String personalAccount;
    private BigDecimal amountOfMoney;

    private List<Client> listOfClients;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(String personalAccount) {
        this.personalAccount = personalAccount;
    }

    public BigDecimal getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(BigDecimal amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public List<Client> getListOfClients() {
        return listOfClients;
    }

    public void setListOfClients(List<Client> listOfClients) {
        this.listOfClients = listOfClients;
    }
}