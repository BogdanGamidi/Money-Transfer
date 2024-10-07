package com.example.mcb.pet_project.client_module.repositories;

import com.example.mcb.pet_project.client_module.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    @Query(value = "FROM Client where id = :id")
    Client getClientById(String id);

    @Query(value = "FROM Client where login = :login")
    Client getClientsByLogin(String login);

    @Query(value = "FROM Client WHERE phoneNumber = :phoneNumber")
    Client getClientByPhone(String phoneNumber);
}