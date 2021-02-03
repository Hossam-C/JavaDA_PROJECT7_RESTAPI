package com.nnk.springboot;

import com.nnk.springboot.DTO.RatingDTO;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
public class RatingTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private UserDetails userDetails;

    private RatingDTO rating1 = new RatingDTO();
    private RatingDTO rating2 = new RatingDTO();
    private RatingDTO rating3 = new RatingDTO();

    @BeforeEach
    public void setUp(){
        rating1.setId(1);
        rating1.setMoodysRating("1");
        rating1.setSandPRating("10");
        rating1.setFitchRating("100");
        rating1.setOrderNumber(11);

        rating2.setId(2);
        rating2.setMoodysRating("2");
        rating2.setSandPRating("20");
        rating2.setFitchRating("200");
        rating2.setOrderNumber(22);

        rating3.setId(3);
        rating3.setMoodysRating("3");
        rating3.setSandPRating("30");
        rating3.setFitchRating("300");
        rating3.setOrderNumber(33);

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void ratingControllergetListTest(){

        List<RatingDTO> listRating =new ArrayList<>();
        listRating.add(rating1);
        listRating.add(rating2);
        listRating.add(rating3);

        when(ratingService.ratingList()).thenReturn(listRating);

        try {
            mvc.perform(get("/rating/list")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void addRatingWithRedirect() throws Exception {

        mvc.perform(get("/rating/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void showUpdateFormWithAnExistingId() throws Exception {

        when(ratingService.checkRating(anyInt())).thenReturn(rating1);

        mvc.perform(get("/rating/update/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void deleteRatingWitheAnExistingId() throws Exception {

        when(ratingService.checkRating(anyInt())).thenReturn(rating1);
        doNothing().when(ratingService).deleteRating(any(RatingDTO.class));
        mvc.perform(get("/rating/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void validateWithAnIncorrectRatingIdFormat() throws Exception {


        when(ratingService.checkRating(anyInt())).thenReturn(rating1);
        mvc.perform(post("/rating/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "abc")
                .param("moodysRating", "1")
                .param("sandPRating", "10")
                .param("fitchRating", "100")
                .param("orderNumber", "11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void updateRatingWithAnExistingId() throws Exception {

        when(ratingService.checkRating(1)).thenReturn(rating1);
        mvc.perform(post("/rating/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("moodysRating", "1")
                .param("sandPRating", "10")
                .param("fitchRating", "100")
                .param("orderNumber", "11"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    public void updateRatingWithIncorrectId() throws Exception {

        mvc.perform(post("/rating/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "abc")
                .param("moodysRating", "1")
                .param("sandPRating", "10")
                .param("fitchRating", "100")
                .param("orderNumber", "11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

}
