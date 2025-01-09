package com.demo.javapersistence;

import com.demo.javapersistence.hibernatecore.dao.StudentHibernateDAO;
import com.demo.javapersistence.entity.Student;
import com.demo.javapersistence.hibernatecore.util.HibernateUtil;
import com.demo.javapersistence.jdbc.dao.StudentJDBCDAO;
import com.demo.javapersistence.jdbc.util.JdbcUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;

@SpringBootApplication
public class HibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateApplication.class, args);
		// Thêm mới sinh viên
		StudentHibernateDAO.createStudent("John", "Doe", "john.doe@example.com");
//
//		// Lấy tất cả sinh viên
//		StudentHibernateDAO.readStudents();
//
//		// Cập nhật thông tin sinh viên
//		StudentHibernateDAO.updateStudent(1L, "Jane", "Smith");
//
//		// Xóa sinh viên
//		StudentHibernateDAO.deleteStudent(1L);
//
//		HibernateUtil.shutdown();
		System.out.println("Checking connection...");
		while (true) {
			try {
				System.out.println("Is connection closed? " + HibernateUtil.getSessionFactory().isClosed()); // Kiểm tra trên cùng một connection
				Thread.sleep(1000);  // Thêm khoảng dừng để tránh chạy vô hạn quá nhanh
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

}
