package com.gidon.gidon_aniz_couponsystem_spring_boot.service;


import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CompanyDto;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CouponDto;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CustomerDto;



import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppService {


    Optional<CompanyDto> insertCompany(CompanyDto companyDto);

    void deleteCompanyByUuid(UUID uuid);

    void updateCompanyNameByUuid(UUID uuid, String name);
    void updateCompanyEmailByUUid(UUID uuid, String email);

    void updateCompanyPasswordByUuid(UUID uuid, String password);

    Optional<CompanyDto> findCompanyByUuid(UUID uuid);

    List<CompanyDto> findAllCompanies();

    Optional<CouponDto> createCoupon(CouponDto couponDto, UUID companyUuid);

    void deleteCouponByUuid(UUID uuid);

    int deleteAllExpiredCoupons();

    List<CouponDto> findAllCoupons();

    Optional<CouponDto> findCouponByUuid(UUID uuid);

    Optional<CustomerDto> insertCustomer(CustomerDto customerDto);

    void deleteCustomerByUuid(UUID uuid);

    void updateCustomerEmailByUuid(UUID uuid, String email);

    void updateCustomerPasswordByUuid(UUID uuid, String password);

    void updateCustomerFirstNameByUuid(UUID uuid, String firstName);

    void updateCustomerLastNameByUuid(UUID uuid, String lastName);

    Optional<CustomerDto> findCustomerByUuid(UUID uuid);

    List<CustomerDto> findAllCustomers();

    void purchase(UUID couponUuid, UUID customerUuid);

    List<CouponDto> findAllCouponsByCustomerUuid(UUID uuid);

    List<CouponDto> findAllCouponsByCompanyUuid(UUID uuid);

    List<CouponDto> findAllCouponsByCustomerExpiredInOneWeek(UUID uuid);

    void updateCouponAmountByUuid(int amount, UUID uuid);

}
