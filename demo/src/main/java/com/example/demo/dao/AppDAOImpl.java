package com.example.demo.dao;

import com.example.demo.entity.Course;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {

    private final EntityManager entityManager;

    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(
                "from User where email = :email and enabled = true", User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        // If user is instructor, detach courses
        if ("INSTRUCTOR".equals(user.getRole()) && user.getCourses() != null) {
            user.getCourses().forEach(course -> course.setInstructor(null));
        }
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    @Override
    public InstructorDetail findInstructorDetailById(Long id) {
        return entityManager.find(InstructorDetail.class, id);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(Long id) {
        InstructorDetail detail = entityManager.find(InstructorDetail.class, id);
        if (detail != null && detail.getInstructor() != null) {
            detail.getInstructor().setInstructorDetail(null);
        }
        entityManager.remove(detail);
    }

    @Override
    @Transactional
    public void saveCourse(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findCourseById(Long id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    public Course findCourseWithReviewsById(Long id) {
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c left join fetch c.reviews where c.id = :id", Course.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Course findCourseWithStudentsById(Long id) {
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c left join fetch c.students where c.id = :id", Course.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Course> findCoursesByInstructorId(Long instructorId) {
        TypedQuery<Course> query = entityManager.createQuery(
                "from Course where instructor.id = :instructorId", Course.class);
        query.setParameter("instructorId", instructorId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        entityManager.merge(course);
    }

    @Override
    @Transactional
    public void deleteCourseById(Long id) {
        Course course = entityManager.find(Course.class, id);
        if (course != null) {
            // Remove course from students
            if (course.getStudents() != null) {
                course.getStudents().forEach(s -> s.getCourses().remove(course));
            }
            entityManager.remove(course);
        }
    }

    @Override
    @Transactional
    public void saveReview(Review review) {
        entityManager.persist(review);
    }

    @Override
    @Transactional
    public void deleteReviewById(Long id) {
        Review review = entityManager.find(Review.class, id);
        if (review != null) {
            entityManager.remove(review);
        }
    }

    @Override
    public List<User> findAllStudents() {
        String jpql = "SELECT u FROM User u WHERE u.role = :role";
        return entityManager.createQuery(jpql, User.class)
                .setParameter("role", "STUDENT")
                .getResultList();
    }


    @Override
    public List<User> findAllInstructors() {
        String jpql = "SELECT u FROM User u WHERE u.role = :role";
        return entityManager.createQuery(jpql, User.class)
                .setParameter("role", "INSTRUCTOR")
                .getResultList();
    }

    @Override
    public User findStudentWithCoursesById(Long id) {
        TypedQuery<User> q = entityManager.createQuery(
                """
                SELECT u FROM User u
                LEFT JOIN FETCH u.courses c
                WHERE u.id = :id
                """,
                User.class);
        q.setParameter("id", id);
        try {
            return q.getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }
    @Override
    public User findInstructorWithCoursesTaughtById(Long id) {
        TypedQuery<User> q = entityManager.createQuery(
                """
                SELECT u FROM User u
                LEFT JOIN FETCH u.coursesTaught ct
                WHERE u.id = :id
                """,
                User.class);
        q.setParameter("id", id);
        try {
            return q.getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }
    @Override
    public List<Course> findAllCourses(String keywords) {

        if (keywords == null || keywords.isBlank()) {
            return entityManager.createQuery(
                    "SELECT c FROM Course c", Course.class
            ).getResultList();
        }

        return entityManager.createQuery(
                        "SELECT c FROM Course c WHERE LOWER(c.title) LIKE LOWER(:kw)"
                        , Course.class
                )
                .setParameter("kw", "%" + keywords + "%")
                .getResultList();
    }
}
