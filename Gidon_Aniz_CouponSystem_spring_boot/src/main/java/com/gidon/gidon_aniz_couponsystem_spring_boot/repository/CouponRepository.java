package com.gidon.gidon_aniz_couponsystem_spring_boot.repository;

import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Coupon;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;



import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Modifying
    @Transactional
    @Query("delete from Coupon where uuid = :uuid")
    void deleteByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query("delete from Coupon where endDate < CURRENT_TIMESTAMP")
    int deleteTheExpiredCoupons();

    @Modifying
    @Transactional
    @Query("update Coupon set amount =:amount where uuid = :uuid")
    void updateAmountByUuid(int amount, UUID uuid);

    @Override
    List<Coupon> findAll(Sort sort);

    Set<Coupon> findAllByCompany_Uuid(UUID uuid);


    Optional<Coupon> findByUuid(UUID uuid);

    Optional<Coupon> findCouponByTitleAndCompany_Uuid(String title, UUID uuid);

    Set<Coupon> findByCustomer_Uuid(UUID uuid);

    Set<Coupon> findByCustomer_UuidAndEndDateIsBetween(UUID uuid, Timestamp now, Timestamp weekFromNow);

}
