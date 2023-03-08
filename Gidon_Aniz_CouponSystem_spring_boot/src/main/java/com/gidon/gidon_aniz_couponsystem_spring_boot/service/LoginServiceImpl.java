package com.gidon.gidon_aniz_couponsystem_spring_boot.service;

import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Company;
import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Customer;
import com.gidon.gidon_aniz_couponsystem_spring_boot.repository.CompanyRepository;
import com.gidon.gidon_aniz_couponsystem_spring_boot.repository.CustomerRepository;
import com.gidon.gidon_aniz_couponsystem_spring_boot.service.ex.InvalidLoginException;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.ClientSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private static final int TOKEN_LENGTH = 8;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    @Override
    public ClientSession createSession(String email, String password) {
        Optional<Company> company = companyRepository.findByEmailAndPassword(email, password);
        Optional<Customer> customer = customerRepository.findByEmailAndPassword(email, password);

        if (email.equals("admin@admin.com") && password.equals("admin")) {
            String clientType = "admin";
            return ClientSession.of(UUID.randomUUID(), clientType);
        }
        if (company.isEmpty() && customer.isEmpty()) {
            throw new InvalidLoginException("Username/password are invalid");
        }
        if (company.isPresent()) {
            String clientType = "company";
            return ClientSession.of(company.get().getUuid(), clientType);
        }
        String clientType = "customer";
        return ClientSession.of(customer.get().getUuid(), clientType);
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(0, TOKEN_LENGTH);
    }
}
