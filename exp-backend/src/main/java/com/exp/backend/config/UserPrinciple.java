package com.exp.backend.config;

import com.exp.backend.aop.MessageInterface;
import com.exp.backend.model.UserModel;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 *
 *
 */
public class UserPrinciple implements UserDetails {

    private final Logger logger = LoggerFactory.getLogger(UserPrinciple.class);

    private final UserModel userModel;

    /**
     *
     *
     * @param userModel
     */
    public UserPrinciple(UserModel userModel) {
        this.userModel = userModel;
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    /**
     *
     * @return
     */

    @Override
    public @NotBlank String getPassword() {
        return userModel.getUserDetailsModel().getPassword();
    }

    /**
     *
     * @return
     */

    @Override
    public String getUsername() {
        return userModel.getEmail();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
