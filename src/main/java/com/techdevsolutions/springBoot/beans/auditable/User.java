package com.techdevsolutions.springBoot.beans.auditable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User extends Auditable {
    @NotEmpty
    private String username = "";
    private String firstName = "";
    private String lastName = "";
    private String middleName = "";
    @NotEmpty
    private String email = "";

    public User() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public User setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }
}
