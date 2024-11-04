package com.example.collegecreditunion;

import com.example.collegecreditunion.model.Repayment;
import com.example.collegecreditunion.model.Loan;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/repayments")
public class RepaymentService {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Repayment> getAllRepayments() {
        EntityManager em = CollegeEntityManager.getEntityManager();
        List<Repayment> repayments = em.createQuery("SELECT r FROM Repayment r", Repayment.class).getResultList();
        em.close();
        return repayments;
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Repayment> getAllRepaymentsJson() {
        return getAllRepayments();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Repayment getRepaymentById(@PathParam("id") Long id) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        Repayment repayment = em.find(Repayment.class, id);
        em.close();
        return repayment;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createRepayment(@QueryParam("loanId") Long loanId, Repayment repayment) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        Loan loan = em.find(Loan.class, loanId);
        if (loan == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Loan doesnt exist")
                    .build();
        }
        Double totalRepaid = em.createQuery("SELECT COALESCE(SUM(r.amount), 0) FROM Repayment r WHERE r.loan.id = :loanId", Double.class)
                .setParameter("loanId", loanId)
                .getSingleResult();

        double remainingBalance = loan.getLoanAmount() - totalRepaid;
        if (repayment.getAmount() > remainingBalance) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Repayment amount exceeds the loan balance " + remainingBalance)
                    .build();
        }
        repayment.setLoan(loan);
        transaction.begin();
        em.persist(repayment);
        transaction.commit();
        em.close();
        
        return Response.status(Response.Status.CREATED).entity(repayment).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateRepayment(@PathParam("id") Long id, Repayment updatedRepayment) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Repayment repayment = em.find(Repayment.class, id);

        transaction.begin();
        repayment.setDepositDate(updatedRepayment.getDepositDate());
        repayment.setAmount(updatedRepayment.getAmount());
        transaction.commit();
        em.close();
        return Response.ok(repayment).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRepayment(@PathParam("id") Long id) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Repayment repayment = em.find(Repayment.class, id);

        transaction.begin();
        em.remove(repayment);
        transaction.commit();
        em.close();
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}