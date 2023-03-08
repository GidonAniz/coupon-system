package com.gidon.gidon_aniz_couponsystem_spring_boot.repository;

import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Company;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Modifying
    @Transactional
    @Query("delete from Company where uuid = :uuid")
    void deleteByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query("update Company set email = :email where uuid = :uuid")
    void updateEmailByUUid(UUID uuid, String email);

    @Modifying
    @Transactional
    @Query("update Company set password = :password where uuid = :uuid")
    void updatePasswordByUuid(UUID uuid, String password);

    @Transactional
    @Modifying
    @Query("update Company set name = :name where uuid = :uuid")
    void updateName(UUID uuid, String name);

    Optional<Company> findByEmail(String email);

    Optional<Company> findByUuid(UUID uuid);

    Optional<Company> findByEmailAndPassword(String email, String Password);

    @Override
    List<Company> findAll(Sort sort);

}
