package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.entity.InstructorDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void saveUser(User user);
    User findUserById(Long id);
    void updateUser(User user);
    void deleteUser(User user);
    User findByEmail(String email);
    boolean checkPassword(User user, String rawPassword);

    InstructorDetail findInstructorDetailById(Long id);
    void deleteInstructorDetailById(Long id);
    User findStudentWithCoursesById(Long id);
    User findInstructorWithCoursesTaughtById(Long id);
    void saveCourse(Course course);
    Course findCourseById(Long id);
    Course findCourseWithReviewsById(Long id);
    Course findCourseWithStudentsById(Long id);
    List<Course> findCoursesByInstructorId(Long instructorId);
    void updateCourse(Course course);
    void deleteCourseById(Long id);
    boolean exists(String email);
    void register(User user);

    void saveReview(Review review);
    void deleteReviewById(Long id);
}
