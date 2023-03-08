package com.gidon.gidon_aniz_couponsystem_spring_boot.web.advice;

import com.gidon.gidon_aniz_couponsystem_spring_boot.service.ex.EntityAlreadyExist;
import com.gidon.gidon_aniz_couponsystem_spring_boot.service.ex.EntityNotExist;
import com.gidon.gidon_aniz_couponsystem_spring_boot.service.ex.InvalidLoginException;
import com.gidon.gidon_aniz_couponsystem_spring_boot.service.ex.NotMeetTheConditions;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.controller.ex.EntityCantBeEmpty;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.controller.ex.NoAccessPermission;
import com.gidon.gidon_aniz_couponsystem_spring_boot.web.response.AppErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(EntityAlreadyExist.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    AppErrorResponse handleEntityAlreadyExist(RuntimeException e) {
        return AppErrorResponse.entityAlreadyExist(e.getMessage());
    }

    @ExceptionHandler(EntityNotExist.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    AppErrorResponse handleEntityNotExist(RuntimeException e) {
        return AppErrorResponse.entityNotExist(e.getMessage());
    }

    @ExceptionHandler(EntityCantBeEmpty.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    AppErrorResponse handleEntityCantBeEmpty(RuntimeException e) {
        return AppErrorResponse.entityCantBeEmpty(e.getMessage());
    }

    @ExceptionHandler(NotMeetTheConditions.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    AppErrorResponse HandleEntityNotMeetCondition(RuntimeException e) {
        return AppErrorResponse.notMeetCondition(e.getMessage());
    }

    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    AppErrorResponse HandleUnauthorized(RuntimeException e) {
        return AppErrorResponse.invalidUserNamePassword(e.getMessage());
    }

    @ExceptionHandler(NoAccessPermission.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    AppErrorResponse HandleNoPermission(RuntimeException e) {
        return AppErrorResponse.noAccessPermission(e.getMessage());
    }
}