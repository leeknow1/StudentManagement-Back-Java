package com.example.studentback.resource;

import com.example.studentback.dao.StudentJdbcDAO;
import com.example.studentback.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", exposedHeaders = "*")
public class StudentController {

    private final StudentJdbcDAO studentJdbcDAO;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() throws SQLException {
        List<Student> students = studentJdbcDAO.findAll();
        return ResponseEntity.ok().body(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getOneStudent(@PathVariable Integer id) throws SQLException {
        Optional<Student> student = studentJdbcDAO.findOne(id);
        return student.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws SQLException {
        Student result = studentJdbcDAO.create(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) throws SQLException {
        Student result = studentJdbcDAO.update(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) throws SQLException {
        studentJdbcDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}
