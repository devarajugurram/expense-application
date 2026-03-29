package com.exp.backend.controller;

import com.exp.backend.model.OTPModel;
import com.exp.backend.model.UserModel;
import com.exp.backend.template.interfaces.OTPServicePrint;
import com.exp.backend.template.interfaces.UserRegistrationServicePrint;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ClassName - UserController
 * RestController is stereotype component which automatically creates bean in spring application context.
 * RequestMapping defines the default api uri to access this controller.
 * This class is known as RestController class, which takes client request and gives response to client as well.
 * It is a first version of api, so it is indicated as /api/v1/
 */

@RestController
@RequestMapping("/api/v1")
@Validated
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Variables - userRegistrationServicePrint, otpServicePrint
     * Data Types - UserRegistrationServicePrint, OTPServicePrint
     */
    private final UserRegistrationServicePrint userRegistrationServicePrint;
    private final OTPServicePrint otpServicePrint;

    /**
     * ConstructorName -  UserController
     * Constructor automatically inject the object from application context.
     * Here Dependency Injection done by constructor instead of using field and annotation injection. Because constructor injection helps in testing easily.
     *
     * @param userRegistrationServicePrint It is an interface implemented by UserRegistrationService which helps in Abstraction.
     * @param otpServicePrint It is an interface implemented by OYPService which helps in Abstraction.
     */

    public UserController(UserRegistrationServicePrint userRegistrationServicePrint,
                          OTPServicePrint otpServicePrint) {
        this.userRegistrationServicePrint = userRegistrationServicePrint;
        this.otpServicePrint = otpServicePrint;
    }

    /**
     * MethodName - otpVerification
     * This method is used to generate OTP and send to client through Email.
     *
     *
     * @param userModel It is a POJO class used to define the Entity.
     * @return Map It respond with HashMap to the client.
     */

    @PostMapping("/otp/verify")
    public ResponseEntity<Map<String,Object>> otpVerification(@Valid
            @RequestBody UserModel userModel) {
        return otpServicePrint.otpGenerator(userModel);
    }

    /**
     * MethodNAme - userRegistrationAPI
     * This method is used to verify the email of the user for better and safe authentication.
     *
     *
     * @param otpModel It is a POJO class used to define temporary data for otp verification
     * @return Map It respond with HashMap to the client.
     */

    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> userRegistrationAPI(@Valid
            @RequestBody OTPModel otpModel) {
        return userRegistrationServicePrint.registerUser(otpModel);
    }

}
