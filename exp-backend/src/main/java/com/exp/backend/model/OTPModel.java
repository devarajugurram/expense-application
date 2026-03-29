package com.exp.backend.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * ClassName - OTPModel
 * This is a POJO class.
 * It is a temporary class. It is used to store that for further processing.
 * It takes OTP and Email from the user request for verification purpose.
 * It defines one of the characteristics of OOPs i.e., Encapsulation.
 * It provides private fields. Protecting from datatype changing.
 */

public class OTPModel {

    /**
     * Variables - email,otp.
     * Data Types - String
     */
    @Email
    @NotBlank(message = "Something went wrong!")
    private String email;
    @NotBlank(message = "Something went wrong!")
    private String otp;

    /**
     * MethodName - getEmail
     * @return String It returns the email variable data.
     */
    public String getEmail() {
        return email;
    }

    /**
     * MethodName - setEmail
     * @param email This method take string type as an argument for the field email.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * MethodName - getOtp
     * @return String It returns the otp variable data.
     */

    public String getOtp() {
        return otp;
    }

    /**
     * MethodName - setOtp
     * @param otp This method take string type as an argument for the field otp.
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }

    /**
     * MethodName - equals
     * @param o  the reference object with which to compare.
     * @return boolean weather current object refers to the same object which is using for comparison.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OTPModel otpModel)) return false;
        return Objects.equals(email, otpModel.email) && Objects.equals(otp, otpModel.otp);
    }

    /**
     * MethodName - hashCode
     * @return int conversion of class field into single hashcode value
     */

    @Override
    public int hashCode() {
        return Objects.hash(email, otp);
    }

    /**
     * MethodName - toString
     * @return String BluePrint of class along with values if present.
     */

    @Override
    public String toString() {
        return "OTPModel{" +
                "email='" + email + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
