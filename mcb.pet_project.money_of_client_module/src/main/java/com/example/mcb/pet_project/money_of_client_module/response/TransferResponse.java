package com.example.mcb.pet_project.money_of_client_module.response;

import java.math.BigDecimal;

public class TransferResponse {

    private String authId;
    private String clientSenderPhone;
    private String clientRecipientPhone;
    private BigDecimal transfer;

    public String getClientSenderPhone() {
        return clientSenderPhone;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
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