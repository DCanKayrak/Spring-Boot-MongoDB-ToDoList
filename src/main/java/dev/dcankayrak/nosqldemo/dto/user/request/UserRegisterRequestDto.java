package dev.dcankayrak.nosqldemo.dto.user.request;

import lombok.Data;

@Data
public class UserRegisterRequestDto {

    private String email;
    private String password;
}
