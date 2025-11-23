package com.example.demo.service;

import com.example.demo.dao.AppDAO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.entity.InstructorDetail;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final AppDAO appDAO;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(AppDAO appDAO, BCryptPasswordEncoder passwordEncoder) {
        this.appDAO = appDAO;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        appDAO.saveUser(user);
    }

    @Override
    public User findUserById(Long id) {
        return appDAO.findUserById(id);
    }

    @Override
    public User findByEmail(String email) {
        return appDAO.findUserByEmail(email);
    }

    @Override
    public boolean checkPassword(User user, String rawPassword) {
        if (user == null) return false;
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }


    @Override
    @Transactional
    public void updateUser(User user) {
        appDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        appDAO.deleteUser(user);
    }

    @Override
    public InstructorDetail findInstructorDetailById(Long id) {
        return appDAO.findInstructorDetailById(id);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(Long id) {
        appDAO.deleteInstructorDetailById(id);
    }

    @Override
    @Transactional
    public void saveCourse(Course course) {
        appDAO.saveCourse(course);
    }

    @Override
    public Course findCourseById(Long id) {
        return appDAO.findCourseById(id);
    }

    @Override
    public Course findCourseWithReviewsById(Long id) {
        return appDAO.findCourseWithReviewsById(id);
    }

    @Override
    public Course findCourseWithStudentsById(Long id) {
        return appDAO.findCourseWithStudentsById(id);
    }

    @Override
    public List<Course> findCoursesByInstructorId(Long instructorId) {
        return appDAO.findCoursesByInstructorId(instructorId);
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        appDAO.updateCourse(course);
    }

    @Override
    @Transactional
    public void deleteCourseById(Long id) {
        appDAO.deleteCourseById(id);
    }

    @Override
    @Transactional
    public void saveReview(Review review) {
        appDAO.saveReview(review);
    }

    @Override
    @Transactional
    public void deleteReviewById(Long id) {
        appDAO.deleteReviewById(id);
    }
    @Override
    public boolean exists(String email) {
        return findByEmail(email) != null;
    }

    @Override
    @Transactional
    public void register(User user) {
        String role = user.getRole().toUpperCase();
        if (!role.equals("STUDENT") && !role.equals("INSTRUCTOR")) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
        user.setRole(role);
        user.setEnabled(true);

        saveUser(user);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }


        Set<SimpleGrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole())
        );

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true, 
                true,
                true, 
                authorities
        );
    }
    @Override
    public User findStudentWithCoursesById(Long id) {
        return appDAO.findStudentWithCoursesById(id);
    }

    @Override
    public User findInstructorWithCoursesTaughtById(Long id) {
        return appDAO.findInstructorWithCoursesTaughtById(id);
    }
}
