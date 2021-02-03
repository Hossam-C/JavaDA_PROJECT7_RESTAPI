package com.nnk.springboot;


import com.nnk.springboot.DTO.CurvePointDTO;
import com.nnk.springboot.services.CurvePointService;

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
public class CurvePointTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    private UserDetails userDetails;

    private CurvePointDTO curve1 = new CurvePointDTO();
    private CurvePointDTO curve2 = new CurvePointDTO();
    private CurvePointDTO curve3 = new CurvePointDTO();

    @BeforeEach
    public void setUp(){
        curve1.setId(1);
        curve1.setCurveId(111);
        curve1.setTerm(100d);
        curve1.setValue(10d);

        curve2.setId(2);
        curve2.setCurveId(222);
        curve2.setTerm(200d);
        curve2.setValue(20d);

        curve3.setId(3);
        curve3.setCurveId(333);
        curve3.setTerm(300d);
        curve3.setValue(30d);

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void curvePointControllergetListTest(){

        List<CurvePointDTO> listCurve =new ArrayList<>();
        listCurve.add(curve1);
        listCurve.add(curve2);
        listCurve.add(curve3);

        when(curvePointService.curvePointList()).thenReturn(listCurve);

        try {
            mvc.perform(get("/curvePoint/list")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void addCurvePointWithRedirect() throws Exception {

        mvc.perform(get("/curvePoint/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void showUpdateFormWithAnExistingId() throws Exception {

        when(curvePointService.checkCurve(anyInt())).thenReturn(curve1);

        mvc.perform(get("/curvePoint/update/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void deleteCurvePointWitheAnExistingId() throws Exception {

        when(curvePointService.checkCurve(anyInt())).thenReturn(curve1);
        doNothing().when(curvePointService).deleteCurve(any(CurvePointDTO.class));
        //WHEN //THEN return list
        mvc.perform(get("/curvePoint/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void validateWithAnIncorrectCurvePointIdFormat() throws Exception {


        when(curvePointService.checkCurve(anyInt())).thenReturn(curve1);
        mvc.perform(post("/curvePoint/validate")
                //.content(asJsonString(bid1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "abc")
                .param("curveId", "111")
                .param("term", "100d")
                .param("Value", "10d"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void updateCurvePointWithAnExistingId() throws Exception {

        when(curvePointService.checkCurve(1)).thenReturn(curve1);
        mvc.perform(post("/curvePoint/update/1")
                //.content(asJsonString(curvePoint))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("curveId", "111")
                .param("term", "100d")
                .param("Value", "10d"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    public void updateCurvePointWithIncorrectId() throws Exception {

        mvc.perform(post("/curvePoint/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "abc")
                .param("curveId", "111")
                .param("term", "100d")
                .param("Value", "10d"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }



}
