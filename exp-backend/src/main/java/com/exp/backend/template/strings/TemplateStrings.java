package com.exp.backend.template.strings;

import org.springframework.beans.factory.annotation.Value;

/**
 * ClassName - TemplateStrings
 * This class contains the data templates.
 * templates includes constant values, like creation,success,failure ...etc. messages.
 */
public class TemplateStrings {
    /**
     * Variables - USER_CREATED_SUCCESSFULLY, OTP_NOT_MATCH, USER_ALREADY_EXIST, EMAIL_NOT_FOUND, OTP_SENT_SUCCESSFULLY, SOMETHING_WENT_WRONG, OTP_EMAIL_PREFIX, OTP_EMAIL_SUFFIX
     * Data Types - String
     */
    public static final String USER_CREATED_SUCCESSFULLY = "User Created Successfully";
    public static final String OTP_NOT_MATCH = "OTP did not match";
    public static final String USER_ALREADY_EXIST = "User Already Exist";
    public static final String EMAIL_NOT_FOUND = "Email not found!";
    public static final String OTP_SENT_SUCCESSFULLY = "Otp sent successfully!";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String OTP_EMAIL_PREFIX = "Hello,\n\nYour One-Time Password (OTP) for verifying your account is:\n\n";
    public static final String OTP_EMAIL_SUFFIX = "\n\n" +
            "Please enter this OTP to continue with your application. This code is valid for a limited time.\n\n" +
            "If you did not request this, please ignore this email.\n\n" +
            "Thanks & Regards,\nExpense Team";


    public static final String EXPENSE_CREATED = "Expense Created Successfully.";
    public static final String EXPENSE_NOT_CREATED = "Expense Not Created.";

    public static final String EXPENSE_NOT_AVAILABLE = "Expense Not Available";
    public static final String EXPENSE_UPDATED_SUCCESSFULLY =  "Expense Updated Successfully";
    public static final String EXPENSE_DELETED_SUCCESSFULLY = "Expense Deleted Successfully";
}
