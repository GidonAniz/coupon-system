package com.gidon.gidon_aniz_couponsystem_spring_boot.scheduler;

import com.gidon.gidon_aniz_couponsystem_spring_boot.service.AppService;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.ClientSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.Set;


@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class SchedulerTasks {
    private final AppService appService;
    @Value("${token.duration}")
    private long tokenTime;

    private final Map<String, ClientSession> tokens;
    Logger logger = LoggerFactory.getLogger(SchedulerTasks.class);

    @Scheduled(fixedRateString = "${task.delete_expired_coupons}")
    public void deleteAllExpiredCoupons() {
        appService.deleteAllExpiredCoupons();
        logger.info("All expired coupons deleted");
    }


    @Scheduled(fixedRateString = "${task.delete_expired_sessions}")
    public void deleteAllExpiredClientSession() {
        if (tokens != null || tokens.size() != 0) {
            Set<Map.Entry<String, ClientSession>> entries = tokens.entrySet();
            for (Map.Entry<String, ClientSession> token : entries) {

                if ((System.currentTimeMillis() - token.getValue().getLastAccessedMillis() > tokenTime)) {
                    tokens.remove(token.getKey());
                    logger.info("All expired ClientSession deleted");
                }
            }
        }
    }
}
