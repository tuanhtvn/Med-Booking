package com.booking.medical.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.medical.common.Response;
import com.booking.medical.components.JwtToken;
import com.booking.medical.configs.EnvConfig;
import com.booking.medical.exception.CustomException;
import com.booking.medical.models.dtos.UserDTO;
import com.booking.medical.models.dtos.VerifyForgotPasswordDTO;
import com.booking.medical.models.dtos.CreateUserInputDTO;
import com.booking.medical.models.dtos.ForgotPasswordDTO;
import com.booking.medical.models.dtos.LoginInputDTO;
import com.booking.medical.services.UserService;
import com.booking.medical.utils.CustomCookie;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private EnvConfig envConfig;
    private Response resp = new Response();

    @PostMapping("/auth/user/register")
    public ResponseEntity<Response> CreateUser(@RequestBody @Valid CreateUserInputDTO createUserInputDTO,
            HttpServletResponse response) {
        UserDTO userDTO = userService.RegisterUser(createUserInputDTO);
        String token = jwtToken.getToken(userDTO.getId(), userDTO.getRole());
        CustomCookie.Set(response, "Authorization", token, envConfig.getCookieExpriesTime(),
                true, true);
        resp.setMessage("Đăng nhập thành công");
        resp.setStatus(HttpStatus.OK);
        resp.put("user", userDTO);

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    @PostMapping("/auth/user/login")
    public ResponseEntity<Response> LoginApp(@RequestBody @Valid LoginInputDTO loginInputDTO,
            HttpServletResponse response) {
        UserDTO userDTO = userService.Login(loginInputDTO);
        String token = jwtToken.getToken(userDTO.getId(), userDTO.getRole());
        CustomCookie.Set(response, "Authorization", token, envConfig.getCookieExpriesTime(),
                true, true);
        resp.setMessage("Đăng nhập thành công");
        resp.setStatus(HttpStatus.OK);

        resp.put("user", userDTO);

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    @PostMapping("/auth/user/password/forgot")
    public ResponseEntity<Response> ForgotPassword(@RequestBody @Valid ForgotPasswordDTO forgotPasswordDTO) {
        String id = userService.ForgotPassword(forgotPasswordDTO);

        resp.setMessage("Mã xác minh đã được gửi tới địa chỉ email: " + forgotPasswordDTO.getEmail());
        resp.setStatus(HttpStatus.OK);
        resp.put("id", id);

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    @PutMapping("/auth/user/password/reset/{id}")
    public ResponseEntity<Response> VerifyForgotPassword(@PathVariable("id") Long id,
            @RequestBody @Valid VerifyForgotPasswordDTO verifyForgotPassword) {
        if (!verifyForgotPassword.getPassword().equals(verifyForgotPassword.getConfirmPassword())) {
            throw new CustomException("Mật khẩu nhập lại không khớp", HttpStatus.BAD_REQUEST);
        }

        userService.VerifyForgotPassword(id, verifyForgotPassword);
        resp.setMessage("Bạn đã đặt lại mật khẩu thành công");
        resp.setStatus(HttpStatus.OK);

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }
}
