package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    private String role; // "INSTRUCTOR" or "STUDENT"

    private boolean enabled = true;

    @OneToOne(mappedBy = "instructor", cascade = CascadeType.ALL)
    private InstructorDetail instructorDetail;

    @OneToMany(mappedBy = "instructor")
    private List<Course> coursesTaught = new ArrayList<>();

    @ManyToMany(mappedBy = "students")
    private List<Course> courses = new ArrayList<>();
    @OneToMany(mappedBy = "student")
    private List<Review> reviews = new ArrayList<>();

    public User() {}

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String role;
        private InstructorDetail instructorDetail;

        public Builder firstName(String v) { this.firstName = v; return this; }
        public Builder lastName(String v) { this.lastName = v; return this; }
        public Builder email(String v) { this.email = v; return this; }
        public Builder password(String v) { this.password = v; return this; }
        public Builder role(String v) { this.role = v; return this; }
        public Builder instructorDetail(InstructorDetail v) { this.instructorDetail = v; return this; }

        public User build() {
            User u = new User();
            u.firstName = firstName;
            u.lastName = lastName;
            u.email = email;
            u.password = password;
            u.role = role;

            if ("INSTRUCTOR".equals(role) && instructorDetail != null) {
                u.instructorDetail = instructorDetail;
                instructorDetail.setInstructor(u);
            }

            return u;
        }
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public InstructorDetail getInstructorDetail() {
        return instructorDetail;
    }

    public void setInstructorDetail(InstructorDetail instructorDetail) {
        this.instructorDetail = instructorDetail;
    }

    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }

    public void setCoursesTaught(List<Course> coursesTaught) {
        this.coursesTaught = coursesTaught;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> enrolledCourses) {
        this.courses = enrolledCourses;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
