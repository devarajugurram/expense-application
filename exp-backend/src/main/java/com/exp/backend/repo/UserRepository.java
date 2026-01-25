package com.exp.backend.repo;

import com.exp.backend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * InterfaceName - UserRepository
 * It extends JPARepository which indicates the Data JPA for communicating with Database in the form of Objects.
 * Hibernate implements JPA.
 * This interface defines the table and its primary key.
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    /**
     * MethodName - findByEmail.
     * @param email email is the primary key. used to get data or row from the table.
     * @return Optional it carries usermodel object,optional warps the class to prevent NullPointerException.
     */
    Optional<UserModel> findByEmail(String email);
}
