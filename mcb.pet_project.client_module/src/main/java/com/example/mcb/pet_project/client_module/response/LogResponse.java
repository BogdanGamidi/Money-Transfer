package com.example.mcb.pet_project.client_module.response;

import java.util.List;

public class LogResponse {

    private String authId;
    private List<OperationLogResponse> operationLogResponses;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public List<OperationLogResponse> getOperationLogResponses() {
        return operationLogResponses;
    }

    public void setOperationLogResponses(List<OperationLogResponse> operationLogResponses) {
        this.operationLogResponses = operationLogResponses;
    }
}