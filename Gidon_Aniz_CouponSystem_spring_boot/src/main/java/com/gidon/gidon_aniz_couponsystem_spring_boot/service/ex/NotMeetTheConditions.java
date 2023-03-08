package com.gidon.gidon_aniz_couponsystem_spring_boot.service.ex;

public class NotMeetTheConditions extends RuntimeException {
    public NotMeetTheConditions(String s) {
        super(s);
    }
}
