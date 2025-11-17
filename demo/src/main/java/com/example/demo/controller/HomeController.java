package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String dashboard(HttpSession session, Model model) {
        User sessUser = (User) session.getAttribute("user");
        if (sessUser == null) {
            return "redirect:/login";
        }

        User fullUser;   // fully loaded entity
        if ("INSTRUCTOR".equals(sessUser.getRole())) {
            fullUser = userService.findInstructorWithCoursesTaughtById(sessUser.getId());
            model.addAttribute("coursesTaught", fullUser.getCoursesTaught());
        } else { // STUDENT
            fullUser = userService.findStudentWithCoursesById(sessUser.getId());
            model.addAttribute("coursesEnrolled", fullUser.getCourses());
        }

        session.setAttribute("user", fullUser);

        return "dashboard";
    }
}