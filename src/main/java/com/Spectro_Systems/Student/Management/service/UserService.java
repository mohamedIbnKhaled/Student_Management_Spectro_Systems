package com.Spectro_Systems.Student.Management.service;

import com.Spectro_Systems.Student.Management.dto.LoginDTo;
import com.Spectro_Systems.Student.Management.dto.RegisterDTO;
import com.Spectro_Systems.Student.Management.exception.UsernameAlreadyExistsException;
import com.Spectro_Systems.Student.Management.model.Role;
import com.Spectro_Systems.Student.Management.model.User;
import com.Spectro_Systems.Student.Management.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    @Autowired
    public UserService(UserRepo userRepo, AuthenticationManager authenticationManager, PasswordEncoder encoder,JwtService jwtService){
        this.userRepo=userRepo;
        this.authenticationManager=authenticationManager;
        this.encoder=encoder;
        this.jwtService=jwtService;
    }
    public User addUser(RegisterDTO registerDTO){
        if (userRepo.findByUsername(registerDTO.getUsername())!=null){
            throw new UsernameAlreadyExistsException("Username " + registerDTO.getUsername() + " is already taken");
        }
        User user= User.builder()
                .username(registerDTO.getUsername())
                .password(encoder.encode(registerDTO.getPassword()))
                .role(registerDTO.getRole()!=null? Role.valueOf(registerDTO.getRole()):Role.USER)
                .build();

        return userRepo.save(user);
    }
    public String verify(LoginDTo loginDTo){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTo.getUsername(),
                        loginDTo.getPassword()
                )
        );
        if(authentication.isAuthenticated()){
            User user = userRepo.findByUsername(loginDTo.getUsername());
            return jwtService.generateToken(user.getUsername(),user.getRole().name());
        }
        throw new BadCredentialsException("Invalid credentials");
    }
}