package com.gidon.gidon_aniz_couponsystem_spring_boot.service.ex;

public class EntityAlreadyExist extends RuntimeException{
    public EntityAlreadyExist(String s) {
        super(s);
    }
}
