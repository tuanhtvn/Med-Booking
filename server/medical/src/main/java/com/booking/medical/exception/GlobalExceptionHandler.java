package com.booking.medical.exception;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.booking.medical.common.Response;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private Environment env;
    private Response resp = new Response();

    // Lỗi hệ thống
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception exception) {
        log.error("Exception: " + exception.getMessage());

        if (env.getActiveProfiles()[0].equals("dev")) {
            log.error("Exception class: " + exception.getClass().getName());
            resp.setMessage(exception.getMessage());
        } else {
            resp.setMessage(
                    "Đã xảy ra lỗi nội bộ trên server. Chúng tôi đang nỗ lực khắc phục sự cố này. Vui lòng thử lại sau ít phút. Nếu bạn tiếp tục gặp vấn đề, hãy liên hệ hỗ trợ của chúng tôi.");
        }

        resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    // Lỗi không hợp lệ do validate
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        log.error("Exception: " + exception.getMessage());

        String message = exception.getBindingResult().getFieldError().getDefaultMessage();
        resp.setMessage(message);
        resp.setStatus(HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    // Custom Exception Error
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response> handleCustomException(CustomException e) {
        log.error("Custom Exception: " + e.getMessage());
        resp.setStatus(e.getStatus());
        resp.setMessage(e.getMessage());

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    // Authentication Error
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<Response> handleInsufficientAuthenticationException(Exception exception) {
        log.error("Exception: " + exception.getMessage());
        String message = "Yêu cầu của bạn không được xác thực. Vui lòng đăng nhập và thử lại.";
        resp.setMessage(message);
        resp.setStatus(HttpStatus.UNAUTHORIZED);

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    // Access Denied Error
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response> handleAccessDeniedException(Exception exception) {
        log.error("Exception: " + exception.getMessage());
        String message = "Bạn không có quyền truy cập vào tài nguyên này.";
        resp.setMessage(message);
        resp.setStatus(HttpStatus.FORBIDDEN);

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    // Not Found Error
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Response> handleIOException(IOException e) {
        log.error("Exception: " + e.getMessage());
        resp.setStatus(HttpStatus.NOT_FOUND);
        resp.setMessage("Không tìm thấy tài nguyên");

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    // Not Found Error
    @ExceptionHandler({ NoResourceFoundException.class, HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<Response> handleIOException(Exception e) {
        log.error("Exception: " + e.getMessage());
        resp.setStatus(HttpStatus.NOT_FOUND);
        resp.setMessage("Không tìm thấy endpoint");

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

}
