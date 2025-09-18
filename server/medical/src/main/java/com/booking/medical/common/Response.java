package com.booking.medical.common;

import java.util.HashMap;

import org.springframework.http.HttpStatus;

public class Response extends HashMap<String, Object> {

    public void setStatus(HttpStatus status) {
        this.put("status", status.value());
    }

    public HttpStatus getStatus() {
        Object statusValue = this.get("status");
        return HttpStatus.valueOf((Integer) statusValue);
    }

    public void setMessage(String message) {
        this.put("message", message);
    }

}
