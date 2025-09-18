package com.booking.medical.utils;

import java.util.Random;

public class VerifyCode {

    public static String Get() {
        String verifyCode = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            verifyCode += random.nextInt(10);
        }
        return verifyCode;
    }
}
