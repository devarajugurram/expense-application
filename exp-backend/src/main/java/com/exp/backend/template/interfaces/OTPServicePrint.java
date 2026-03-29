package com.exp.backend.template.interfaces;

import com.exp.backend.model.UserModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * InterfaceName - OTPServicePrint
 * This interface used to define the otpGenerator methods.
 * This helps in Abstraction.
 * This method is FunctionalInterface because it only defines single method.
 */
@FunctionalInterface
public interface OTPServicePrint {
    /**
     * MethodName -  otpGenerator
     * This method generates the otp. but it is a definition and implemented by OTPService.
     * @param userModel It holds data in the form of object.
     * @return ResponseEntity method respond in the form of Map.
     */
    ResponseEntity<Map<String,Object>> otpGenerator(UserModel userModel);
}
