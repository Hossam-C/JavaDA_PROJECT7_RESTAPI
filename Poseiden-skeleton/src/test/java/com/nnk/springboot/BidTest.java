package com.nnk.springboot;


import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
public class BidTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDetails userDetails;

    @MockBean
    private BidListService bidListService;


    private BidListDTO bid1 = new BidListDTO();
    private BidListDTO bid2 = new BidListDTO();
    private BidListDTO bid3 = new BidListDTO();

    @BeforeEach
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

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void addBidListWithRedirect() throws Exception {

        mvc.perform(get("/bidList/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void showUpdateFormWithAnExistingId() throws Exception {

        when(bidListService.checkBid(anyInt())).thenReturn(bid1);

            mvc.perform(get("/bidList/update/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void deleteUserWitheAnExistingId() throws Exception {

        when(bidListService.checkBid(anyInt())).thenReturn(bid1);
        doNothing().when(bidListService).deleteBid(any(BidListDTO.class));
        mvc.perform(get("/bidList/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void validateWithAnIncorrectBidListIdFormat() throws Exception {


        when(bidListService.checkBid(anyInt())).thenReturn(bid1);
            mvc.perform(post("/bidList/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("Id", "abc")
                .param("account", "account")
                .param("type", "type")
                .param("bidQuantity", "150d"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    public void updateBidListWithAnExistingId() throws Exception {

        when(bidListService.checkBid(1)).thenReturn(bid1);
            mvc.perform(post("/bidList/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("bidListId", "4")
                .param("account", "Account")
                .param("type", "Type")
                .param("bidQuantity", "150d"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    public void updateBidListWithIncorrectId() throws Exception {

            mvc.perform(post("/bidList/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("Id", "abc")
                .param("account", "Account")
                .param("type", "Type")
                .param("bidQuantity", "150d"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }
}
