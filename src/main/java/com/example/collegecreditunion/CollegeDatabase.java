package com.example.collegecreditunion;

import com.example.collegecreditunion.model.Student;
import com.example.collegecreditunion.model.Loan;
import com.example.collegecreditunion.model.Repayment;
import javax.persistence.EntityManager;
import java.util.Arrays;

public class CollegeDatabase {
    public static void main(String[] args) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        
        em.getTransaction().begin();

        Student student = new Student();
        student.setName("Abdul");
        student.setStudentNumber("C21325446");
        student.setPhoneNumber("+353 89 235 7734");
        student.setAddress("12 Boulevard Bellgree, Dublin");
        student.setProgrammeCode("TU914");
        em.persist(student);

        Loan loan = new Loan();
        loan.setDescription("Personal Loan for me");
        loan.setLoanAmount(5000.00);
        loan.setStudent(student);
        em.persist(loan);

        Repayment repayment1 = new Repayment();
        repayment1.setDepositDate("2024-01-15");
        repayment1.setAmount(500.00);
        repayment1.setLoan(loan);

        Repayment repayment2 = new Repayment();
        repayment2.setDepositDate("2024-02-15");
        repayment2.setAmount(500.00);
        repayment2.setLoan(loan);

        em.persist(repayment1);
        em.persist(repayment2);

        em.getTransaction().commit();
        
        em.close();
        CollegeEntityManager.close();
        
        System.out.println("Database connected");
    }
}