package com.example.mcb.pet_project.money_of_client_module.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "operation_log")
public class OperationLog {

    @Id
    @SequenceGenerator(name = "operation_log_seq", sequenceName = "operation_log_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_log_seq")
    private Long id;

    @Column(name = "client_sender_id")
    private String clientSenderId;

    @Column(name = "client_recipient_id")
    private String clientRecipientId;

    @CreatedDate
    @Column(name = "date_of_operation")
    private LocalDateTime dateOfOperation = LocalDateTime.now();

    @Column(name = "description")
    private String description;

    public OperationLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientSenderId() {
        return clientSenderId;
    }

    public void setClientSenderId(String clientSenderId) {
        this.clientSenderId = clientSenderId;
    }

    public String getClientRecipientId() {
        return clientRecipientId;
    }

    public void setClientRecipientId(String clientRecipientId) {
        this.clientRecipientId = clientRecipientId;
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