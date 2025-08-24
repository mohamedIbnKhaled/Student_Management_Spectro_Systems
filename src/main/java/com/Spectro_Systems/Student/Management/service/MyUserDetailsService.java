package com.Spectro_Systems.Student.Management.service;

import com.Spectro_Systems.Student.Management.model.User;
import com.Spectro_Systems.Student.Management.model.UserPrincipal;
import com.Spectro_Systems.Student.Management.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    @Autowired
    public MyUserDetailsService(UserRepo userRepo){
        this.userRepo=userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepo.findByUsername(username);
        if(user==null){
            System.out.println("User not Found");
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(user);
    }
}
