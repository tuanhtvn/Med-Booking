package com.booking.medical.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CustomCookie {
    public static void Set(HttpServletResponse response, String name, String value, int maxAge,
            boolean httpOnly, boolean sameSite) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge * 24 * 60 * 60);
        response.addCookie(cookie);
        if (sameSite) {
            String cookieHeader = response.getHeader("Set-Cookie") + "; " + "SameSite=Strict";
            response.setHeader("Set-Cookie", cookieHeader);
        }
    }
}
