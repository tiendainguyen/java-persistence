package com.demo.javapersistence.hibernatecore.dao;

import com.demo.javapersistence.entity.Student;
import com.demo.javapersistence.hibernatecore.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StudentHibernateDAO {

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
