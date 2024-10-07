package com.example.mcb.pet_project.client_module.response;

import java.math.BigDecimal;

public class ClientResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String login;
    private String password;
    private String role;
    private String personalAccount;
    private BigDecimal amountOfMoney;

    private MoneyOfClientResponse moneyOfClientResponse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public MoneyOfClientResponse getMoneyOfClientResponse() {
        return moneyOfClientResponse;
    }

    public void setMoneyOfClientResponse(MoneyOfClientResponse moneyOfClientResponse) {
        this.moneyOfClientResponse = moneyOfClientResponse;
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
}