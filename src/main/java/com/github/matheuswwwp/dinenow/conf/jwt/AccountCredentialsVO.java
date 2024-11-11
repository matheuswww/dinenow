package com.github.matheuswwwp.dinenow.conf.jwt;

import java.io.Serial;
import java.io.Serializable;

public class AccountCredentialsVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;

    public AccountCredentialsVO(String email, String password) {
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
