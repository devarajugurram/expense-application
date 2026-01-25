package com.exp.backend.model;

/**
 * EnumeratorName - UserType
 * It is an Enumerator which defines the user type and helps in user control system of application.
 * ADMIN who can access everything of application like analysis,users,activities.
 * USER helps to give limited privileges for the user.
 */

public enum UserType {

    /**
     * Constants - ADMIN, USER
     */
    ADMIN,
    USER
}
