package com.example.mcb.pet_project.money_of_client_module.repositories;

import com.example.mcb.pet_project.money_of_client_module.dto.ClientInfoDto;
import com.example.mcb.pet_project.money_of_client_module.entities.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {

    @Query(nativeQuery = true, value = "SELECT log.id, log.client_sender_id, log.client_recipient_id, log.date_of_operation, log.description FROM operation_log log JOIN client ON client.id = log.client_recipient_id WHERE log.client_recipient_id = :authid ORDER BY log.date_of_operation desc")
    List<OperationLog> getAllByClientRecipientId(@Param("authid") String authid);

    @Query(nativeQuery = true, value = "SELECT client.first_name, client.last_name FROM client WHERE client.phone_number = :senderPhone")
    ClientInfoDto getInfo(String senderPhone);
}