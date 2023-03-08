package com.gidon.gidon_aniz_couponsystem_spring_boot.web.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCredentials {
    private final String email;
    private final String password;
}
