package com.kangjun.common.mapstruct;

import lombok.Data;

// DTO：UserRegisterReq
@Data
public class UserRegisterReq {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
}