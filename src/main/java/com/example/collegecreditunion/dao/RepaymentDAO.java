package com.example.collegecreditunion.dao;

import com.example.collegecreditunion.model.Repayment;
import com.example.collegecreditunion.model.Loan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class RepaymentDAO {

    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CollegeCreditUnionPU");

    public void persist(Repayment repayment) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(repayment);
        em.getTransaction().commit();
        em.close();
    }

    public void removeRepayment(Repayment repayment) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(repayment));
        em.getTransaction().commit();
        em.close();
    }

    public Repayment merge(Repayment repayment) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Repayment updatedRepayment = em.merge(repayment);
        em.getTransaction().commit();
        em.close();
        return updatedRepayment;
    }

    public List<Repayment> getAllRepayments() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Repayment> repayments = em.createQuery("from Repayment", Repayment.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return repayments;
    }

    public Repayment getRepaymentById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Repayment repayment = em.find(Repayment.class, id);
        em.getTransaction().commit();
        em.close();
        return repayment;
    }

    public Double getTotalRepaidAmountByLoanId(Long loanId) {
        EntityManager em = emf.createEntityManager();
        Double totalRepaid = em.createQuery("SELECT COALESCE(SUM(r.amount), 0) FROM Repayment r WHERE r.loan.id = :loanId", Double.class)
                .setParameter("loanId", loanId)
                .getSingleResult();
        em.close();
        return totalRepaid;
    }
    
    public Loan findLoanById(Long loanId) {
        EntityManager em = emf.createEntityManager();
        Loan loan = em.find(Loan.class, loanId);
        em.close();
        return loan;
    }
}