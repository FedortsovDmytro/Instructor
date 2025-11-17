package com.example.demo.controller;

import com.example.demo.entity.InstructorDetail;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/student")
    public String showStudentForm(Model model) {
        model.addAttribute("student", new User());
        return "register-student";
    }


    @GetMapping("/instructor")
    public String showInstructorForm(Model model) {
        model.addAttribute("instructor", new User());
        return "register-instructor";
    }


    @PostMapping("/student")
    public String registerStudent(@ModelAttribute("student") @Valid User user,
                                  BindingResult result,
                                  Model model) {

        if (result.hasErrors()) {
            return "register-student";
        }

        if (userService.exists(user.getEmail())) {
            model.addAttribute("registrationError", "Email already exists");
            return "register-student";
        }

        User newUser = new User.Builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role("STUDENT")
                .build();

        userService.register(newUser);
        return "redirect:/login";
    }

    @PostMapping("/instructor")
    public String registerInstructor(@ModelAttribute("instructor") @Valid User user,
                                     @RequestParam("youtube") String youtube,
                                     @RequestParam("hobby") String hobby,
                                     BindingResult result,
                                     Model model) {

        if (result.hasErrors()) {
            return "register-instructor";
        }

        if (userService.exists(user.getEmail())) {
            model.addAttribute("registrationError", "Email already exists");
            return "register-instructor";
        }

        InstructorDetail detail = new InstructorDetail();
        detail.setYoutubeChannel(youtube);
        detail.setHobby(hobby);

        User newUser = new User.Builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role("INSTRUCTOR")
                .instructorDetail(detail)
                .build();

        userService.register(newUser);
        return "redirect:/login";
    }
}
