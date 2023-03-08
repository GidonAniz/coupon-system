package com.gidon.gidon_aniz_couponsystem_spring_boot.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppErrorResponse {
    public static final int CODE_ENTITY_NOT_EXIST = 404;
    private static final int CODE_ENTITY_ALREADY_EXIST = 409;
    private static final int CODE_ENTITY_NOT_MEET_CONDITION = 400;
    private static final int UNAUTHORIZED = 401;

    private final long timestamp;
    private final String message;
    private final int code;

    private static AppErrorResponse ofNow(String message, int code) {
        return new AppErrorResponse(System.currentTimeMillis(), message, code);
    }

    public static AppErrorResponse entityNotExist(String message) {
        return ofNow(message, CODE_ENTITY_NOT_EXIST);
    }

    public static AppErrorResponse entityAlreadyExist(String message) {
        return ofNow(message, CODE_ENTITY_ALREADY_EXIST);
    }

    public static AppErrorResponse entityCantBeEmpty(String message) {
        return ofNow(message, CODE_ENTITY_NOT_EXIST);
    }

    public static AppErrorResponse notMeetCondition(String message) {
        return ofNow(message, CODE_ENTITY_NOT_MEET_CONDITION);
    }

    public static AppErrorResponse invalidUserNamePassword(String message) {
        return ofNow(message, UNAUTHORIZED);
    }

    public static AppErrorResponse noAccessPermission(String message) {
        return ofNow(message, UNAUTHORIZED);
    }
}
