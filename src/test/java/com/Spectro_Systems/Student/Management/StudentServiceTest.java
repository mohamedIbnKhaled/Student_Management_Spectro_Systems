package com.Spectro_Systems.Student.Management;


import com.Spectro_Systems.Student.Management.exception.StudentAlreadyExistsException;
import com.Spectro_Systems.Student.Management.exception.StudentNotFoundException;
import com.Spectro_Systems.Student.Management.model.Student;
import com.Spectro_Systems.Student.Management.repo.StudentRepo;
import com.Spectro_Systems.Student.Management.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepo studentRepo;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1L);
        student.setFirstName("Mohamed");
        student.setLastName("Khaled");
        student.setEmail("Mohamed@gmail.com");
        student.setDateOfBirth(LocalDate.of(2000, 1, 1));
    }

    @Test
    void createStudent_ShouldSave_WhenEmailNotExists() {
        when(studentRepo.existsByEmail(student.getEmail())).thenReturn(false);
        when(studentRepo.save(student)).thenReturn(student);

        Student saved = studentService.createStudent(student);

        assertNotNull(saved);
        assertEquals("Mohamed@gmail.com", saved.getEmail());
        verify(studentRepo).save(student);
    }

    @Test
    void createStudent_ShouldThrow_WhenEmailExists() {
        when(studentRepo.existsByEmail(student.getEmail())).thenReturn(true);

        assertThrows(StudentAlreadyExistsException.class,
                () -> studentService.createStudent(student));

        verify(studentRepo, never()).save(any());
    }

    @Test
    void getStudents_ShouldReturnPage() {
        List<Student> students = Arrays.asList(student);
        Page<Student> page = new PageImpl<>(students);
        when(studentRepo.findAll(any(Pageable.class))).thenReturn(page);

        Page<Student> result = studentService.getStudents(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        verify(studentRepo).findAll(any(Pageable.class));
    }

    @Test
    void getStudent_ShouldReturn_WhenFound() {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.getStudent(1L);

        assertEquals("Mohamed", result.getFirstName());
    }

    @Test
    void getStudent_ShouldThrow_WhenNotFound() {
        when(studentRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                () -> studentService.getStudent(1L));
    }

    @Test
    void updateStudent_ShouldUpdateAndSave_WhenFound() {
        Student updated = new Student();
        updated.setFirstName("Essam");
        updated.setLastName("Khaled");
        updated.setEmail("Essam@gmail.com");
        updated.setDateOfBirth(LocalDate.of(1999, 5, 15));

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepo.save(any(Student.class))).thenAnswer(inv -> inv.getArgument(0));

        Student result = studentService.updateStudent(1L, updated);

        assertEquals("Essam", result.getFirstName());
        assertEquals("Khaled", result.getLastName());
        assertEquals("Essam@gmail.com", result.getEmail());

        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepo).save(captor.capture());
        assertEquals("Essam", captor.getValue().getFirstName());
    }

    @Test
    void updateStudent_ShouldThrow_WhenNotFound() {
        when(studentRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                () -> studentService.updateStudent(1L, student));
    }

    @Test
    void deleteStudent_ShouldDelete_WhenFound() {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        studentService.deleteStudent(1L);

        verify(studentRepo).delete(student);
    }

    @Test
    void deleteStudent_ShouldThrow_WhenNotFound() {
        when(studentRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                () -> studentService.deleteStudent(1L));

        verify(studentRepo, never()).delete(any());
    }
}
