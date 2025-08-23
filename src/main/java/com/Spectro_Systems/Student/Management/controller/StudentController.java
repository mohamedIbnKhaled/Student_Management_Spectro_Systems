package com.Spectro_Systems.Student.Management.controller;

import com.Spectro_Systems.Student.Management.model.Student;
import com.Spectro_Systems.Student.Management.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }
    @PostMapping
    public Student createStudent(@RequestBody @Valid Student student){
        return studentService.createStudent(student);
    }
}
