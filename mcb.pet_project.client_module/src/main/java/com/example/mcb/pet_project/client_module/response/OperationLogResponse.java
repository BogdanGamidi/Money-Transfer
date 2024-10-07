package com.example.mcb.pet_project.client_module.response;

import java.time.LocalDateTime;

public class OperationLogResponse {

    private String authId;
    private LocalDateTime dateOfOperation;
    private String description;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public LocalDateTime getDateOfOperation() {
        return dateOfOperation;
    }

    public void setDateOfOperation(LocalDateTime dateOfOperation) {
        this.dateOfOperation = dateOfOperation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}