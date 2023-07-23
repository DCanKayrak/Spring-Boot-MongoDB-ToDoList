package dev.dcankayrak.nosqldemo.dto.user.request;

import lombok.Data;

@Data
public class UserLoginRequestDto {

    private String email;
    private String password;
}
