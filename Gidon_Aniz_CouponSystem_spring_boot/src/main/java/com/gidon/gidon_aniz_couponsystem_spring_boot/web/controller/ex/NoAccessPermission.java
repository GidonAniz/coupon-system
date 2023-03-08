package com.gidon.gidon_aniz_couponsystem_spring_boot.web.controller.ex;

public class NoAccessPermission extends RuntimeException {
    public NoAccessPermission(String no_access_permit) {
        super(no_access_permit);
    }
}
