package com.example.collegecreditunion.dao;

import com.example.collegecreditunion.model.Student;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CollegeCreditUnionPU");

    public void persist(Student student) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();
        em.close();
    }

    public void removeStudent(Student student) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(student));
        em.getTransaction().commit();
        em.close();
    }

    public Student merge(Student student) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student updatedStudent = em.merge(student);
        em.getTransaction().commit();
        em.close();
        return updatedStudent;
    }

    public List<Student> getAllStudents() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Student> students = em.createQuery("from Student", Student.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return students;
    }

    public Student getStudentById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student student = em.find(Student.class, id);
        em.getTransaction().commit();
        em.close();
        return student;
    }
}