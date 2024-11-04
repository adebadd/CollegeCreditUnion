package com.example.collegecreditunion;

import com.example.collegecreditunion.dao.RepaymentDAO;
import com.example.collegecreditunion.model.Repayment;
import com.example.collegecreditunion.model.Loan;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/repayments")
public class RepaymentService {

    private RepaymentDAO repaymentDAO = new RepaymentDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Repayment> getAllRepayments() {
        return repaymentDAO.getAllRepayments();
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
        return repaymentDAO.getRepaymentById(id);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createRepayment(@QueryParam("loanId") Long loanId, Repayment repayment) {
        Loan loan = repaymentDAO.findLoanById(loanId);
        if (loan == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Loan doesn't exist")
                    .build();
        }
        
        Double totalRepaid = repaymentDAO.getTotalRepaidAmountByLoanId(loanId);
        double remainingBalance = loan.getLoanAmount() - totalRepaid;

        if (repayment.getAmount() > remainingBalance) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Repayment amount exceeds the loan balance " + remainingBalance)
                    .build();
        }
        
        repayment.setLoan(loan);
        repaymentDAO.persist(repayment);
        return Response.status(Response.Status.CREATED).entity(repayment).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateRepayment(@PathParam("id") Long id, Repayment updatedRepayment) {
        updatedRepayment.setId(id);
        Repayment repayment = repaymentDAO.merge(updatedRepayment);
        return Response.ok(repayment).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRepayment(@PathParam("id") Long id) {
        Repayment repayment = repaymentDAO.getRepaymentById(id);
        repaymentDAO.removeRepayment(repayment);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}