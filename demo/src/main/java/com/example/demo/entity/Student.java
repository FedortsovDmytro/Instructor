package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    int id;
    @Column(name="first_name")
    String firstName;
    @Column(name="last_name")
    String lastName;
    @Column(name="email")
    String email;

    @ManyToMany(fetch=FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH},mappedBy = "students")
    private List<Course> courses;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;

    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Student() {}

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        if(courses==null){
            courses=new ArrayList<>();
        }
        this.courses.add(course);
course.addStudent(this);
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
