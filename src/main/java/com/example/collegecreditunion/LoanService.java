package com.example.collegecreditunion;

import com.example.collegecreditunion.model.Loan;
import com.example.collegecreditunion.model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/loans")
public class LoanService {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Loan> getAllLoans() {
        EntityManager em = CollegeEntityManager.getEntityManager();
        List<Loan> loans = em.createQuery("SELECT l FROM Loan l", Loan.class).getResultList();
        em.close();
        return loans;
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Loan> getAllLoansJson() {
        return getAllLoans();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Loan getLoanById(@PathParam("id") Long id) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        Loan loan = em.find(Loan.class, id);
        em.close();
        return loan;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createLoan(@QueryParam("studentId") Long studentId, Loan loan) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Student student = em.find(Student.class, studentId);
        
        if (student == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Student not found.")
                    .build();
        }
        Long existingLoanCount = em.createQuery("SELECT COUNT(l) FROM Loan l WHERE l.student.id = :studentId", Long.class)
                .setParameter("studentId", studentId)
                .getSingleResult();
        if (existingLoanCount > 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("You already have a loan, you can only take 1 out at a time.")
                    .build();
        }
        loan.setStudent(student);
        transaction.begin();
        em.persist(loan);
        transaction.commit();
        em.close();
        return Response.status(Response.Status.CREATED).entity(loan).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateLoan(@PathParam("id") Long id, Loan updatedLoan) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Loan loan = em.find(Loan.class, id);

        transaction.begin();
        loan.setDescription(updatedLoan.getDescription());
        loan.setLoanAmount(updatedLoan.getLoanAmount());
        transaction.commit();
        em.close();
        return Response.ok(loan).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteLoan(@PathParam("id") Long id) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Loan loan = em.find(Loan.class, id);

        transaction.begin();
        em.remove(loan);
        transaction.commit();
        em.close();
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}