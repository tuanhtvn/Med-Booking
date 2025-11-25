package com.booking.medical.services;

import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.booking.medical.common.IAuthentication;
import com.booking.medical.exception.CustomException;
import com.booking.medical.models.dtos.CreateRecordInputDTO;
import com.booking.medical.models.dtos.CreateUserInputDTO;
import com.booking.medical.models.dtos.ForgotPasswordDTO;
import com.booking.medical.models.dtos.LoginInputDTO;
import com.booking.medical.models.dtos.RecordDTO;
import com.booking.medical.models.dtos.UpdatePasswordDTO;
import com.booking.medical.models.dtos.UpdateRecordInputDTO;
import com.booking.medical.models.dtos.UserDTO;
import com.booking.medical.models.dtos.VerifyForgotPasswordDTO;
import com.booking.medical.models.entities.Record;
import com.booking.medical.models.entities.User;
import com.booking.medical.models.entities.VerifyCode;
import com.booking.medical.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImp implements UserService, RecordService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IAuthentication authentication;

    @Override
    public UserDTO RegisterUser(CreateUserInputDTO createUserInputDTO) {
        // Check email unique
        if (userRepository.findByEmail(createUserInputDTO.getEmail()).isPresent()) {
            throw new CustomException("Email này đã được sử dụng", HttpStatus.BAD_REQUEST);
        }
        // Success then create user
        User user = modelMapper.map(createUserInputDTO, User.class);
        log.debug("Value user: " + user);
        user = userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO Login(LoginInputDTO loginInputDTO) {
        // check username by email
        User user = userRepository.findByEmail(loginInputDTO.getEmail()).orElseThrow(() -> {
            log.error("LOGIN - User not found by email: {}", loginInputDTO.getEmail());
            throw new CustomException("Tài khoản hoặc mật khẩu không chính xác", HttpStatus.BAD_REQUEST);
        });
        // check password
        if (BCrypt.checkpw(loginInputDTO.getPassword(), user.getPassword())) {
            // return User if success
            log.info("LOGIN - User by email: {} - SUCCESS", user.getEmail());
            return modelMapper.map(user, UserDTO.class);
        }
        // throw Exception if fail
        log.error("LOGIN - User by email: {} - wrong password: {}", loginInputDTO.getEmail(),
                loginInputDTO.getPassword());
        throw new CustomException("Tài khoản hoặc mật khẩu không chính xác", HttpStatus.BAD_REQUEST);
    }

    @Override
    public String ForgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        User user = userRepository.findByEmail(forgotPasswordDTO.getEmail()).orElseThrow(() -> {
            log.error("User not found by email: {}", forgotPasswordDTO.getEmail());
            throw new CustomException("Địa chỉ email chưa đăng ký tài khoản trên hệ thống", HttpStatus.BAD_REQUEST);
        });

        String code = VerifyCode.Get();

        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setCode(code);
        user.setVerifyCode(verifyCode);

        userRepository.save(user);

        try {
            emailService.SendVerifyCode(user.getEmail(), user.getFullName(), code);
        } catch (Exception e) {
            log.error("Send email fail: {}", e.getMessage());
            user.setVerifyCode(null);
            userRepository.save(user);
            throw new CustomException("Hệ thống đang bị lỗi gửi mã xác minh. Vui lòng thử lại sau",
                    HttpStatus.SERVICE_UNAVAILABLE);
        }

        return user.getId().toString();
    }

    @Override
    public void VerifyForgotPassword(Long id, VerifyForgotPasswordDTO verifyForgotPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new CustomException("Lỗi không tìm thấy người dùng này", HttpStatus.BAD_REQUEST);
        });

        if (!user.getVerifyCode().Verify(verifyForgotPassword.getVerifycode())) {
            log.info("Người dùng; {} nhập mã xác minh {} khôi phục mật khẩu không chính xác", user.getEmail(),
                    verifyForgotPassword.getVerifycode());
            throw new CustomException("Mã xác mình không chính xác Hoặc đã hết hạn", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(verifyForgotPassword.getPassword());
        userRepository.save(user);
        log.info("Người dùng: {} đã cập nhật mật khẩu thành công", user.getEmail());
    }

    @Override
    public UserDTO UpdatePassword(UpdatePasswordDTO UpdatePasswordDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UpdatePassword'");
    }

    @Override
    public List<UserDTO> GetAll(String keyword, int page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetAll'");
    }

    @Override
    public void Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

    @Override
    public Page<RecordDTO> RecordGetAll(String keyword, int page, int size) {
        User user = this.GetUserWithAuthentication();

        if (user.getRecords() == null || user.getRecords().size() == 0) {
            return Page.empty();
        }
        List<com.booking.medical.models.entities.Record> records = user.getRecords().stream()
                .filter(item -> item.getFullName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();

        if (records.size() == 0) {
            return Page.empty();
        }

        int total = records.size();
        int start = page * size;
        int end = Math.min(start + size, total);
        List<Record> paginatedList = records.subList(start, end);
        Page<Record> entitiesPage = new PageImpl<>(paginatedList, PageRequest.of(page, size), total);

        return entitiesPage.map(item -> {
            return modelMapper.map(item, RecordDTO.class);
        });
    }

    @Override
    public void RecordCreate(CreateRecordInputDTO createRecordInputDTO) {
        User user = this.GetUserWithAuthentication();

        Record record = modelMapper.map(createRecordInputDTO, Record.class);

        if (user.getRecords() == null) {
            user.setRecords(new ArrayList<>());
        }
        user.getRecords().add(record);
        userRepository.save(user);
    }

    @Override
    public void RecordUpdate(UpdateRecordInputDTO UpdateRecordInputDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'RecordUpdate'");
    }

    @Override
    public void RecordDelete(String recordID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'RecordDelete'");
    }

    @Override
    public User GetUserWithAuthentication() {
        Long userID = authentication.getUserIDAuthentication();

        User user = userRepository.findById(userID).orElseThrow(() -> {
            throw new CustomException("Không tìm thấy người dùng", HttpStatus.BAD_REQUEST);
        });
        return user;
    }

    @Override
    public void Save(User user) {
        userRepository.save(user);
    }
}
