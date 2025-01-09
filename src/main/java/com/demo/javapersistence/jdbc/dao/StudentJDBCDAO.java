package com.demo.javapersistence.jdbc.dao;

import com.demo.javapersistence.entity.Student;
import com.demo.javapersistence.jdbc.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentJDBCDAO {

    // Thêm mới sinh viên
    public static void createStudent(Student student) {
        String sql = "INSERT INTO students (first_name, last_name, email) VALUES (?, ?, ?)";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
    public static List<Student> readStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                students.add(new Student(firstName, lastName, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Cập nhật thông tin sinh viên
    public static void updateStudent(Long id, String newFirstName, String newLastName) {
        String sql = "UPDATE students SET first_name = ?, last_name = ? WHERE id = ?";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
    public static void deleteStudent(Long id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = JdbcUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
