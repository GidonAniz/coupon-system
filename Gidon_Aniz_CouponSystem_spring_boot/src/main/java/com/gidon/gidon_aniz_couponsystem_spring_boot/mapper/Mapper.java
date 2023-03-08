package com.gidon.gidon_aniz_couponsystem_spring_boot.mapper;

import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Company;
import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Coupon;
import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Customer;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CompanyDto;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CouponDto;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CustomerDto;


@org.mapstruct.Mapper
public interface Mapper {

    Company map(CompanyDto companyDto);

    Coupon map(CouponDto couponDto);

    CompanyDto map(Company company);

    CouponDto map(Coupon coupon);

    CustomerDto map(Customer customer);

    Customer map(CustomerDto customerDto);
}
