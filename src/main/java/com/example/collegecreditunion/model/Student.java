package com.example.collegecreditunion.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.*;
import java.util.List;

@Entity
@XmlRootElement
public class Student {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
   private String studentNumber;
   private String phoneNumber;
   private String address;
   private String programmeCode;

   @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   private List<Loan> loans;

   public Student() {}

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getStudentNumber() {
      return studentNumber;
   }

   public void setStudentNumber(String studentNumber) {
      this.studentNumber = studentNumber;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getProgrammeCode() {
      return programmeCode;
   }

   public void setProgrammeCode(String programmeCode) {
      this.programmeCode = programmeCode;
   }

   @XmlTransient
   public List<Loan> getLoans() {
      return loans;
   }

   public void setLoans(List<Loan> loans) {
      this.loans = loans;
   }
}