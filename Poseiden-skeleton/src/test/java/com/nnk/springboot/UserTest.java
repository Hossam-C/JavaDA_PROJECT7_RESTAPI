package com.nnk.springboot;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.UserDTO;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private UserDTO user1 = new UserDTO();
    private UserDTO user2 = new UserDTO();
    private UserDTO user3 = new UserDTO();

    @Before
    public void setUp(){
        user1.setId(1);
        user1.setUsername("Test1");
        user1.setPassword("Test1");
        user1.setFullname("Test1");
        user1.setRole("USER");

        user2.setId(2);
        user2.setUsername("Test1");
        user2.setPassword("Test1");
        user2.setFullname("Test1");
        user2.setRole("USER");

        user3.setId(3);
        user3.setUsername("Test1");
        user3.setPassword("Test1");
        user3.setFullname("Test1");
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

        verify(userService, Mockito.times(1)).userList();

    }
}
