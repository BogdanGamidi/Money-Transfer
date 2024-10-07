package com.example.mcb.pet_project.client_module.kafka;

import com.example.mcb.pet_project.client_module.response.TransferResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ModelMapper modelMapper;

    public KafkaProducer(@Autowired KafkaTemplate<String, Object> kafkaTemplate, @Autowired ModelMapper modelMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.modelMapper = modelMapper;
    }

    public void sendInfo(TransferResponse transferResponse) {
        kafkaTemplate.send("transfer_money", modelMapper.map(transferResponse, TransferResponse.class));
    }
}