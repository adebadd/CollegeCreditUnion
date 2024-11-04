package com.example.collegecreditunion;

import com.example.collegecreditunion.dao.StudentDAO;
import com.example.collegecreditunion.model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
public class StudentService {

    private StudentDAO studentDAO = new StudentDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
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
        return studentDAO.getStudentById(id);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String createStudent(Student student) {
        studentDAO.persist(student);
        return "Student added " + student.getName();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Student updateStudent(@PathParam("id") Long id, Student updatedStudent) {
        updatedStudent.setId(id);
        return studentDAO.merge(updatedStudent);
    }

    @DELETE
    @Path("/{id}")
    public String deleteStudent(@PathParam("id") Long id) {
        Student student = studentDAO.getStudentById(id);
        studentDAO.removeStudent(student);
        return "Student " + student.getName() + " deleted";
    }
}