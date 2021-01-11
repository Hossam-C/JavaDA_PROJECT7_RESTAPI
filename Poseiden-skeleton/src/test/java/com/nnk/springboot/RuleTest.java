package com.nnk.springboot;


import com.nnk.springboot.DTO.RuleNameDTO;
import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.services.RuleNameService;
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
@WebMvcTest(RuleNameController.class)
public class RuleTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RuleNameService ruleNameService;

    private RuleNameDTO rule1 = new RuleNameDTO();
    private RuleNameDTO rule2 = new RuleNameDTO();
    private RuleNameDTO rule3 = new RuleNameDTO();

    @Before
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

        verify(ruleNameService, Mockito.times(1)).ruleList();

    }

}
