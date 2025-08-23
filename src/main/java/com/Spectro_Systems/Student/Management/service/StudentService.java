package com.Spectro_Systems.Student.Management.service;

import com.Spectro_Systems.Student.Management.exception.StudentAlreadyExistsException;
import com.Spectro_Systems.Student.Management.exception.StudentNotFoundException;
import com.Spectro_Systems.Student.Management.model.Student;
import com.Spectro_Systems.Student.Management.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Page<Student> getStudents(Pageable pageable){
        return studentRepo.findAll(pageable);
    }
    public Student getStudent(Long id){
        return studentRepo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with the given ID "));
    }
    public Student updateStudent(Long id,Student studentUpdated){
       Student student= studentRepo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with the given ID "));
       student.setFirstName(studentUpdated.getFirstName());
       student.setLastName(studentUpdated.getLastName());
       student.setEmail(studentUpdated.getEmail());
        student.setDateOfBirth(studentUpdated.getDateOfBirth());
       return studentRepo.save(student);
    }

}
