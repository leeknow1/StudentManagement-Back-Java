package com.example.studentback.dao;

import com.example.studentback.model.Student;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.studentback.dao.DAOUtils.*;

@Component
public class StudentJdbcDAO {

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students.student ORDER BY last_name ASC";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Student student = new Student();
            student.setId(resultSet.getInt("id"));
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            student.setMark(resultSet.getInt("mark"));
            students.add(student);
        }

        return students;
    }

    public Optional<Student> findOne(int id) throws SQLException {
        Student student;
        String sql = "SELECT * FROM students.student WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        student = getStudentFromResultSet(resultSet);
        return Optional.of(student);
    }

    public Student create(Student student) throws SQLException {
        String sql = "INSERT INTO `students`.`student` (`first_name`, `last_name`, `mark`) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, student.getFirstName());
        statement.setString(2, student.getLastName());
        statement.setInt(3, student.getMark());
        statement.executeUpdate();

        String sqlCreatedStudent = "SELECT * FROM students.student ORDER BY id DESC LIMIT 1";
        statement = connection.prepareStatement(sqlCreatedStudent);
        ResultSet resultSet = statement.executeQuery();
        student = getStudentFromResultSet(resultSet);
        return student;
    }

    public Student update(Student student) throws SQLException {
        String sql = "UPDATE `students`.`student` SET `first_name` = ?, `last_name` = ?, `mark` = ? WHERE (`id` = ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, student.getFirstName());
        statement.setString(2, student.getLastName());
        statement.setInt(3, student.getMark());
        statement.setInt(4, student.getId());
        statement.executeUpdate();

        String sqlCreatedStudent = "SELECT * FROM students.student WHERE id = ?";
        statement = connection.prepareStatement(sqlCreatedStudent);
        statement.setInt(1, student.getId());
        ResultSet resultSet = statement.executeQuery();
        student = getStudentFromResultSet(resultSet);
        return student;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM students.student WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    private Student getStudentFromResultSet(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        if (resultSet.next()) {
            student.setId(resultSet.getInt("id"));
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            student.setMark(resultSet.getInt("mark"));
        }
        return student;
    }
}
