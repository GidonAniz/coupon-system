package com.gidon.gidon_aniz_couponsystem_spring_boot.repository;

import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Transactional
    @Modifying
    @Query("delete from Customer where uuid = :uuid")
    void deleteByUuid(UUID uuid);

    @Transactional
    @Modifying
    @Query("update Customer set email = :email where uuid = :uuid")
    void updateEmail(UUID uuid, String email);

    @Transactional
    @Modifying
    @Query("update Customer set firstName = :firstName where uuid = :uuid")
    void updateFirstName(UUID uuid, String firstName);

    @Transactional
    @Modifying
    @Query("update Customer set lastName = :lastName where uuid = :uuid")
    void updateLastName(UUID uuid, String lastName);

    @Transactional
    @Modifying
    @Query("update Customer set password = :password where uuid = :uuid")
    void updatePassword(UUID uuid, String password);

    Optional<Customer> findByUuid(UUID uuid);

    Optional<Customer> findByEmail(String email);

    @Override
    List<Customer> findAll();

    Optional<Customer> findByEmailAndPassword(String email, String password);

}
