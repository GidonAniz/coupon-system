package com.gidon.gidon_aniz_couponsystem_spring_boot.repository;

import com.gidon.gidon_aniz_couponsystem_spring_boot.entity.Company;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@DataJpaTest
class CompanyRepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    @Test
    public void whenCompanyIsFetched_ShouldGetOptionalCompanyWithSameUuid() {
        UUID randomUuid = UUID.randomUUID();
        Company company = Company.builder().uuid(randomUuid).name("companyTest").creationTimestamp(Timestamp.from(Instant.now()))
                .email("email@gmail.com")
                .password("Aa123456!@#")
                .build();
        Company savedCompany =  this.companyRepository.save(company);
        Optional<Company> optCompany = this.companyRepository.findByUuid(randomUuid);
        Assertions.assertThat(optCompany).get().extracting(Company::getUuid).isEqualTo(savedCompany.getUuid());
    }
}