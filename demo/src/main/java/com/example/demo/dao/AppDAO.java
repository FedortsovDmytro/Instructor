package com.example.demo.dao;

import com.example.demo.entity.Course;
import com.example.demo.entity.Instructor;
import com.example.demo.entity.InstructorDetail;
import com.example.demo.entity.Student;

import java.util.List;

public interface AppDAO {

    void save(Instructor theInstructor);

    Instructor findInstructorById(int theId);

    void deleteInstructorById(int theId);

    InstructorDetail findInstructorDetailById(int theId);

    void deleteInstructorDetailById(int theId);

    List<Course> findCoursesByInstructorId(int theId);

    Instructor findInstructorByIdJoinFetch(int theId);
    void update (Instructor theInstructor);
    void update(Course theCourse);
    Course findCourseById(int theId);
     void deleteCourseById(int theId);
     void saveCourse(Course theCourse);
     Course findCourseAndReviewsById(int Id);
     Course findCourseAndStudentsById(int Id);
     Student findStudentANdCourseById(int Id);
     void updateStudent(Student theStudent);
void deleteStudent(int id);
}