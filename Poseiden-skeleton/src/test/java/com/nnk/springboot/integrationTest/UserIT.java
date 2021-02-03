package com.nnk.springboot.integrationTest;


import com.nnk.springboot.DTO.UserDTO;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class UserIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User user1 = new User();
    private User user2 = new User();
    private User user3 = new User();

    private UserDTO userDTO1 = new UserDTO();
    private UserDTO userDTO2 = new UserDTO();
    private UserDTO userDTO3 = new UserDTO();

    @BeforeEach
    @Rollback(value = false)
    public void setUp(){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        user1.setId(1);
        user1.setUsername("Test1");
        user1.setPassword(encoder.encode("Test1"));
        user1.setFullname("Test1");
        user1.setRole("USER");

        userDTO1.setId(user1.getId());
        userDTO1.setUsername(user1.getUsername());
        userDTO1.setPassword(user1.getPassword());
        userDTO1.setFullname(user1.getFullname());
        userDTO1.setRole(user1.getRole());


        user2.setId(2);
        user2.setUsername("Test2");
        user2.setPassword(encoder.encode("Test2"));
        user2.setFullname("Test2");
        user2.setRole("ADMIN");

        userDTO2.setId(user2.getId());
        userDTO2.setUsername(user2.getUsername());
        userDTO2.setPassword(user2.getPassword());
        userDTO2.setFullname(user2.getFullname());
        userDTO2.setRole(user2.getRole());

        user3.setId(3);
        user3.setUsername("Test3");
        user3.setPassword(encoder.encode("Test3"));
        user3.setFullname("Test3");
        user3.setRole("USER");

        userDTO3.setId(user3.getId());
        userDTO3.setUsername(user3.getUsername());
        userDTO3.setPassword(user3.getPassword());
        userDTO3.setFullname(user3.getFullname());
        userDTO3.setRole(user3.getRole());

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        user3 = userRepository.save(user3);

    }

    @AfterEach
    @Rollback(value = false)
    public void cleanDB() {
        userRepository.deleteAll();
    }

    @Test
    public void userTest() {
        User user = new User("Test1", "Test1", "Test1", "USER");

        // Save
        user = userRepository.save(user);
        Assert.assertNotNull(user.getId());
        Assert.assertEquals(user.getUsername(), "Test1", "Test1");

        // Update
        user.setUsername("Test11");
        user = userRepository.save(user);
        Assert.assertEquals(user.getUsername(), "Test11", "Test11");

        // Find
        List<User> listResult = userRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = user.getId();
        userRepository.delete(user);
        Optional<User> userList = userRepository.findById(id);
        Assert.assertFalse(userList.isPresent());
    }

    @Test
    public void listUserService() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        List<UserDTO> listUser = userService.userList();

        assertThat(listUser.get(0).getUsername()).isEqualTo("Test1");
        assertThat(encoder.matches("Test1",listUser.get(0).getPassword()));
        assertThat(listUser.get(0).getFullname()).isEqualTo("Test1");
        assertThat(listUser.get(0).getRole()).isEqualTo("USER");

        assertThat(listUser.get(1).getUsername()).isEqualTo("Test2");
        assertThat(encoder.matches("Test2",listUser.get(1).getPassword()));
        assertThat(listUser.get(1).getFullname()).isEqualTo("Test2");
        assertThat(listUser.get(1).getRole()).isEqualTo("ADMIN");

        assertThat(listUser.get(2).getUsername()).isEqualTo("Test3");
        assertThat(encoder.matches("Test3",listUser.get(2).getPassword()));
        assertThat(listUser.get(2).getFullname()).isEqualTo("Test3");
        assertThat(listUser.get(2).getRole()).isEqualTo("USER");

    }

    @Test
    public void addUserTest() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        UserDTO userDTO4 = new UserDTO();

        userDTO4.setId(4);
        userDTO4.setUsername("Test4");
        userDTO4.setPassword("Test4");
        userDTO4.setFullname("Test4");
        userDTO4.setRole("ADMIN");

        userService.addUser(userDTO4);


        List<UserDTO> listUser = userService.userList();

        assertThat(listUser.get(3).getUsername()).isEqualTo("Test4");
        assertThat(encoder.matches("Test4",listUser.get(3).getPassword()));
        assertThat(listUser.get(3).getFullname()).isEqualTo("Test4");
        assertThat(listUser.get(3).getRole()).isEqualTo("ADMIN");


    }

    @Test
    public void updateUserTest() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        userDTO1.setUsername("Test9");
        userDTO1.setPassword("Test9");
        userDTO1.setFullname("Test9");
        userDTO1.setRole("USER");

        userService.updateUser(userDTO1);

        User user9 = userRepository.findByUsername("Test9");

        assertThat(user9.getUsername()).isEqualTo("Test9");
        assertThat(encoder.matches("Test4",user9.getPassword()));
        assertThat(user9.getFullname()).isEqualTo("Test9");
        assertThat(user9.getRole()).isEqualTo("USER");

    }

    @Test
    public void deleteUserTest() {

        user1 = userRepository.findByUsername("Test1");
        userDTO1.setId(user1.getId());

        userService.deleteUser(userDTO1);


        assertThat(userRepository.findByUsername("Test1")).isNull();
        assertThat(userRepository.findByUsername("Test2").getFullname()).isEqualTo("Test2");
        assertThat(userRepository.findByUsername("Test3").getRole()).isEqualTo("USER");
    }

    @Test
    public void checkUserTest() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        UserDTO userCheck = userService.checkUser(userRepository.findByUsername("Test2").getId());

        assertThat(userCheck.getUsername()).isEqualTo("Test2");
        assertThat(encoder.matches("Test4",userCheck.getPassword()));
        assertThat(userCheck.getFullname()).isEqualTo("Test2");
        assertThat(userCheck.getRole()).isEqualTo("ADMIN");
    }

    @Test
    public void checkUserNotFoundTest() {

        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkUser(999);
        });

    }
}
