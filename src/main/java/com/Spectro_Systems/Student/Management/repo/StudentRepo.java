package com.Spectro_Systems.Student.Management.repo;

import com.Spectro_Systems.Student.Management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Long> {
    public boolean existsByEmail(String email);
}
