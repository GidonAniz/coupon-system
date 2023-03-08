package com.gidon.gidon_aniz_couponsystem_spring_boot.mapper;

import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Company;
import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Coupon;
import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Customer;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CompanyDto;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CouponDto;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CustomerDto;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-04T00:28:53+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class MapperImpl implements Mapper {

    @Override
    public Company map(CompanyDto companyDto) {
        if ( companyDto == null ) {
            return null;
        }

        Company.CompanyBuilder company = Company.builder();

        company.uuid( companyDto.getUuid() );
        company.name( companyDto.getName() );
        company.email( companyDto.getEmail() );
        company.password( companyDto.getPassword() );
        company.coupons( couponDtoListToCouponSet( companyDto.getCoupons() ) );

        return company.build();
    }

    @Override
    public Coupon map(CouponDto couponDto) {
        if ( couponDto == null ) {
            return null;
        }

        Coupon.CouponBuilder coupon = Coupon.builder();

        coupon.uuid( couponDto.getUuid() );
        coupon.title( couponDto.getTitle() );
        coupon.category( couponDto.getCategory() );
        coupon.startDate( couponDto.getStartDate() );
        coupon.endDate( couponDto.getEndDate() );
        coupon.amount( couponDto.getAmount() );
        coupon.description( couponDto.getDescription() );
        coupon.price( couponDto.getPrice() );
        coupon.imageUrl( couponDto.getImageUrl() );

        return coupon.build();
    }

    @Override
    public CompanyDto map(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyDto.CompanyDtoBuilder companyDto = CompanyDto.builder();

        companyDto.uuid( company.getUuid() );
        companyDto.name( company.getName() );
        companyDto.email( company.getEmail() );
        companyDto.password( company.getPassword() );
        companyDto.coupons( couponSetToCouponDtoList( company.getCoupons() ) );

        return companyDto.build();
    }

    @Override
    public CouponDto map(Coupon coupon) {
        if ( coupon == null ) {
            return null;
        }

        CouponDto.CouponDtoBuilder couponDto = CouponDto.builder();

        couponDto.uuid( coupon.getUuid() );
        couponDto.title( coupon.getTitle() );
        couponDto.category( coupon.getCategory() );
        couponDto.startDate( coupon.getStartDate() );
        couponDto.endDate( coupon.getEndDate() );
        couponDto.amount( coupon.getAmount() );
        couponDto.description( coupon.getDescription() );
        couponDto.price( coupon.getPrice() );
        couponDto.imageUrl( coupon.getImageUrl() );

        return couponDto.build();
    }

    @Override
    public CustomerDto map(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDto.CustomerDtoBuilder customerDto = CustomerDto.builder();

        customerDto.uuid( customer.getUuid() );
        customerDto.firstName( customer.getFirstName() );
        customerDto.lastName( customer.getLastName() );
        customerDto.email( customer.getEmail() );
        customerDto.password( customer.getPassword() );
        customerDto.coupons( couponSetToCouponDtoList( customer.getCoupons() ) );

        return customerDto.build();
    }

    @Override
    public Customer map(CustomerDto customerDto) {
        if ( customerDto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.uuid( customerDto.getUuid() );
        customer.firstName( customerDto.getFirstName() );
        customer.lastName( customerDto.getLastName() );
        customer.email( customerDto.getEmail() );
        customer.password( customerDto.getPassword() );
        customer.coupons( couponDtoListToCouponSet( customerDto.getCoupons() ) );

        return customer.build();
    }

    protected Set<Coupon> couponDtoListToCouponSet(List<CouponDto> list) {
        if ( list == null ) {
            return null;
        }

        Set<Coupon> set = new LinkedHashSet<Coupon>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( CouponDto couponDto : list ) {
            set.add( map( couponDto ) );
        }

        return set;
    }

    protected List<CouponDto> couponSetToCouponDtoList(Set<Coupon> set) {
        if ( set == null ) {
            return null;
        }

        List<CouponDto> list = new ArrayList<CouponDto>( set.size() );
        for ( Coupon coupon : set ) {
            list.add( map( coupon ) );
        }

        return list;
    }
}
