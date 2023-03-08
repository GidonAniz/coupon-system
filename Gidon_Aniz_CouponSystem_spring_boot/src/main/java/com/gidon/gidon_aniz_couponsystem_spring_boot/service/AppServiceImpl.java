package com.gidon.gidon_aniz_couponsystem_spring_boot.service;

import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Company;
import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Coupon;
import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Customer;
import com.gidon.gidon_aniz_couponsystem_spring_boot.mapper.Mapper;
import com.gidon.gidon_aniz_couponsystem_spring_boot.repository.CompanyRepository;
import com.gidon.gidon_aniz_couponsystem_spring_boot.repository.CouponRepository;
import com.gidon.gidon_aniz_couponsystem_spring_boot.repository.CustomerRepository;
import com.gidon.gidon_aniz_couponsystem_spring_boot.service.ex.EntityAlreadyExist;
import com.gidon.gidon_aniz_couponsystem_spring_boot.service.ex.EntityNotExist;
import com.gidon.gidon_aniz_couponsystem_spring_boot.service.ex.NotMeetTheConditions;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CompanyDto;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CouponDto;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AppServiceImpl implements AppService {

    public static final int ONE_WEEK_MILLIS = 604800000;
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    private final Mapper mapper;


    @Override
    public Optional<CompanyDto> insertCompany(CompanyDto companyDto) {
        if (companyIsPresent(companyDto)) {
            throw entityAlreadyExist("Company: " + companyDto.getUuid() + " already exist");
        }
        checkPasswordCondition(companyDto.getPassword());

        return Optional.of(companyRepository.save(mapper.map(companyDto))).map(mapper::map);
    }

    @Override
    public void deleteCompanyByUuid(UUID uuid) {

        if (companyIsEmpty(uuid)) {
            throw entityNotExist("Company: " + uuid + " not exist");
        }
        companyRepository.deleteByUuid(uuid);
    }

    @Override
    public void updateCompanyNameByUuid(UUID uuid, String name) {
        if (companyIsEmpty(uuid)) {
            throw entityNotExist("Company: " + uuid + " not exist");
        }
        companyRepository.updateName(uuid,name);
    }

    @Override
    public void updateCompanyEmailByUUid(UUID uuid, String email) {
        if (companyIsEmpty(uuid)) {
            throw entityNotExist("Company with " + uuid + " doesn't exist");
        }
        if (companyRepository.findByEmail(email).isPresent()) {
            throw entityAlreadyExist("Customer with " + email + " already exist");
        }
        companyRepository.updateEmailByUUid(uuid, email);
    }

    @Override
    public void updateCompanyPasswordByUuid(UUID uuid, String password) {
        if (companyIsEmpty(uuid)) {
            throw entityNotExist("Customer with " + uuid + " doesn't exist");
        }
        checkPasswordCondition(password);

        companyRepository.updatePasswordByUuid(uuid, password);
    }

    @Override
    public Optional<CompanyDto> findCompanyByUuid(UUID uuid) {
        if (companyIsEmpty(uuid)) {
            throw entityNotExist("Company: " + uuid + " not exist");
        }
        return companyRepository.findByUuid(uuid).map(mapper::map);
    }

    @Override
    public List<CompanyDto> findAllCompanies() {
        if (companyRepository.findAll().isEmpty()) {
            throw entityNotExist("No companies exist");
        }
        return companyRepository.findAll().stream().map(mapper::map).collect(Collectors.toList());
    }

    @Override
    public Optional<CouponDto> createCoupon(CouponDto couponDto, UUID companyUuid) {
        Optional<Company> optCompany = companyRepository.findByUuid(companyUuid);
        Coupon coupon = mapper.map(couponDto);
        if (optCompany.isEmpty()) {
            throw entityNotExist("Company for this coupon not exist");
        }
        if (couponRepository.findCouponByTitleAndCompany_Uuid(coupon.getTitle(), companyUuid).isPresent()) {
            throw entityAlreadyExist("Coupon already exist");
        }
        if (couponDto.getAmount() <= 0 || couponDto.getPrice().intValue() <= 0
                || couponDto.getEndDate().before(new Timestamp(System.currentTimeMillis()))) {
            throw new NotMeetTheConditions("Coupon amount/price can't be <0 or expired");
        }
        coupon.setCompany(optCompany.get());
        couponRepository.save(coupon);
        optCompany.get().getCoupons().add(coupon);
        companyRepository.save(optCompany.get());
        return Optional.of(mapper.map(coupon));
    }

    @Override
    public void deleteCouponByUuid(UUID uuid) {

        if (couponIsEmpty(uuid)) {
            throw entityNotExist("Coupon: " + uuid + " not exist");
        }
        couponRepository.deleteByUuid(uuid);
    }

    @Override
    @Transactional
    public int deleteAllExpiredCoupons() {
        return couponRepository.deleteTheExpiredCoupons();
    }


    @Override
    public List<CouponDto> findAllCoupons() {
        if (couponRepository.findAll().isEmpty()) {
            throw entityNotExist("No coupons find");
        }
        return couponRepository.findAll().stream().map(mapper::map).collect(Collectors.toList());
    }


    @Override
    public Optional<CouponDto> findCouponByUuid(UUID uuid) {
        if (couponIsEmpty(uuid)) {
            throw entityNotExist("Coupon with " + uuid + " doesn't exist");
        }
        return couponRepository.findByUuid(uuid).map(mapper::map);
    }

    @Override
    public Optional<CustomerDto> insertCustomer(CustomerDto customerDto) {
        if (customerIsPresent(customerDto)) {
            throw entityAlreadyExist("Customer with " + customerDto.getUuid() + " already exist");
        }
        return Optional.of(customerRepository.save(mapper.map(customerDto))).map(mapper::map);
    }


    @Override
    public void deleteCustomerByUuid(UUID uuid) {
        if (customerIsEmpty(uuid)) {
            throw entityNotExist("Customer with " + uuid + " doesn't exist");
        }
        customerRepository.deleteByUuid(uuid);
    }


    @Override
    public void updateCustomerEmailByUuid(UUID uuid, String email) {
        if (customerIsEmpty(uuid)) {
            throw entityNotExist("Customer with " + uuid + " doesn't exist");
        }
        if (customerRepository.findByEmail(email).isPresent()) {
            throw entityAlreadyExist("Customer with " + email + " already exist");
        }

        customerRepository.updateEmail(uuid, email);
    }

    @Override
    public void updateCustomerPasswordByUuid(UUID uuid, String password) {

        if (customerIsEmpty(uuid)) {
            throw entityNotExist("Customer with " + uuid + " doesn't exist");
        }
        checkPasswordCondition(password);

        customerRepository.updatePassword(uuid, password);
    }

    @Override
    public void updateCustomerFirstNameByUuid(UUID uuid, String firstName) {

        if (customerIsEmpty(uuid)) {
            throw entityNotExist("Customer with " + uuid + " doesn't exist");
        }

        customerRepository.updateFirstName(uuid, firstName);
    }

    @Override
    public void updateCustomerLastNameByUuid(UUID uuid, String lastName) {

        if (customerIsEmpty(uuid)) {
            throw entityNotExist("Customer with " + uuid + " doesn't exist");
        }

        customerRepository.updateLastName(uuid, lastName);
    }

    @Override
    public Optional<CustomerDto> findCustomerByUuid(UUID uuid) {
        if (customerIsEmpty(uuid)) {
            throw entityNotExist("Customer with " + uuid + " doesn't exist");
        }
        return customerRepository.findByUuid(uuid).map(mapper::map);
    }

    @Override
    public List<CustomerDto> findAllCustomers() {
        if (customerRepository.findAll().isEmpty()) {
            throw entityNotExist("No customer exist");
        }
        return customerRepository.findAll().stream().map(mapper::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void purchase(UUID couponUuid, UUID customerUuid) {

        Optional<Coupon> couponByUuid = couponRepository.findByUuid(couponUuid);
        Optional<Customer> customerByUuid = customerRepository.findByUuid(customerUuid);

        if (couponByUuid.isEmpty() || customerByUuid.isEmpty()) {
            throw entityNotExist("Coupon/customer doesn't exist");
        } else if (couponByUuid.get().getAmount() <= 0 || couponByUuid.get().getEndDate().before(new Timestamp(System.currentTimeMillis()))) {
            throw new NotMeetTheConditions("Can't purchase coupon, amount is 0 or coupon expired");
        } else if (couponRepository.findByCustomer_Uuid(customerUuid).stream().map(Coupon::getUuid).anyMatch(uuid -> uuid.equals(couponUuid))) {
            throw new NotMeetTheConditions("Can not buy coupon that already purchased in the past");
        }
        customerByUuid.get().getCoupons().add(couponByUuid.get());
        customerRepository.save(customerByUuid.get());

        int couponNewAmount = couponByUuid.get().getAmount() - 1;
        couponByUuid.get().setAmount(couponNewAmount);
        couponRepository.save(couponByUuid.get());
    }

    @Override
    public List<CouponDto> findAllCouponsByCustomerUuid(UUID uuid) {
        if (couponRepository.findByCustomer_Uuid(uuid).isEmpty()) {
         return Collections.emptyList();
        }
        return couponRepository.findByCustomer_Uuid(uuid).stream().map(mapper::map).collect(Collectors.toList());
    }

    @Override
    public List<CouponDto> findAllCouponsByCompanyUuid(UUID uuid) {
        if (couponRepository.findAllByCompany_Uuid(uuid).isEmpty()) {
            throw entityNotExist("No coupons find");
        }
        return couponRepository.findAllByCompany_Uuid(uuid).stream().map(mapper::map).collect(Collectors.toList());
    }

    @Override
    public List<CouponDto> findAllCouponsByCustomerExpiredInOneWeek(UUID uuid) {
        if (couponRepository.findByCustomer_UuidAndEndDateIsBetween(uuid
                , new Timestamp(System.currentTimeMillis())
                , new Timestamp(System.currentTimeMillis() + ONE_WEEK_MILLIS)).isEmpty()) {
            throw entityNotExist("No coupons found");
        }
        return couponRepository.findByCustomer_UuidAndEndDateIsBetween(uuid
                , new Timestamp(System.currentTimeMillis())
                , new Timestamp(System.currentTimeMillis() + ONE_WEEK_MILLIS)).stream().map(mapper::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateCouponAmountByUuid(int amount, UUID uuid) {
        Optional<Coupon> coupon = couponRepository.findByUuid(uuid);
        if (coupon.isEmpty() || amount <= 0) {
            throw entityNotExist("Coupon/customer doesn't exist or amount <=0");
        }
        int totalAmount = coupon.get().getAmount() + amount;
        couponRepository.updateAmountByUuid(totalAmount, uuid);
    }

    private boolean companyIsEmpty(UUID uuid) {
        return companyRepository.findByUuid(uuid).isEmpty();
    }

    private boolean companyIsPresent(CompanyDto companyDto) {
        return companyRepository.findByUuid(companyDto.getUuid()).isPresent();
    }

    private boolean customerIsEmpty(UUID uuid) {
        return customerRepository.findByUuid(uuid).isEmpty();
    }

    private boolean customerIsPresent(CustomerDto customerDto) {
        return customerRepository.findByUuid(customerDto.getUuid()).isPresent();
    }

    private boolean couponIsEmpty(UUID uuid) {
        return couponRepository.findByUuid(uuid).isEmpty();
    }

    private EntityNotExist entityNotExist(String string) {
        return new EntityNotExist(string);
    }

    private EntityAlreadyExist entityAlreadyExist(String string) {
        return new EntityAlreadyExist(string);
    }

    private void checkPasswordCondition(String password) {
        int minPasswordLength = 8, upChars = 0, lowChars = 0, digits = 0;
        char[] passwordCharArr = password.toCharArray();

        if (password.length() < minPasswordLength) {
            throw new NotMeetTheConditions("Password does not meet the conditions");
        } else {
            for (char ch : passwordCharArr) {
                if (Character.isUpperCase(ch))
                    upChars = 1;
                else if (Character.isLowerCase(ch))
                    lowChars = 1;
                else if (Character.isDigit(ch))
                    digits = 1;
            }
        }
        if (upChars != 1 || lowChars != 1 || digits != 1) {
            throw new NotMeetTheConditions("Password does not meet the conditions");
        }
    }
}
