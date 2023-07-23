package dev.dcankayrak.nosqldemo.controller;

import dev.dcankayrak.nosqldemo.dto.user.request.UserLoginRequestDto;
import dev.dcankayrak.nosqldemo.dto.user.request.UserRegisterRequestDto;
import dev.dcankayrak.nosqldemo.dto.user.response.AuthResponse;
import dev.dcankayrak.nosqldemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginRequestDto request){

        return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> register(@RequestBody UserRegisterRequestDto request) {
        userService.register(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
