package com.example.collegecreditunion;
import javax.xml.bind.annotation.XmlRootElement;

import com.example.collegecreditunion.model.Student;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlRootElement(name = "students")
public class StudentListWrapper {
    private List<Student> students;

    public StudentListWrapper() {}

    public StudentListWrapper(List<Student> students) {
        this.students = students;
    }

    @XmlElement(name = "student")
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}