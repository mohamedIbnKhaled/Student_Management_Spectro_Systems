package com.Spectro_Systems.Student.Management.repo;

import com.Spectro_Systems.Student.Management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
