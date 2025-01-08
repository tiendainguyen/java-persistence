package com.demo.javapersistence;

import com.demo.javapersistence.hibernatecore.entity.Student;
import com.demo.javapersistence.hibernatecore.until.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateApplication.class, args);
		// Thêm mới sinh viên
		createStudent("John", "Doe", "john.doe@example.com");

		// Lấy tất cả sinh viên
		readStudents();

		// Cập nhật thông tin sinh viên
		updateStudent(1L, "Jane", "Smith");

		// Xóa sinh viên
		deleteStudent(1L);

		HibernateUtil.shutdown();


	}

	public static void createStudent(String firstName, String lastName, String email) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Student student = new Student(firstName, lastName, email);
			session.save(student);
			transaction.commit();
			System.out.println("Student saved successfully!");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public static void readStudents() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.createQuery("from com.demo.javapersistence.hibernatecore.entity.Student", Student.class)
					.list()
					.forEach(System.out::println);
		}
	}

	public static void updateStudent(Long studentId, String newFirstName, String newLastName) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Student student = session.get(Student.class, studentId);
			if (student != null) {
				student.setFirstName(newFirstName);
				student.setLastName(newLastName);
				session.update(student);
				transaction.commit();
				System.out.println("Student updated successfully!");
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public static void deleteStudent(Long studentId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Student student = session.get(Student.class, studentId);
			if (student != null) {
				session.delete(student);
				transaction.commit();
				System.out.println("Student deleted successfully!");
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

}
