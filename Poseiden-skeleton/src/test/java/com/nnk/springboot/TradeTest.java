package com.nnk.springboot;



import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.TradeDTO;
import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.UserDetails;
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
public class TradeTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TradeService tradeService;

    @MockBean
    private UserDetails userDetails;

    private TradeDTO trade1 = new TradeDTO();
    private TradeDTO trade2 = new TradeDTO();
    private TradeDTO trade3 = new TradeDTO();

    @BeforeEach
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
    public void tradeListControllergetListTest(){

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


    }
    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void addTradeWithRedirect() throws Exception {

        mvc.perform(get("/trade/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void showUpdateFormWithAnExistingId() throws Exception {

        when(tradeService.checkTrade(anyInt())).thenReturn(trade1);

        mvc.perform(get("/trade/update/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void deleteTradeWitheAnExistingId() throws Exception {

        when(tradeService.checkTrade(anyInt())).thenReturn(trade1);
        doNothing().when(tradeService).deleteTrade(any(TradeDTO.class));
        mvc.perform(get("/trade/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void validateWithAnIncorrectTradeIdFormat() throws Exception {


        when(tradeService.checkTrade(anyInt())).thenReturn(trade1);
        mvc.perform(post("/trade/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("tradeId", "abc")
                .param("account", "account1")
                .param("type", "type1")
                .param("buyQuantity", "10d"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void updateTradeWithAnExistingId() throws Exception {

        when(tradeService.checkTrade(1)).thenReturn(trade1);
        mvc.perform(post("/trade/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("tradeId", "1")
                .param("account", "account1")
                .param("type", "type1")
                .param("buyQuantity", "10d"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    public void updateTradeWithIncorrectId() throws Exception {

        mvc.perform(post("/trade/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("tradeId", "abc")
                .param("account", "account1")
                .param("type", "type1")
                .param("buyQuantity", "10d"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

}
