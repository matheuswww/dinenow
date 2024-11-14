package com.github.matheuswwwp.dinenow.conf.exception.customException.user;

public class EmailAlreadyRegistredException extends Exception {
    public EmailAlreadyRegistredException() {
        super("Email already exists");
    }
}
