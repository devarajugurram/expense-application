package com.exp.backend.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ClassName - UserModel
 * It is a POJO class.
 * It is used to define the Entity.
 * It contains indexes,unique constraints ...etc.
 * Entity - declares the class as an entity.
 * Table - helps to rename and add additional constraints.
 * id - mentions field as primary key.
 * GeneratedValue - type of auto increment, GenerationType. IDENTITY is supported by mysql and postgresql.
 * Batch Processing execution would not support by this type of Generation.
 * Column - used to set custom name for field and add constraints to it.
 * NotNull - would not allow null values for variables.
 * user_email and user_phone are uniques constraints.
 * idx_user_email is indexes.
 */

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_email","user_phone"})
        },
        indexes = {
                @Index(name = "idx_user_email",columnList = "user_email")
        }
)
public class UserModel {


    /**
     * Variables - id, firstName, lastName, email, userType, createdAt, userDetailsModel
     * Data Types - long, String, UserType, LocalDateTime, UserDetailsModel
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @NotBlank(message = "FirstName is missing")
    @Column(name = "user_firstname",nullable = false)
    private String firstName;

    @NotBlank(message = "LastName is missing")
    @Column(name = "user_lastname",nullable = false)
    private String lastName;

    @Email
    @NotBlank(message = "Email is missing")
    @Column(name = "user_email",nullable = false)
    private String email;

    @Column(name = "user_type")
    private final UserType userType = UserType.USER;

    @Column(name = "user_timestamp")
    private LocalDateTime createdAt;

    @Embedded
    @NotNull(message = "Please fill the all required fields.")
    @Valid
    private UserDetailsModel userDetailsModel;

    /**
     * MethodName - getFirstName
     * @return String It returns the firstName variable data.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * MethodName - setFirstName
     * @param firstName This method take string type as an argument for the field firstName.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * MethodName - getlastName
     * @return String It returns the lastName variable data.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * MethodName - setlastName
     * @param lastName This method take string type as an argument for the field lastName.
     * */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * MethodName - getUserType
     * @return Enum It returns the UserType variable data.
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * MethodName - getCreatedAt
     * @return LocalDateTime It returns the createdAt variable data.
     */

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * MethodName - setCreatedAt
     * @param createdAt This method take LocalDateTime type as an argument for the field createdAt.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    /**
     * MethodName - getUserDetailsModel
     * @return UserDetailsModel It returns the additional details stored in a class UserDetailsModel variable data.
     */
    public UserDetailsModel getUserDetailsModel() {
        return userDetailsModel;
    }

    /**
     * MethodName - setUserDetailsModel
     * @param userDetailsModel This method take UserDetailModel type object as an argument for the field userDetailModel object.
     */
    public void setUserDetailsModel(UserDetailsModel userDetailsModel) {
        this.userDetailsModel = userDetailsModel;
    }

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
     * MethodName - equals
     * @param o   the reference object with which to compare.
     * @return boolean weather current object refers to the same object which is using for comparison.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserModel userModel)) return false;
        return id == userModel.id &&
                Objects.equals(firstName, userModel.firstName) &&
                Objects.equals(lastName, userModel.lastName) &&
                Objects.equals(email, userModel.email) &&
                userType == userModel.userType &&
                Objects.equals(createdAt, userModel.createdAt) &&
                Objects.equals(userDetailsModel, userModel.userDetailsModel);
    }

    /**
     * MethodName - hashCode
     * @return int conversion of class field into single hashcode value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, userType, createdAt, userDetailsModel);
    }

    /**
     * MethodName - toString
     * @return String BluePrint of class along with values if present.
     */
    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", createdAt=" + createdAt +
                ", userDetailsModel=" + userDetailsModel +
                '}';
    }
}
