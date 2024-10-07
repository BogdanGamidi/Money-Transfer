package com.example.mcb.pet_project.money_of_client_module.response;

import java.time.LocalDateTime;

public class OperationLogResponse {

    private LocalDateTime dateOfOperation;
    private String description;

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