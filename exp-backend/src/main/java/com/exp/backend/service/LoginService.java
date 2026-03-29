package com.exp.backend.service;

import com.exp.backend.aop.MessageInterface;
import com.exp.backend.exceptions.local.UserNotFoundException;
import com.exp.backend.exceptions.local.UsernameOrPasswordIncorrectException;
import com.exp.backend.model.LoginModel;
import com.exp.backend.model.UserModel;
import com.exp.backend.repo.UserRepository;
import com.exp.backend.template.helpers.ResponseHelperMethods;
import com.exp.backend.template.interfaces.LoginServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Map;
import java.util.Optional;

/**
 *
 *
 *
 *
 *
 */

@Service
public class LoginService implements LoginServiceImpl {

    /**
     *
     *
     *
     *
     */
    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResponseHelperMethods responseHelperMethods;
    private final JWTServices jwtServices;

    /**
     * @param userRepository
     * @param passwordEncoder
     * @param responseHelperMethods
     * @param jwtServices
     */
    public LoginService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        ResponseHelperMethods responseHelperMethods,
                        JWTServices jwtServices) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.responseHelperMethods = responseHelperMethods;
        this.jwtServices = jwtServices;
    }

    /**
     * @param loginModel
     * @param response
     * @return
     */
    @MessageInterface
    @Override
    public ResponseEntity<Map<String, Object>> login(LoginModel loginModel,
                                                     HttpServletResponse response)
    throws UserNotFoundException,UsernameOrPasswordIncorrectException {
        Optional<UserModel> userModel = userRepository.findByEmail(loginModel.getEmail());
            /**
             *
             *
             *
             */
            if (userModel.isEmpty()) {
                logger.error("user not found. USER : {}",loginModel.getEmail());
                throw new UserNotFoundException("User Not Found!");
            }

            /**
             *
             *
             *
             *
             */
            if(passwordEncoder.matches(loginModel.getPassword(),userModel.get().getUserDetailsModel().getPassword())) {
                boolean indicator = loginModel.isRemember();
                long calculate = indicator ? 1 : 60 * 24;
                logger.info("generating JWT Token. is user used refresh token {}.  USER : {}",loginModel.isRemember(),loginModel.getEmail());
                String jwtAccessToken = jwtServices.generateSessionToken(loginModel.getEmail(),1000 * 60 * 15);
                String jwtRefreshToken = jwtServices.generateSessionToken(loginModel.getEmail(), (long) 1000 * 60 * calculate * 30);
                /**
                 *
                 *
                 *
                 */
                ResponseCookie accessToken = ResponseCookie.from(
                        "accessToken",
                        jwtAccessToken
                ).httpOnly(false)
                        .secure(false)
                        .path("/")
                        .maxAge(60 * 15)
                        .build();

                ResponseCookie refreshToken = ResponseCookie.from(
                        "refreshToken",
                        jwtRefreshToken
                ).httpOnly(true)
                        .secure(false)
                        .path("/api/refresh-token")
                        .maxAge(60 * calculate * 30)
                        .build();

                ResponseCookie uniqueId = ResponseCookie.from(
                        "AUTHENTICATION_ID",
                        "EX2026"+ userModel.get().getId() +"2103X"
                ).httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(60 * calculate * 30)
                        .build();
                /**
                 *
                 *
                 *
                 *
                 *
                 */
                return ResponseEntity.status(200).
                        header(HttpHeaders.SET_COOKIE,accessToken.toString())
                                .header(HttpHeaders.SET_COOKIE,refreshToken.toString())
                        .header(HttpHeaders.SET_COOKIE,uniqueId.toString())
                                        .body(responseHelperMethods.getLoginResponseHelper(
                        LocalDateTime.now(),
                                                HttpStatus.OK));

            }else
                throw new UsernameOrPasswordIncorrectException("Username or Password is Wrong.");

    }
}
