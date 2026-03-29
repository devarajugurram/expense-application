package com.exp.backend.template.interfaces;

import com.exp.backend.model.OTPModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * InterfaceName - UserRegistrationServicePrint
 * This interface used to define the registerUser methods.
 * This helps in Abstraction.
 * This method is FunctionalInterface because it only defines single method.
 */
@FunctionalInterface
public interface UserRegistrationServicePrint {
    /**
     * MethodName -  registerUser
     * This method verifies the otp. but it is a definition and implemented by UserRegistrationService.
     * @param otpModel It holds data in the form of object.
     * @return ResponseEntity method respond in the form of Map.
     */
    ResponseEntity<Map<String,Object>> registerUser(OTPModel otpModel);
}
