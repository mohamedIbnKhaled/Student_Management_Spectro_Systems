package com.Spectro_Systems.Student.Management;

import com.Spectro_Systems.Student.Management.dto.LoginDTo;
import com.Spectro_Systems.Student.Management.dto.RegisterDTO;
import com.Spectro_Systems.Student.Management.exception.UsernameAlreadyExistsException;
import com.Spectro_Systems.Student.Management.model.Role;
import com.Spectro_Systems.Student.Management.model.User;
import com.Spectro_Systems.Student.Management.repo.UserRepo;
import com.Spectro_Systems.Student.Management.service.JwtService;
import com.Spectro_Systems.Student.Management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepo userRepo;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void addUser_ShouldReturnSavedUser_WhenUsernameIsAvailable() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Mohamed");
        registerDTO.setPassword("password123");
        registerDTO.setRole("USER");

        when(userRepo.findByUsername("Mohamed")).thenReturn(null);
        when(encoder.encode("password123")).thenReturn("encodedPassword");

        User savedUser = User.builder()
                .username("Mohamed")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        User result = userService.addUser(registerDTO);

        assertNotNull(result);
        assertEquals("Mohamed", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(Role.USER, result.getRole());
    }

    @Test
    void addUser_ShouldThrow_WhenUsernameAlreadyExists() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Khaled");
        registerDTO.setPassword("pass123");

        when(userRepo.findByUsername("Khaled")).thenReturn(new User());

        assertThrows(UsernameAlreadyExistsException.class,
                () -> userService.addUser(registerDTO));
    }


    @Test
    void verify_ShouldReturnJwtToken_WhenCredentialsAreValid() {
        LoginDTo loginDTo = new LoginDTo();
        loginDTo.setUsername("Essam");
        loginDTo.setPassword("pass123");

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        User user = User.builder()
                .username("Essam")
                .password("encodedPass")
                .role(Role.ADMIN)
                .build();

        when(userRepo.findByUsername("Essam")).thenReturn(user);
        when(jwtService.generateToken("Essam", "ADMIN")).thenReturn("jwt-token-123");

        String token = userService.verify(loginDTo);

        assertEquals("jwt-token-123", token);
    }

    @Test
    void verify_ShouldThrow_WhenCredentialsAreInvalid() {
        LoginDTo loginDTo = new LoginDTo();
        loginDTo.setUsername("7osam");
        loginDTo.setPassword("aaaaaa");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> userService.verify(loginDTo));
    }
}
