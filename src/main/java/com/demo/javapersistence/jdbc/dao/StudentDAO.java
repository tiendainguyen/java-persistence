package com.demo.javapersistence.jdbc.dao;

import com.demo.javapersistence.jdbc.entity.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/hibernate_demo";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    // Tạo kết nối đến database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Thêm mới sinh viên
    public void createStudent(Student student) {
        String sql = "INSERT INTO students (first_name, last_name, email) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.executeUpdate();
            System.out.println("Student created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách sinh viên
    public List<Student> readStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                students.add(new Student(id, firstName, lastName, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Cập nhật thông tin sinh viên
    public void updateStudent(Long id, String newFirstName, String newLastName) {
        String sql = "UPDATE students SET first_name = ?, last_name = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newFirstName);
            pstmt.setString(2, newLastName);
            pstmt.setLong(3, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa sinh viên
    public void deleteStudent(Long id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
