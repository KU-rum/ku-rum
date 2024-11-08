package ku_rum.backend.domain.user.application;

import ku_rum.backend.domain.department.domain.Department;
import ku_rum.backend.domain.department.domain.repository.DepartmentRepository;
import ku_rum.backend.domain.user.domain.MailSendSetting;
import ku_rum.backend.domain.user.domain.User;
import ku_rum.backend.domain.user.domain.repository.UserRepository;
import ku_rum.backend.domain.user.dto.request.EmailValidationRequest;
import ku_rum.backend.domain.user.dto.request.MailSendRequest;
import ku_rum.backend.domain.user.dto.request.MailVerificationRequest;
import ku_rum.backend.domain.user.dto.request.UserSaveRequest;
import ku_rum.backend.domain.user.dto.response.MailVerificationResponse;
import ku_rum.backend.domain.user.dto.response.UserSaveResponse;
import ku_rum.backend.global.config.MailConfig;
import ku_rum.backend.global.exception.department.NoSuchDepartmentException;
import ku_rum.backend.global.exception.user.DuplicateEmailException;
import ku_rum.backend.global.exception.user.DuplicateStudentIdException;
import ku_rum.backend.global.exception.user.MailSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

import static ku_rum.backend.domain.user.domain.MailSendSetting.*;
import static ku_rum.backend.global.config.MailConfig.*;
import static ku_rum.backend.global.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public UserSaveResponse saveUser(final UserSaveRequest userSaveRequest) {
        validateDuplicateEmail(userSaveRequest.getEmail());
        validateDuplicateStudentId(userSaveRequest.getStudentId());
        validateDepartmentName(userSaveRequest.getDepartment());

        // TODO Password Encoder 적용
        String password = userSaveRequest.getPassword();
        Department department = departmentRepository.findByName(userSaveRequest.getDepartment())
                .orElseThrow(() -> new NoSuchDepartmentException(NO_SUCH_DEPARTMENT));

        User user = User.builder()
                .nickname(userSaveRequest.getNickname())
                .email(userSaveRequest.getEmail())
                .password(password)
                .studentId(userSaveRequest.getStudentId())
                .department(department)
                .build();

        userRepository.save(user);
        return UserSaveResponse.of(user);
    }

    public void validateEmail(final EmailValidationRequest emailValidationRequest) {
        validateDuplicateEmail(emailValidationRequest.getEmail());
    }


    private void validateDuplicateEmail(final String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(DUPLICATE_EMAIL);
        }
    }

    private void validateDuplicateStudentId(final String studentId) {
        if (userRepository.existsByStudentId(studentId)) {
            throw new DuplicateStudentIdException(DUPLICATE_STUDENT_ID);
        }
    }
    private void validateDepartmentName(final String department) {
        if (!departmentRepository.existsByName(department)) {
            throw new NoSuchDepartmentException(NO_SUCH_DEPARTMENT);
        }
    }
}
