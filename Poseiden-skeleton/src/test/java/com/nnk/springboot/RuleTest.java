package com.nnk.springboot;


import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.RuleNameDTO;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RuleNameService ruleNameService;

    @MockBean
    private UserDetails userDetails;

    private RuleNameDTO rule1 = new RuleNameDTO();
    private RuleNameDTO rule2 = new RuleNameDTO();
    private RuleNameDTO rule3 = new RuleNameDTO();

    @BeforeEach
    public void setUp(){
        rule1.setId(1);
        rule1.setName("rule1");
        rule1.setDescription("test1");
        rule1.setJson("1");
        rule1.setTemplate("11");
        rule1.setSqlStr("111");
        rule1.setSqlPart("1111");

        rule2.setId(2);
        rule2.setName("rule2");
        rule2.setDescription("test2");
        rule2.setJson("2");
        rule2.setTemplate("22");
        rule2.setSqlStr("222");
        rule2.setSqlPart("2222");

        rule3.setId(3);
        rule3.setName("rule3");
        rule3.setDescription("test3");
        rule3.setJson("3");
        rule3.setTemplate("33");
        rule3.setSqlStr("333");
        rule3.setSqlPart("3333");

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void ruleNameControllergetListTest(){

        List<RuleNameDTO> listRule =new ArrayList<>();
        listRule.add(rule1);
        listRule.add(rule2);
        listRule.add(rule3);

        when(ruleNameService.ruleList()).thenReturn(listRule);

        try {
            mvc.perform(get("/ruleName/list")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
;

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void addRuleNameWithRedirect() throws Exception {

        mvc.perform(get("/ruleName/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void showUpdateFormWithAnExistingId() throws Exception {

        when(ruleNameService.checkRule(anyInt())).thenReturn(rule1);

        mvc.perform(get("/ruleName/update/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void deleteRuleNameWitheAnExistingId() throws Exception {

        when(ruleNameService.checkRule(anyInt())).thenReturn(rule1);
        doNothing().when(ruleNameService).deleteRule(any(RuleNameDTO.class));
        mvc.perform(get("/ruleName/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @org.junit.jupiter.api.Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void validateWithAnIncorrectRuleNameIdFormat() throws Exception {


        when(ruleNameService.checkRule(anyInt())).thenReturn(rule1);
        mvc.perform(post("/ruleName/validate")
                //.content(asJsonString(bid1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("Id", "abc")
                .param("name", "rule1")
                .param("description", "test1")
                .param("json", "1")
                .param("template", "11")
                .param("sqlStr", "111")
                .param("sqlPart", "1111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @org.junit.jupiter.api.Test
    public void updateBidListWithAnExistingId() throws Exception {

        when(ruleNameService.checkRule(1)).thenReturn(rule1);
        mvc.perform(post("/ruleName/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("Id", "1")
                .param("name", "rule1")
                .param("description", "test1")
                .param("json", "1")
                .param("template", "11")
                .param("sqlStr", "111")
                .param("sqlPart", "1111"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @org.junit.jupiter.api.Test
    public void updateRuleNameWithIncorrectId() throws Exception {

        mvc.perform(post("/ruleName/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("Id", "abc")
                .param("name", "rule1")
                .param("description", "test1")
                .param("json", "1")
                .param("template", "11")
                .param("sqlStr", "111")
                .param("sqlPart", "1111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

}
