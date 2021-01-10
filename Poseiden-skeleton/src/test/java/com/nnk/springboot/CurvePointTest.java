package com.nnk.springboot;


import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.CurvePointDTO;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.CurvePointService;
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
@WebMvcTest(CurveController.class)
public class CurvePointTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurvePointService curvePointService;

    private CurvePointDTO curve1 = new CurvePointDTO();
    private CurvePointDTO curve2 = new CurvePointDTO();
    private CurvePointDTO curve3 = new CurvePointDTO();

    @Before
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

        verify(curvePointService, Mockito.times(1)).curvePointList();

    }

}
