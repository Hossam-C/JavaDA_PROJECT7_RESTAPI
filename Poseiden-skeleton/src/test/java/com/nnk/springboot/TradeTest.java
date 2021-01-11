package com.nnk.springboot;


import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.TradeDTO;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.TradeService;
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
@WebMvcTest(TradeController.class)
public class TradeTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TradeService tradeService;

    private TradeDTO trade1 = new TradeDTO();
    private TradeDTO trade2 = new TradeDTO();
    private TradeDTO trade3 = new TradeDTO();

    @Before
    public void setUp(){
        trade1.setTradeId(1);
        trade1.setAccount("Account1");
        trade1.setType("Type1");
        trade1.setBuyQuantity(10d);

        trade2.setTradeId(2);
        trade2.setAccount("Account2");
        trade2.setType("Type2");
        trade2.setBuyQuantity(20d);

        trade3.setTradeId(3);
        trade3.setAccount("Account3");
        trade3.setType("Type3");
        trade3.setBuyQuantity(30d);

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void bidListControllergetListTest(){

        List<TradeDTO> listTrade =new ArrayList<>();
        listTrade.add(trade1);
        listTrade.add(trade1);
        listTrade.add(trade1);

        when(tradeService.tradeList()).thenReturn(listTrade);

        try {
            mvc.perform(get("/trade/list")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
;

        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(tradeService, Mockito.times(1)).tradeList();

    }

}
