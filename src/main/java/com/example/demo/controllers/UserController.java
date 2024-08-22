package com.example.demo.controllers;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, @ModelAttribute("role") String roleName) {
        Role selectedRole = roleRepository.findByName(roleName);
        user.setRoles(Collections.singleton(selectedRole));  // Assign the selected role to the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encode the password
        user.setEnabled(true);
        userRepository.save(user);
        return "redirect:/login";  // Redirect to the login page after registration
    }
}
