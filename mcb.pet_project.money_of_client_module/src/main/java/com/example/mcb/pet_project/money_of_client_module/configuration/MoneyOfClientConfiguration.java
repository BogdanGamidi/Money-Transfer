package com.example.mcb.pet_project.money_of_client_module.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MoneyOfClientConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}