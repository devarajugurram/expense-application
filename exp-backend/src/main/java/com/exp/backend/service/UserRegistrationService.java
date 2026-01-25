package com.exp.backend.service;

import com.exp.backend.aop.messageInterface;
import com.exp.backend.model.OTPModel;
import com.exp.backend.model.UserModel;
import com.exp.backend.repo.UserRepository;
import com.exp.backend.template.helpers.ResponseHelperMethods;
import com.exp.backend.template.interfaces.UserRegistrationServicePrint;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static com.exp.backend.template.strings.TemplateStrings.OTP_NOT_MATCH;
import static com.exp.backend.template.strings.TemplateStrings.USER_CREATED_SUCCESSFULLY;
import static com.exp.backend.template.strings.TemplateStrings.SOMETHING_WENT_WRONG;

/**
 * ClassName - UserRegistrationService
 * It implements UserRegistrationServicePrint interface.
 * This class defines registerUser, getRegistrationResponseHelper and securityConversionAlgorithm methods
 * It is a stereotype component, bean is controlled by spring or application context.
 * This class takes data from client in the form of otpModel and verifies an OTP and register user into application.
 * UserModel and OTP is stored in Cache buckets in their respective buckets used to save in database in persistence method.
 */
@Service
public class UserRegistrationService implements UserRegistrationServicePrint {

    /**
     * Variables - userRepository,cacheManager,passwordEncoder
     * Data Types - UserRepository, CacheManager, PasswordEncoder
     */

    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CacheManager cacheManager;
    private final ResponseHelperMethods responseHelperMethods;

    /**
     * ConstructorName - UserRegistrationService
     * Constructor automatically inject the object from application context.
     * Here Dependency Injection done by constructor instead of using field and annotation injection. Because constructor injection helps in testing easily.
     * @param userRepository It is used to communicate with database variable.
     * @param cacheManager It is used to store and access cache values.
     */
    public UserRegistrationService(UserRepository userRepository,
                                   CacheManager cacheManager,
                                   PasswordEncoder passwordEncoder,
                                   ResponseHelperMethods responseHelperMethods) {
        this.userRepository = userRepository;
        this.cacheManager = cacheManager;
        this.passwordEncoder = passwordEncoder;
        this.responseHelperMethods = responseHelperMethods;
    }

    /**
     * MethodName - registerUser
     * This method used to register user into application.
     * By verifying the user otp sent through api.
     * @param otpModel POJO class for temporary data holder.
     * @return ResponseEntity It respond in the form of HashMap
     */
    @messageInterface(value = "controller entering into register service")
    @Override
    public ResponseEntity<Map<String,String>> registerUser(OTPModel otpModel) {
        String otp = Objects.requireNonNull(cacheManager.getCache("otpCache"))
                .get(otpModel.getEmail(),String.class);

        if(otp != null && !otp.equals(otpModel.getOtp())) {
            return ResponseEntity.ok(
                    responseHelperMethods.getRegistrationResponseHelper(
                            OTP_NOT_MATCH,
                            "401",
                            LocalDateTime.now()
                    ));
        }
        UserModel userModel = Objects.requireNonNull(cacheManager.getCache("userCache"))
                .get(otpModel.getEmail(), UserModel.class);
        try {
            assert userModel != null;
        }catch(Exception ignored) {
            return ResponseEntity.ok(responseHelperMethods.getRegistrationResponseHelper(
                    SOMETHING_WENT_WRONG,
                    "500",
                    LocalDateTime.now()
            ));
        }
        Map<String,String> result;
        userModel.getUserDetailsModel()
                    .setPassword(passwordEncoder.encode(
                            userModel.getUserDetailsModel().getPassword()
                    ));

        userModel.setCreatedAt(LocalDateTime.now());
        userRepository.save(userModel);
        result = responseHelperMethods.getRegistrationResponseHelper(
                    USER_CREATED_SUCCESSFULLY,
                    "201",
                    userModel.getCreatedAt());

        return ResponseEntity.ok(result);
    }



}
