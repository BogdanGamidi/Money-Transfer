package com.example.mcb.pet_project.money_of_client_module.entities;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "money_of_client")
public class MoneyOfClient {

    @Id
    @SequenceGenerator(name = "money_of_client_seq", sequenceName = "money_of_client_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "money_of_client_seq")
    private Long id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "personal_account")
    private String personalAccount;

    @Column(name = "amount_of_money")
    private BigDecimal amountOfMoney;

    public MoneyOfClient() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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