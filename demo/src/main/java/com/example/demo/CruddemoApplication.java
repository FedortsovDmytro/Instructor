package com.example.demo;

import com.example.demo.dao.AppDAO;
import com.example.demo.entity.*;
import jakarta.persistence.Table;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CruddemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AppDAO appDAO) {

        return runner -> {
            // createInstructor(appDAO);

            // findInstructor(appDAO);

           //  deleteInstructor(appDAO);

            // findInstructorDetail(appDAO);

            // deleteInstructorDetail(appDAO);

            // createInstructorWithCourses(appDAO);
            //createInstructorWithCourses(appDAO);

            // findInstructorWithCourses(appDAO);

            // findCoursesForInstructor(appDAO);

            //findInstructorWithCoursesJoinFetch(appDAO);
            //updateInstructor(appDAO);
           // updateCourse(appDAO);
            //deleteCourse(appDAO);
            //createCourseAndRewiews(appDAO);
       // retrieveCourseandReviews(appDAO);
      //  deleteCourseAndReviews(appDAO);
           // createCourseAndStudents(appDAO);
            //findCourseAndStudents(appDAO);
           // findStudentAndCourseById(appDAO);
            //addMoreCoursesForStudents(appDAO);
           // deleteCourseS(appDAO);
            deleteStudent(appDAO);
        };
    }

    private void deleteStudent(AppDAO appDAO) {
        int id=1;
        appDAO.deleteStudent(id);
    }

    private void deleteCourseS(AppDAO appDAO) {
        int theId=10;
        System.out.println(theId);
        appDAO.deleteCourseById(theId);
        System.out.println("Done");
    }

    private void addMoreCoursesForStudents(AppDAO appDAO) {
        int theId = 1;
        Student theStudent = appDAO.findStudentANdCourseById(theId);
        Course theCourse = new Course("Rubik's Cube");
        Course theCourse2 = new Course("Game-Developer");
        theStudent.addCourse(theCourse);
        theStudent.addCourse(theCourse2);
        appDAO.updateStudent(theStudent);

    }

    private void findStudentAndCourseById(AppDAO appDAO) {
        int id = 2;
        Student student = appDAO.findStudentANdCourseById(id);
        System.out.println(student);
        System.out.println(" Attempt this courses: "+student.getCourses());

    }

    private void findCourseAndStudents(AppDAO appDAO) {
        int id=10;
        Course course = appDAO.findCourseAndStudentsById(id);
        System.out.println("the course :"+course);
        System.out.println("attempted by this students: "+course.getStudents());
    }

    private void createCourseAndStudents(AppDAO appDAO) {
        Course course = new Course("dsgjdgd");
        course.addStudent(new Student("John","WEak","gdiweg@o.com"));
        course.addStudent(new Student("John","WEak","gdiweg@o.com"));
        appDAO.saveCourse(course);
    }

    private void deleteCourseAndReviews(AppDAO appDAO) {
        int theId = 10;
        System.out.println("Delete Course and Reviews id:"+theId);
        appDAO.deleteCourseById(theId);
        System.out.println("Done");
    }

    private void retrieveCourseandReviews(AppDAO appDAO) {
        int id=10;
        Course course = appDAO.findCourseAndReviewsById(id);
        System.out.println(course);
        System.out.println(appDAO.findCourseAndReviewsById(id));
    }

    private void createCourseAndRewiews(AppDAO appDAO) {
        Course course = new Course("Pacman-How to ect");
        course.addReview(new Review("Great course"));
        course.addReview(new Review("Awesome course"));
        course.addReview(new Review("Dumb course"));
        appDAO.saveCourse(course);
    }

    private void deleteCourse(AppDAO appDAO) {
        int theId=11;
        System.out.println(theId);
        appDAO.deleteCourseById(theId);
        System.out.println("Done");
    }

    private void updateCourse(AppDAO appDAO) {
        int theId = 10;
        Course theCourse = appDAO.findCourseById(theId);
        System.out.println("przed"+theCourse);
        theCourse.setTitle("ENJOY!");
        appDAO.update(theCourse);
        System.out.println("po"+theCourse);
    }

    private void updateInstructor(AppDAO appDAO) {
        int theId=1;
        Instructor instructor=appDAO.findInstructorById(theId);
        System.out.println("PRZED:"+instructor);
        instructor.setLastName("TESTER");
        appDAO.update(instructor);
        System.out.println("PO:"+instructor);
    }

    private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {

        int theId = 1;

        // find the instructor
        System.out.println("Finding instructor id: " + theId);
        Instructor tempInstructor = appDAO.findInstructorByIdJoinFetch(theId);

        System.out.println("tempInstructor: " + tempInstructor);
        System.out.println("the associated courses: " + tempInstructor.getCourses());

        System.out.println("Done!");
    }

    private void findCoursesForInstructor(AppDAO appDAO) {

        int theId = 1;
        // find instructor
        System.out.println("Finding instructor id: " + theId);

        Instructor tempInstructor = appDAO.findInstructorById(theId);

        System.out.println("tempInstructor: " + tempInstructor);

        // find courses for instructor
        System.out.println("Finding courses for instructor id: " + theId);
        List<Course> courses = appDAO.findCoursesByInstructorId(theId);

        // associate the objects
        tempInstructor.setCourses(courses);

        System.out.println("the associated courses: " + tempInstructor.getCourses());

        System.out.println("Done!");
    }

    private void findInstructorWithCourses(AppDAO appDAO) {

        int theId = 1;
        System.out.println("Finding instructor id: " + theId);

        Instructor tempInstructor = appDAO.findInstructorById(theId);

        System.out.println("tempInstructor: " + tempInstructor);
        System.out.println("the associated courses: " + tempInstructor.getCourses());

        System.out.println("Done!");
    }

    private void createInstructorWithCourses(AppDAO appDAO) {

        // create the instructor
        Instructor tempInstructor =
                new Instructor("Susan", "Public", "susan.public@luv2code.com");

        // create the instructor detail
        InstructorDetail tempInstructorDetail =
                new InstructorDetail(
                        "http://www.youtube.com",
                        "Video Games");

        // associate the objects
        tempInstructor.setInstructorDetail(tempInstructorDetail);

        // create some courses
        Course tempCourse1 = new Course("Air Guitar - The Ultimate Guide");
        Course tempCourse2 = new Course("The Pinball Masterclass");

        // add courses to instructor
        tempInstructor.add(tempCourse1);
        tempInstructor.add(tempCourse2);

        // save the instructor
        //
        // NOTE: this will ALSO save the courses
        // because of CascadeType.PERSIST
        //
        System.out.println("Saving instructor: " + tempInstructor);
        System.out.println("The courses: " + tempInstructor.getCourses());
        appDAO.save(tempInstructor);

        System.out.println("Done!");
    }

    private void deleteInstructorDetail(AppDAO appDAO) {

        int theId = 3;
        System.out.println("Deleting instructor detail id: " + theId);

        appDAO.deleteInstructorDetailById(theId);

        System.out.println("Done!");
    }

    private void findInstructorDetail(AppDAO appDAO) {

        // get the instructor detail object
        int theId = 2;
        InstructorDetail tempInstructorDetail = appDAO.findInstructorDetailById(theId);

        // print the instructor detail
        System.out.println("tempInstructorDetail: " + tempInstructorDetail);

        // print the associated instructor
        System.out.println("the associated instructor: " + tempInstructorDetail.getInstructor());

        System.out.println("Done!");
    }

    private void deleteInstructor(AppDAO appDAO) {

        int theId = 1;
        System.out.println("Deleting instructor id: " + theId);

        appDAO.deleteInstructorById(theId);

        System.out.println("Done!");
    }

    private void findInstructor(AppDAO appDAO) {

        int theId = 2;
        System.out.println("Finding instructor id: " + theId);

        Instructor tempInstructor = appDAO.findInstructorById(theId);

        System.out.println("tempInstructor: " + tempInstructor);
        System.out.println("the associated instructorDetail only: " + tempInstructor.getInstructorDetail());

    }

    private void createInstructor(AppDAO appDAO) {

		/*
		// create the instructor
		Instructor tempInstructor =
				new Instructor("Chad", "Darby", "darby@luv2code.com");

		// create the instructor detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail(
						"http://www.luv2code.com/youtube",
						"Luv 2 code!!!");
		*/

        // create the instructor
        Instructor tempInstructor =
                new Instructor("Madhu", "Patel", "madhu@luv2code.com");

        // create the instructor detail
        InstructorDetail tempInstructorDetail =
                new InstructorDetail(
                        "http://www.luv2code.com/youtube",
                        "Guitar");

        // associate the objects
        tempInstructor.setInstructorDetail(tempInstructorDetail);

        // save the instructor
        //
        // NOTE: this will ALSO save the details object
        // because of CascadeType.ALL
        //
        System.out.println("Saving instructor: " + tempInstructor);
        appDAO.save(tempInstructor);

        System.out.println("Done!");
    }
}