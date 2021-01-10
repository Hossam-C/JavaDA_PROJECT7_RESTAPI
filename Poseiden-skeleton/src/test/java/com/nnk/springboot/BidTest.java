package com.nnk.springboot;


import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
@WebMvcTest(BidListController.class)
public class BidTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BidListService bidListService;

    private BidListDTO bid1 = new BidListDTO();
    private BidListDTO bid2 = new BidListDTO();
    private BidListDTO bid3 = new BidListDTO();

    @Before
    public void setUp(){
        bid1.setId(1);
        bid1.setAccount("Account1");
        bid1.setType("Type1");
        bid1.setBidQuantity(10d);

        bid2.setId(2);
        bid2.setAccount("Account2");
        bid2.setType("Type2");
        bid2.setBidQuantity(20d);

        bid3.setId(3);
        bid3.setAccount("Account3");
        bid3.setType("Type3");
        bid3.setBidQuantity(30d);

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void bidListControllergetListTest(){

        List<BidListDTO> listBid =new ArrayList<>();
        listBid.add(bid1);
        listBid.add(bid2);
        listBid.add(bid3);

        when(bidListService.bidList()).thenReturn(listBid);

        try {
            mvc.perform(get("/bidList/list")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
;

        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(bidListService, Mockito.times(1)).bidList();

    }

}
