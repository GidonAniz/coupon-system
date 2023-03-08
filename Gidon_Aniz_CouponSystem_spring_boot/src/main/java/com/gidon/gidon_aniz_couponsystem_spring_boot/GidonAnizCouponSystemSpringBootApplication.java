package com.gidon.gidon_aniz_couponsystem_spring_boot;


import com.gidon.gidon_aniz_couponsystem_spring_boot.web.ClientSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class GidonAnizCouponSystemSpringBootApplication {


    public static void main(String[] args) {
        SpringApplication.run(GidonAnizCouponSystemSpringBootApplication.class, args);
    }

    @Bean(name = "tokens")
    public Map<String, ClientSession> tokensMap() {
        return new HashMap<>();
    }

}
