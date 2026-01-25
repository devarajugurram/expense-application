package com.exp.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 *
 * ClassName - UserDetailsModel
 * It is a POJO class.
 * It is used to define the Entity.
 * It contains indexes,unique constraints ...etc.
 * Embeddable - It is the part of a table but used to concise code for clear understanding of table fields.
 * Entity - declares the class as an entity.
 * Table - helps to rename and add additional constraints.
 * id - mentions field as primary key.
 * GeneratedValue - type of auto increment, GenerationType. IDENTITY is supported by mysql and postgresql.
 * Batch Processing execution would not support by this type of Generation.
 * Column - used to set custom name for field and add constraints to it.
 * NotNull - would not allow null values for variables.
 */
@Embeddable
public class UserDetailsModel {
    /**
     * Variables - phone, occupation, password, location
     * Data Types - String
     */

    @NotBlank(message = "Phone is missing")
    @Column(name = "user_phone", length = 14,nullable = false)
    private String phone;

    @Column(name = "user_occupation")
    private String occupation;

    @NotBlank(message = "password is missing")
    @Column(name = "user_password",nullable = false)
    @Size(min = 8, max = 20)
    private String password;

    @Column(name = "user_address")
    private String location;

    /**
     * MethodName - getPhone
     * @return String It returns the phone variable data.
     */

    public String getPhone() {
        return phone;
    }

    /**
     * MethodName - setPhone
     * @param phone This method take string type as an argument for the field firstName.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * MethodName - getOccupation
     * @return String It returns the occupation variable data.
     */

    public String getOccupation() {
        return occupation;
    }

    /**
     * MethodName - setOccupation
     * @param occupation This method take string type as an argument for the field firstName.
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * MethodName - getLocation
     * @return String It returns the location variable data.
     */
    public String getLocation() {
        return location;
    }

    /**
     * MethodName - setLocation
     * @param location This method take string type as an argument for the field firstName.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * MethodName - getPassword
     * @return String It returns the password variable data.
     */
    public String getPassword() {
        return password;
    }
    /**
     * MethodName - setpassword
     * @param password This method take string type as an argument for the field firstName.
     */

    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * MethodName - equals
     * @param o   the reference object with which to compare.
     * @return boolean weather current object refers to the same object which is using for comparison.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserDetailsModel that)) return false;
        return Objects.equals(phone, that.phone) &&
                Objects.equals(occupation, that.occupation) &&
                Objects.equals(password, that.password) &&
                Objects.equals(location, that.location);
    }

    /**
     * MethodName - hashCode
     * @return int conversion of class field into single hashcode value
     */
    @Override
    public int hashCode() {
        return Objects.hash(phone, occupation, password, location);
    }

    /**
     * MethodName - toString
     * @return String BluePrint of class along with values if present.
     */
    @Override
    public String toString() {
        return "UserDetailsModel{" +
                "phone='" + phone + '\'' +
                ", occupation='" + occupation + '\'' +
                ", password='" + password + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
