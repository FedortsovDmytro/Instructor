package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.service.CourseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping("/student")
public class StudentController {

    private final CourseService courseService;

    public StudentController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/find-course")
    public String searchCourses(@RequestParam(required = false) String keywords, Model model) {

        List<Course> result = new ArrayList<>();

        if (keywords != null && !keywords.isBlank()) {
            result = courseService.findAllCourses(keywords);
        }

        model.addAttribute("courses", result);
        model.addAttribute("keywords", keywords);

        return "student-course-search";
    }
    @GetMapping("/enroll/{courseId}")
    public String enrollCourse(@PathVariable Long courseId,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        User student = (User) session.getAttribute("user");
        if (student == null) {
            redirectAttributes.addFlashAttribute("error", "You must be logged in.");
            return "redirect:/login";
        }

        boolean enrolled = courseService.enrollStudentToCourse(student.getId(), courseId);

        if (enrolled) {
            redirectAttributes.addFlashAttribute("success", "Successfully enrolled!");
        } else {
            redirectAttributes.addFlashAttribute("error", "You are already enrolled.");
        }

        return "redirect:/student/find-course";
    }

}
