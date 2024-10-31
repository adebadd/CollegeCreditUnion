package com.example.collegecreditunion;

import com.example.collegecreditunion.model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Path("/students")
public class StudentService {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllStudents() {
        EntityManager em = CollegeEntityManager.getEntityManager();
        List<Student> students = em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        em.close();
        return Response.ok(new StudentListWrapper(students)).build();
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudentsJson() {
        EntityManager em = CollegeEntityManager.getEntityManager();
        List<Student> students = em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        em.close();
        return Response.ok(new StudentListWrapper(students)).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentById(@PathParam("id") Long id) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        Student student = em.find(Student.class, id);
        em.close();
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(student).build();
    }

    @GET
    @Path("/{id}/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentByIdJson(@PathParam("id") Long id) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        Student student = em.find(Student.class, id);
        em.close();
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(student).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createStudent(Student student) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(student);
        transaction.commit();
        em.close();
        return Response.status(Response.Status.CREATED).entity(student).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateStudent(@PathParam("id") Long id, Student updatedStudent) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Student student = em.find(Student.class, id);

        if (student == null) {
            em.close();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        transaction.begin();
        student.setName(updatedStudent.getName());
        student.setStudentNumber(updatedStudent.getStudentNumber());
        student.setPhoneNumber(updatedStudent.getPhoneNumber());
        student.setAddress(updatedStudent.getAddress());
        student.setProgrammeCode(updatedStudent.getProgrammeCode());
        transaction.commit();

        em.close();
        return Response.ok(student).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteStudent(@PathParam("id") Long id) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Student student = em.find(Student.class, id);

        if (student == null) {
            em.close();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        transaction.begin();
        em.remove(student);
        transaction.commit();

        em.close();
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}