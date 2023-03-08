package com.gidon.gidon_aniz_couponsystem_spring_boot.service.ex;

public class InvalidLoginException extends  RuntimeException {
    public InvalidLoginException(String s) {
        super(s);
    }
}
