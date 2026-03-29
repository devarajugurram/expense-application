package com.exp.backend.service;

import com.exp.backend.aop.MessageInterface;
import com.exp.backend.exceptions.local.OTPSentUnsuccessfulException;
import com.exp.backend.exceptions.local.UserAlreadyExistException;
import com.exp.backend.model.UserModel;
import com.exp.backend.repo.UserRepository;
import com.exp.backend.template.helpers.ResponseHelperMethods;
import com.exp.backend.template.interfaces.OTPServicePrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static com.exp.backend.template.strings.TemplateStrings.USER_ALREADY_EXIST;
import static com.exp.backend.template.strings.TemplateStrings.EMAIL_NOT_FOUND;
import static com.exp.backend.template.strings.TemplateStrings.OTP_SENT_SUCCESSFULLY;
import static com.exp.backend.template.strings.TemplateStrings.OTP_EMAIL_PREFIX;
import static com.exp.backend.template.strings.TemplateStrings.OTP_EMAIL_SUFFIX;

/**
 * ClassName - OTPService
 * It implements OTPServicePrint interface.
 * This class defines otpGenerator and userExistOrNot methods
 * It is a stereotype component, bean is controlled by spring or application context.
 * This class takes data from client in the form of UserModel and generate an OTP send to client through Email.
 * UserModel and OTP is stored in Cache buckets in their respective buckets.
 */

@Service
public class OTPService implements OTPServicePrint {

    private final Logger logger = LoggerFactory.getLogger(OTPService.class);
    /**
     * Variables - cacheManager, javaMailSender, responseHelperMethods, userRepository
     * Data Types - CacheManager, JavaMailSender, ResponseHelperMethods, UserRepository
     */
    private final CacheManager cacheManager;
    private final JavaMailSender javaMailSender;
    private final ResponseHelperMethods responseHelperMethods;
    private final UserRepository userRepository;

    /**
     * ConstructorName - OTPService
     * Constructor automatically inject the object from application context.
     * Here Dependency Injection done by constructor instead of using field and annotation injection. Because constructor injection helps in testing easily.
     * @param cacheManager External Library Object, It provides in memory storage as a cache.
     * @param javaMailSender External Library Object, It helps to send communication info like otp ...etc. sent through Email.
     * @param responseHelperMethods It is a service providing class, here we use method called getRegistrationResponseHelper for client response.
     * @param userRepository It is repository used to communicate with database.
     */
    public OTPService(CacheManager cacheManager,
                      JavaMailSender javaMailSender,
                      ResponseHelperMethods responseHelperMethods,
                      UserRepository userRepository) {
        this.cacheManager = cacheManager;
        this.javaMailSender = javaMailSender;
        this.responseHelperMethods = responseHelperMethods;
        this.userRepository = userRepository;
    }

    /**
     * MethodName - otpGenerator
     * This method generates otp and stores data in tht cache for authentication and user registration.
     * @param userModel It is POJO class for holding data in object form.
     * @return ResponseEntity It contains response in the form of HashMap.
     *
     */
    @MessageInterface
    @Override
    public ResponseEntity<Map<String,Object>> otpGenerator(UserModel userModel)
            throws UserAlreadyExistException,OTPSentUnsuccessfulException {

        String email = userModel.getEmail();
        if(userExistOrNot(email)) {
            logger.error("user already exist with email. USER : {}", userModel.getEmail());
            throw new UserAlreadyExistException(USER_ALREADY_EXIST);
        }

        /**
         *
         *
         *
         *
         */

        Objects.requireNonNull(cacheManager.getCache("userCache")).put(email,userModel);
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        Objects.requireNonNull(cacheManager.getCache("otpCache")).put(email,otp);

        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("OTP Verification");
            simpleMailMessage.setText(
                    OTP_EMAIL_PREFIX + otp + OTP_EMAIL_SUFFIX
            );

            javaMailSender.send(simpleMailMessage);
            logger.info("mail Sent to user for OTP verification is successful. USER : {}",userModel.getEmail());


            /**
             *
             *
             *
             */

        }catch(Exception exception) {

            logger.error("OTP is not sent user due to internal issue. USER : {}",userModel.getEmail());
            throw new OTPSentUnsuccessfulException(EMAIL_NOT_FOUND);
        }


        /**
         *
         *
         *
         *
         */

        return ResponseEntity.ok(responseHelperMethods
                .getRegistrationResponseHelper(
                        OTP_SENT_SUCCESSFULLY,
                        HttpStatus.OK,
                        LocalDateTime.now()
                ));
    }

    /**
     * MethodName - userExistOrNot
     * This method helper to know whether user already exist or not in the database.
     * @param email It takes email as parameter and get whether user exist in database or not.
     * @return boolean user exist or not
     */

    private boolean userExistOrNot(String email) {
        Optional<UserModel> userModel = userRepository.findByEmail(email);
        logger.info("user Exist or Not : {}",userModel.isPresent());
        return userModel.isPresent();
    }

}
