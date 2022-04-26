package com.example.demo1.student;

import com.example.demo1.student.exception.BadRequestException;
import com.example.demo1.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService
{
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents () {
        return studentRepository.findAll();
    }

    public void addStudent(Student student)
    {
        // check if email is taken
        Boolean existsEmail = studentRepository
                .selectExistsEmail(student.getEmail());
        if ( existsEmail )
        {
                throw new BadRequestException(
                        "Email" + student.getEmail() + "taken"
                );
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId)
    {
        // check if student exists

        if ( !studentRepository.existsById(studentId ) ) {
            throw new StudentNotFoundException(
                    "Student with id" + studentId + "doesn't exist"
            );
        }

        studentRepository.deleteById(studentId);
    }
}
