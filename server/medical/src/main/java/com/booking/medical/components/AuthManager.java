package com.booking.medical.components;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.booking.medical.common.IAuthentication;

@Component
public class AuthManager implements IAuthentication {

    @Override
    public String getUserIDAuthentication() {
        String userID = SecurityContextHolder.getContext().getAuthentication().getName();
        return userID;
    }

}
