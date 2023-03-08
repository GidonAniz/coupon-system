package com.gidon.gidon_aniz_couponsystem_spring_boot.web.controller;


import com.gidon.gidon_aniz_couponsystem_spring_boot.service.AppService;
import com.gidon.gidon_aniz_couponsystem_spring_boot.service.ex.EntityNotExist;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.ClientSession;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.controller.ex.EntityCantBeEmpty;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.controller.ex.NoAccessPermission;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CompanyDto;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CouponDto;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/couponSystem")
public class Controller {
    @Value("${token.duration}")
    private long tokenTime;
    private final AppService appService;
    private final Map<String, ClientSession> tokens;

    @GetMapping("/getCustomer/all/{token}")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(@PathVariable String token) {
        tokenValidation(token, "admin");
        return ResponseEntity.ok(appService.findAllCustomers());
    }

    @GetMapping("/getCompany/all/{token}")
    public ResponseEntity<List<CompanyDto>> getAllCompany(@PathVariable String token) {
        tokenValidation(token, "admin");
        return ResponseEntity.ok(appService.findAllCompanies());
    }

    @GetMapping("/getCoupon/all/{token}")
    public ResponseEntity<List<CouponDto>> getAllCoupons(@PathVariable String token) {
        tokenValidation(token, "admin");
        return ResponseEntity.ok(appService.findAllCoupons());
    }

    @PostMapping("customer/insert/{token}")
    public ResponseEntity<CustomerDto> insert(@RequestBody CustomerDto customerDto, @PathVariable String token) {
        tokenValidation(token, "admin");
        Optional<CustomerDto> persistCustomer = appService.insertCustomer(customerDto);
        if (persistCustomer.isEmpty()) {
            throw new EntityNotExist("Something went wrong with customer insert");
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").build(persistCustomer.get().getUuid());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(persistCustomer.get());
    }

    @DeleteMapping("customer/delete/{uuid}/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerByUuid(@PathVariable UUID uuid, @PathVariable String token) {
        tokenValidation(token, "admin");
        appService.deleteCustomerByUuid(uuid);
    }

    @PostMapping("company/insert/{token}")
    public ResponseEntity<CompanyDto> insert(@RequestBody CompanyDto companyDto, @PathVariable String token) {
        tokenValidation(token, "admin");
        Optional<CompanyDto> persistCompany = appService.insertCompany(companyDto);
        if (persistCompany.isEmpty()) {
            throw new EntityNotExist("Something wend wrong with company insert");
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").build(persistCompany.get().getUuid());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(persistCompany.get());
    }

    @DeleteMapping("company/delete/{uuid}/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyByUuid(@PathVariable UUID uuid, @PathVariable String token) {
        tokenValidation(token, "admin");
        appService.deleteCompanyByUuid(uuid);
    }

    @PostMapping("customer/update_email/{email}/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomerEmailByUuid(@PathVariable String email, @PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "customer");
        if (email.equals("")) {
            throw new EntityCantBeEmpty("Email can't be empty");
        }
        appService.updateCustomerEmailByUuid(clientSession.getUuid(), email);
    }

    @PostMapping("customer/update_password/{password}/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomerPasswordByUuid(@PathVariable String password, @PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "customer");
        appService.updateCustomerPasswordByUuid(clientSession.getUuid(), password);
    }

    @PostMapping("customer/update_firstName/{firstName}/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomerFirstNameByUuid(@PathVariable String firstName, @PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "customer");
        appService.updateCustomerFirstNameByUuid(clientSession.getUuid(), firstName);
    }

    @PostMapping("customer/update_lastName/{lastName}/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomerLastNameByUuid(@PathVariable String lastName, @PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "customer");
        appService.updateCustomerLastNameByUuid(clientSession.getUuid(), lastName);
    }

    @GetMapping("/getCompany/{token}")
    public ResponseEntity<Optional<CompanyDto>> getCompanyByUuid(@PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "company");
        return ResponseEntity.ok(appService.findCompanyByUuid(clientSession.getUuid()));
    }

    @PostMapping("company/update_name/{name}/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCompanyNameByUuid(@PathVariable String name, @PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "company");
        appService.updateCompanyNameByUuid(clientSession.getUuid(), name);
    }

    @PostMapping("company/update_password/{password}/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCompanyPasswordByUuid(@PathVariable String password, @PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "company");
        appService.updateCompanyPasswordByUuid(clientSession.getUuid(), password);
    }

    @PostMapping("company/update_email/{email}/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCompanyEmailByUuid(@PathVariable String email, @PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "company");
        if (email.equals("")) {
            throw new EntityCantBeEmpty("Email can't be empty");
        }
        appService.updateCompanyEmailByUUid(clientSession.getUuid(), email);
    }

    @DeleteMapping("delete_expired_coupons/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int deleteAllExpiredCoupons(@PathVariable String token) {
        tokenValidation(token, "admin");
        return appService.deleteAllExpiredCoupons();
    }

    @GetMapping("/getCustomer/{token}")
    public ResponseEntity<Optional<CustomerDto>> getCustomerByUuid(@PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "customer");
        return ResponseEntity.ok(appService.findCustomerByUuid(clientSession.getUuid()));
    }

    @GetMapping("/customers/all/purchased/{token}")
    public ResponseEntity<List<CouponDto>> getAllCustomerCouponsPurchased(@PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "customer");
        List<CouponDto> allCouponsByCustomerUuid = appService.findAllCouponsByCustomerUuid(clientSession.getUuid());
        return ResponseEntity.ok(allCouponsByCustomerUuid);
    }

    @GetMapping("/customers/all/not-purchased/{token}")
    public ResponseEntity<List<CouponDto>> getAllCustomerCouponsNotPurchased(@PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "customer");
        List<CouponDto> allCoupons = appService.findAllCoupons();
        List<CouponDto> allCouponsByCustomerUuid = appService.findAllCouponsByCustomerUuid(clientSession.getUuid());
        if (!allCouponsByCustomerUuid.isEmpty()) {
            allCoupons.removeAll(allCouponsByCustomerUuid);
        }
        return ResponseEntity.ok(allCoupons);
    }

    @GetMapping("/customers/all/purchasedOneWeekExpired/{token}")
    public ResponseEntity<List<CouponDto>> getAllCustomerCouponsOneWeekExpired(@PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "customer");
        return ResponseEntity.ok(appService.findAllCouponsByCustomerExpiredInOneWeek(clientSession.getUuid()));
    }

    @PostMapping("customer/purchase/{couponUuid}/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void purchase(@PathVariable UUID couponUuid, @PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "customer");
        UUID customerUuid = clientSession.getUuid();
        appService.purchase(couponUuid, customerUuid);
    }

    @PostMapping("company/create_coupon/{token}")
    public ResponseEntity<CouponDto> insert(@RequestBody CouponDto couponDto, @PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "company");
        Optional<CouponDto> persistCoupon = appService.createCoupon(couponDto, clientSession.getUuid());
        if (persistCoupon.isEmpty()) {
            throw new EntityNotExist("Something wend wrong with coupon insert");
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").build(persistCoupon.get().getUuid());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(persistCoupon.get());
    }

    @PostMapping("company/delete_coupon/{couponUuid}/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoupon(@PathVariable UUID couponUuid, @PathVariable String token) {
        tokenValidation(token, "company");
        appService.deleteCouponByUuid(couponUuid);
    }

    @PostMapping("company/update-amount/{couponUuid}/{amount}/{token}")
    public ResponseEntity<Optional<CouponDto>> updateCouponAmount(@PathVariable UUID couponUuid, @PathVariable int amount, @PathVariable String token) {
        tokenValidation(token, "company");
        appService.updateCouponAmountByUuid(amount, couponUuid);
        Optional<CouponDto> coupon = appService.findCouponByUuid(couponUuid);
        if (coupon.isEmpty()) {
            throw new EntityCantBeEmpty("Something went wrong with coupon amount update");
        }
        coupon.get().setAmount(coupon.get().getAmount() + amount);
        return ResponseEntity.ok(coupon);
    }

    @GetMapping("/company/couponAll/{token}")
    public ResponseEntity<List<CouponDto>> getAllCompanyCoupons(@PathVariable String token) {
        ClientSession clientSession = tokenValidation(token, "company");
        return ResponseEntity.ok(appService.findAllCouponsByCompanyUuid(clientSession.getUuid()));
    }

    private ClientSession tokenValidation(String token, String clientType) {

        ClientSession clientSession = tokens.get(token);
        if (
                clientSession == null
                        || !clientSession.getClientType().equals(clientType)
                        || ((System.currentTimeMillis() - clientSession.getLastAccessedMillis()) > tokenTime)) {
            throw new NoAccessPermission("Token is invalid");
        } else
            clientSession.updateLastAccessedMillis();
        return clientSession;
    }
}
