package com.example.demo.service;

import com.example.demo.dao.AppDAO;
import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final AppDAO appDAO;

    public CourseServiceImpl(AppDAO appDAO) {
        this.appDAO = appDAO;
    }

    @Override
    public List<Course> getCoursesByInstructor(User instructor) {
        if (!"INSTRUCTOR".equals(instructor.getRole())) {
            throw new IllegalArgumentException("User is not an instructor");
        }
        return appDAO.findCoursesByInstructorId(instructor.getId());
    }

    @Override
    @Transactional
    public void saveCourse(Course course) {
        if (course.getId() == 0) {
            appDAO.saveCourse(course);
        } else {
            appDAO.updateCourse(course);
        }
    }

    @Override
    public Course findById(long id) {
        return appDAO.findCourseById(id);
    }

    @Override
    @Transactional
    public void deleteCourseById(long id) {
        appDAO.deleteCourseById(id);
    }

    @Override
    public List<Course> findAllCourses(String keywords) {
        return  appDAO.findAllCourses(keywords);
    }
    @Override
    @Transactional
    public boolean enrollStudentToCourse(Long studentId, Long courseId) {

        User student = appDAO.findUserById(studentId);
        Course course = appDAO.findCourseById(courseId);

        if (student.getCourses().contains(course)) {
            return false;
        }

        student.getCourses().add(course);
        course.getStudents().add(student);

        appDAO.updateUser(student);

        return true;
    }

}
