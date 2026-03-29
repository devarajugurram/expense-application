package com.exp.backend.config;

import com.exp.backend.aop.MessageInterface;
import com.exp.backend.model.UserModel;
import com.exp.backend.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 *
 */
@Component
public class UserDetail implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserDetail.class);

    private final UserRepository userRepository;

    /**
     *
     * @param userRepository
     */
    public UserDetail(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     *
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        /**
         *
         *
         *
         */
        Optional<UserModel> userModel = userRepository.findByEmail(username);
        if(userModel.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found!");
        }
        return new UserPrinciple(userModel.get());
    }
}
