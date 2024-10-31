package com.example.collegecreditunion;
import com.example.collegecreditunion.model.Student;
import javax.persistence.EntityManager;
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
        
        em.getTransaction().commit();
        
        em.close();
        CollegeEntityManager.close();
        
        System.out.println("Database connencted");
    }
}