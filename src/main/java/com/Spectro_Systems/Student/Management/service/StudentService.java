package com.Spectro_Systems.Student.Management.service;

import com.Spectro_Systems.Student.Management.exception.StudentAlreadyExistsException;
import com.Spectro_Systems.Student.Management.model.Student;
import com.Spectro_Systems.Student.Management.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepo studentRepo;
    @Autowired
    public StudentService (StudentRepo studentRepo){
        this.studentRepo=studentRepo;
    }
    public Student createStudent(Student student){
        if(studentRepo.existsByEmail(student.getEmail())){
            throw  new StudentAlreadyExistsException(
                    "Student with email "+ student.getEmail()+" already exists"
            );
        }
        return studentRepo.save(student);
    }

}
