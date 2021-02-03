package com.nnk.springboot;


import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.UserDTO;


import com.nnk.springboot.controllers.UserController;

import com.nnk.springboot.services.UserDetails;
import com.nnk.springboot.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetails userDetails;

    private UserDTO user1 = new UserDTO();
    private UserDTO user2 = new UserDTO();
    private UserDTO user3 = new UserDTO();

    @BeforeEach
    public void setUp(){
        user1.setId(1);
        user1.setUsername("Test1");
        user1.setPassword("Test1");
        user1.setFullname("Test1");
        user1.setRole("USER");

        user2.setId(2);
        user2.setUsername("Test2");
        user2.setPassword("Test2");
        user2.setFullname("Test2");
        user2.setRole("ADMIN");

        user3.setId(3);
        user3.setUsername("Test3");
        user3.setPassword("Test3");
        user3.setFullname("Test3");
        user3.setRole("USER");

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void bidListControllergetListTest(){

        List<UserDTO> listUser =new ArrayList<>();
        listUser.add(user1);
        listUser.add(user1);
        listUser.add(user1);

        when(userService.userList()).thenReturn(listUser);

        try {
            mvc.perform(get("/user/list")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
            ;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void addUserWithRedirect() throws Exception {

        mvc.perform(get("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void showUpdateFormWithAnExistingId() throws Exception {

        when(userService.checkUser(anyInt())).thenReturn(user1);

        mvc.perform(get("/user/update/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void deleteUserWitheAnExistingId() throws Exception {

        when(userService.checkUser(anyInt())).thenReturn(user1);
        doNothing().when(userService).deleteUser(any(UserDTO.class));
        mvc.perform(get("/user/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void validateWithAnIncorrectUserIdFormat() throws Exception {


        when(userService.checkUser(anyInt())).thenReturn(user1);
        mvc.perform(post("/user/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "abc")
                .param("fullname", "Test1")
                .param("username", "Test1")
                .param("role", "USER"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    public void updateUserWithAnExistingId() throws Exception {

        when(userService.checkUser(1)).thenReturn(user1);
        mvc.perform(post("/user/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("fullname", "Test1")
                .param("username", "Test1")
                .param("role", "USER"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    public void updateUserWithIncorrectId() throws Exception {

        mvc.perform(post("/user/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "abc")
                .param("fullname", "Test1")
                .param("username", "Test1")
                .param("role", "USER"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }
}
