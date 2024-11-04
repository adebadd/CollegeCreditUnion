package com.example.collegecreditunion;

import com.example.collegecreditunion.dao.LoanDAO;
import com.example.collegecreditunion.model.Loan;
import com.example.collegecreditunion.model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/loans")
public class LoanService {

    private LoanDAO loanDAO = new LoanDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Loan> getAllLoans() {
        return loanDAO.getAllLoans();
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
        return loanDAO.getLoanById(id);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createLoan(@QueryParam("studentId") Long studentId, Loan loan) {
        Student student = loanDAO.findStudentById(studentId);

        if (student == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Student not found.")
                    .build();
        }

        Long existingLoanCount = loanDAO.countLoansByStudentId(studentId);
        if (existingLoanCount > 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("You already have a loan, you can only take 1 out at a time.")
                    .build();
        }

        loan.setStudent(student);
        loanDAO.persist(loan);
        return Response.status(Response.Status.CREATED).entity(loan).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateLoan(@PathParam("id") Long id, Loan updatedLoan) {
        updatedLoan.setId(id);
        Loan loan = loanDAO.merge(updatedLoan);
        return Response.ok(loan).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteLoan(@PathParam("id") Long id) {
        Loan loan = loanDAO.getLoanById(id);
        loanDAO.removeLoan(loan);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}