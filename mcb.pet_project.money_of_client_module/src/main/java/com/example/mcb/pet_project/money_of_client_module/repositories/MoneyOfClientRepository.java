package com.example.mcb.pet_project.money_of_client_module.repositories;

import com.example.mcb.pet_project.money_of_client_module.entities.MoneyOfClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MoneyOfClientRepository extends JpaRepository<MoneyOfClient, Long> {

    @Query(nativeQuery = true, value = "SELECT money.id, money.client_id, money.personal_account, money.amount_of_money FROM money_of_client money JOIN client ON client.id = money.client_id")
    List<MoneyOfClient> getAllMoneyOfClient();

    @Query(nativeQuery = true, value = "SELECT money.id, money.client_id, money.personal_account, money.amount_of_money FROM money_of_client money JOIN client ON client.id = money.client_id WHERE money.client_id =:id")
    MoneyOfClient getMoneyOfClientById(@Param("id") String id);

    @Query(nativeQuery = true, value = "SELECT money.id, money.client_id, money.personal_account, money.amount_of_money FROM money_of_client money JOIN client ON client.id = money.client_id WHERE client.phone_number =:phoneNumber")
    MoneyOfClient getMoneyOfClientByPhone(@Param("phoneNumber") String phoneNumber);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE money_of_client SET amount_of_money = :amount WHERE client_id = :id")
    void updateClientAccount(@Param("id") String id, @Param("amount") BigDecimal amount);
}