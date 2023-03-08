package com.gidon.gidon_aniz_couponsystem_spring_boot.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientSession {

    private final UUID uuid;

    private String clientType;
    private long lastAccessedMillis;


    public static ClientSession of(UUID uuid, String clientType) {
        return new ClientSession(uuid, clientType, System.currentTimeMillis());
    }

    public void updateLastAccessedMillis() {
        lastAccessedMillis = System.currentTimeMillis();
    }

}
