package com.gidon.gidon_aniz_couponsystem_spring_boot.service;

import com.gidon.gidon_aniz_couponsystem_spring_boot.web.ClientSession;

public interface LoginService {

    ClientSession createSession(String email, String password);

    String generateToken();
}
