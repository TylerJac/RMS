package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpass");
        user.setRole("ROLE_USER");

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testFindUserByUsername() {
        User user = new User();
        user.setUsername("findme");
        user.setPassword("testpass");
        user.setRole("ROLE_USER");

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("findme");

    assertThat(foundUser.isPresent()).isTrue();
    assertThat(foundUser.get().getUsername()).isEqualTo("findme");
    }
}

