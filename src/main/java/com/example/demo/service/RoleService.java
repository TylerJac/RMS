package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        Set<String> roleNames = new HashSet<>();
        roleNames.add("ADMIN");
        roleNames.add("STAFF");

        for (String roleName : roleNames) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                roleRepository.save(new Role(roleName));
            }
        }
    }
}
