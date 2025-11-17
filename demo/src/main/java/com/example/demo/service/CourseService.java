package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.User;

import java.util.List;

public interface CourseService {
    List<Course> getCoursesByInstructor(User instructor);
    void saveCourse(Course course);
    Course findById(long id);
    void deleteCourseById(long id);
    List<Course> findAllCourses(String keywords);
    boolean enrollStudentToCourse(Long studentId, Long courseId);

}
