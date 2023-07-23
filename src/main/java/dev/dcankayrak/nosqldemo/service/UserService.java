package dev.dcankayrak.nosqldemo.service;

import dev.dcankayrak.nosqldemo.dto.user.request.UserLoginRequestDto;
import dev.dcankayrak.nosqldemo.dto.user.request.UserRegisterRequestDto;
import dev.dcankayrak.nosqldemo.dto.user.response.AuthResponse;
import dev.dcankayrak.nosqldemo.entity.User;
import dev.dcankayrak.nosqldemo.entity.enums.Role;
import dev.dcankayrak.nosqldemo.exception.UserException;
import dev.dcankayrak.nosqldemo.repository.UserRepository;
import dev.dcankayrak.nosqldemo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username).orElseThrow(() -> new UserException("Kullanıcı Bulunamadı!"));
    }

    public AuthResponse login(UserLoginRequestDto request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException("Kullanıcı Bulunamadı!"));

        var jwt = this.jwtService.generateToken(user);
        AuthResponse response = AuthResponse
                .builder()
                .token(jwt)
                .build();
        return response;
    }

    @Transactional
    public void register(UserRegisterRequestDto request) {
        User registeredUser = User
                .builder()
                .email(request.getEmail())
                .pass(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        this.userRepository.save(registeredUser);
    }
}
