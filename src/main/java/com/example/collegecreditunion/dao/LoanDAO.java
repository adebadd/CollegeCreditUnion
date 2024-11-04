package com.example.collegecreditunion.dao;

import com.example.collegecreditunion.model.Loan;
import com.example.collegecreditunion.model.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class LoanDAO {

    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CollegeCreditUnionPU");

    public void persist(Loan loan) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(loan);
        em.getTransaction().commit();
        em.close();
    }

    public void removeLoan(Loan loan) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(loan));
        em.getTransaction().commit();
        em.close();
    }

    public Loan merge(Loan loan) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Loan updatedLoan = em.merge(loan);
        em.getTransaction().commit();
        em.close();
        return updatedLoan;
    }

    public List<Loan> getAllLoans() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Loan> loans = em.createQuery("from Loan", Loan.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return loans;
    }

    public Loan getLoanById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Loan loan = em.find(Loan.class, id);
        em.getTransaction().commit();
        em.close();
        return loan;
    }

    public Long countLoansByStudentId(Long studentId) {
        EntityManager em = emf.createEntityManager();
        Long count = em.createQuery("SELECT COUNT(l) FROM Loan l WHERE l.student.id = :studentId", Long.class)
                .setParameter("studentId", studentId)
                .getSingleResult();
        em.close();
        return count;
    }

    public Student findStudentById(Long studentId) {
        EntityManager em = emf.createEntityManager();
        Student student = em.find(Student.class, studentId);
        em.close();
        return student;
    }
}