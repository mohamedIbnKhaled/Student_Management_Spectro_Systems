package com.Spectro_Systems.Student.Management.controller;


import com.Spectro_Systems.Student.Management.dto.LoginDTo;
import com.Spectro_Systems.Student.Management.dto.RegisterDTO;
import com.Spectro_Systems.Student.Management.model.User;
import com.Spectro_Systems.Student.Management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody @Valid RegisterDTO user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginDTo user) {
        return userService.verify(user);
    }
}
