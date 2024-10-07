package com.example.mcb.pet_project.client_module.response;

import java.math.BigDecimal;

public class TransferResponse {

    private String authId;
    private String recipientId;
    private String authAccount;
    private BigDecimal authAmount;
    private String clientSenderPhone;
    private String clientRecipientPhone;
    private BigDecimal transfer;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getAuthAccount() {
        return authAccount;
    }

    public void setAuthAccount(String authAccount) {
        this.authAccount = authAccount;
    }

    public BigDecimal getAuthAmount() {
        return authAmount;
    }

    public void setAuthAmount(BigDecimal authAmount) {
        this.authAmount = authAmount;
    }

    public String getClientSenderPhone() {
        return clientSenderPhone;
    }

    public void setClientSenderPhone(String clientSenderPhone) {
        this.clientSenderPhone = clientSenderPhone;
    }

    public String getClientRecipientPhone() {
        return clientRecipientPhone;
    }

    public void setClientRecipientPhone(String clientRecipientPhone) {
        this.clientRecipientPhone = clientRecipientPhone;
    }

    public BigDecimal getTransfer() {
        return transfer;
    }

    public void setTransfer(BigDecimal transfer) {
        this.transfer = transfer;
    }
}