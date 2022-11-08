package com.huce.quanlysinhvien.error;

public class LoginAlreadyUsedException extends com.huce.quanlysinhvien.error.BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
        super(com.huce.quanlysinhvien.error.ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Login name already used!", "userManagement", "userexists");
    }
}
