package com.example.collegecreditunion;

import com.example.collegecreditunion.model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
public class StudentService {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Student> getAllStudents() {
        EntityManager em = CollegeEntityManager.getEntityManager();
        List<Student> students = em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        em.close();
        return students;
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getAllStudentsJson() {
        return getAllStudents();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Student getStudentById(@PathParam("id") Long id) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        Student student = em.find(Student.class, id);
        em.close();
        return student;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String createStudent(Student student) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(student);
        transaction.commit();
        em.close();
        return "Student " + student.getName() + " created";
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String updateStudent(@PathParam("id") Long id, Student updatedStudent) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Student student = em.find(Student.class, id);

        transaction.begin();
        student.setName(updatedStudent.getName());
        student.setStudentNumber(updatedStudent.getStudentNumber());
        student.setPhoneNumber(updatedStudent.getPhoneNumber());
        student.setAddress(updatedStudent.getAddress());
        student.setProgrammeCode(updatedStudent.getProgrammeCode());
        transaction.commit();

        em.close();
        return "Student " + student.getName() + " updated";
    }

    @DELETE
    @Path("/{id}")
    public String deleteStudent(@PathParam("id") Long id) {
        EntityManager em = CollegeEntityManager.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Student student = em.find(Student.class, id);

        transaction.begin();
        em.remove(student);
        transaction.commit();

        em.close();
        return "Student " + student.getName() + " deleted";
    }
}