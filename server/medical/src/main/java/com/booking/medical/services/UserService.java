package com.booking.medical.services;

import com.booking.medical.models.dtos.LoginInputDTO;
import com.booking.medical.models.dtos.UpdatePasswordDTO;
import com.booking.medical.models.dtos.UserDTO;
import com.booking.medical.models.dtos.VerifyForgotPasswordDTO;
import com.booking.medical.models.entities.User;

import java.util.List;

import com.booking.medical.models.dtos.CreateUserInputDTO;
import com.booking.medical.models.dtos.ForgotPasswordDTO;

public interface UserService {
    UserDTO RegisterUser(CreateUserInputDTO createUserInputDTO);

    UserDTO Login(LoginInputDTO loginInputDTO);

    String ForgotPassword(ForgotPasswordDTO forgotPasswordDTO);

    void VerifyForgotPassword(String id, VerifyForgotPasswordDTO verifyForgotPassword);

    UserDTO UpdatePassword(UpdatePasswordDTO UpdatePasswordDTO);

    List<UserDTO> GetAll(String keyword, int page);

    void Delete(String id);

    User GetUserWithAuthentication();

    void Save(User user);

}
