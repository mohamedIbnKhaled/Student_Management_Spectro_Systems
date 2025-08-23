package com.Spectro_Systems.Student.Management.controller;

import com.Spectro_Systems.Student.Management.model.Student;
import com.Spectro_Systems.Student.Management.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public Page<Student>getStudents(Pageable pageable){
            return studentService.getStudents(pageable);
    }
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id){
        return studentService.getStudent(id);
    }
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id,@RequestBody @Valid Student student){
        return  studentService.updateStudent(id,student);
    }
}
