package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

    private final UserService userService;
    private final CourseService courseService;

    public InstructorController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/home")
    public String instructorHome(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        if (user == null || !user.getRole().equals("INSTRUCTOR")) {
            return "redirect:/login";
        }

        List<Course> courses = courseService.getCoursesByInstructor(user);
        model.addAttribute("courses", courses);
        model.addAttribute("instructor", user);

        return "instructor-home";
    }

    @GetMapping("/create-course")
    public String showCreateCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "create-course";
    }

    @PostMapping("/save-course")
    public String saveCourse(
            @ModelAttribute("course") Course course,
            HttpSession session
    ) {
        User instructor = (User) session.getAttribute("user");

        if (!instructor.getRole().equals("INSTRUCTOR")) {
            return "redirect:/access-denied";
        }

        course.setInstructor(instructor);
        courseService.saveCourse(course);

        return "redirect:/instructor/home";
    }

    @GetMapping("/edit-course/{id}")
    public String editCourse(@PathVariable("id") int id, HttpSession session, Model model) {
        User instructor = (User) session.getAttribute("user");

        Course course = courseService.findById(id);

        if (course == null || course.getInstructor().getId() != instructor.getId()) {
            throw new IllegalArgumentException("Course not found or unauthorized");
        }

        model.addAttribute("course", course);
        return "edit-course";
    }

    @PostMapping("/update-course/{id}")
    public String updateCourse(@PathVariable("id") int id,
                               @ModelAttribute("course") Course updated,
                               HttpSession session) {

        User instructor = (User) session.getAttribute("user");

        Course existing = courseService.findById(id);

        if (existing == null || existing.getInstructor().getId() != instructor.getId()) {
            throw new IllegalArgumentException("Course not found or unauthorized");
        }

        existing.setTitle(updated.getTitle());
        courseService.saveCourse(existing);

        return "redirect:/instructor/home";
    }

    @GetMapping("/delete-course/{id}")
    public String deleteCourse(@PathVariable("id") int id, HttpSession session) {

        User instructor = (User) session.getAttribute("user");
        Course course = courseService.findById(id);

        if (course == null || course.getInstructor().getId() != instructor.getId()) {
            throw new IllegalArgumentException("Course not found or unauthorized");
        }

        courseService.deleteCourseById(id);
        return "redirect:/instructor/home";
    }
}
