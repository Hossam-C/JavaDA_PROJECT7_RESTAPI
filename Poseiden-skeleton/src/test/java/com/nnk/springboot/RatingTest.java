package com.nnk.springboot;


import com.nnk.springboot.DTO.CurvePointDTO;
import com.nnk.springboot.DTO.RatingDTO;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.RatingService;
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
@WebMvcTest(RatingController.class)
public class RatingTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RatingService ratingService;

    private RatingDTO rating1 = new RatingDTO();
    private RatingDTO rating2 = new RatingDTO();
    private RatingDTO rating3 = new RatingDTO();

    @Before
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
        listRating.add(rating1);
        listRating.add(rating1);

        when(ratingService.ratingList()).thenReturn(listRating);

        try {
            mvc.perform(get("/rating/list")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
;

        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, Mockito.times(1)).ratingList();

    }

}
