package com.nnk.springboot.controllers;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.RuleNameDTO;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    private static final Logger logger = LogManager.getLogger(RuleNameController.class);

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        logger.debug("ruleNameList");
        List<RuleNameDTO> ruleList = ruleNameService.ruleList();
        model.addAttribute("ruleList", ruleList);

        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleNameDTO ruleName) {

        logger.debug("ruleName/addRuleNameForm");
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleNameDTO ruleNameDTO, BindingResult result, Model model) {
        logger.debug("ruleName/validate");
        if (!result.hasErrors()) {
            ruleNameService.addRule(ruleNameDTO);
            model.addAttribute("ruleNameDTO", ruleNameService.ruleList());
            return "redirect:/ruleName/list";
        }
        logger.error("validate error for id"+ruleNameDTO.getId());
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("ruleName/updateForm");
        RuleNameDTO ruleNameDTO = ruleNameService.checkRule(id);
        model.addAttribute("ruleNameDTO", ruleNameDTO);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameDTO ruleNameDTO,
                             BindingResult result, Model model) {
        logger.debug("ruleName/update");
        if (result.hasErrors()) {
            logger.error("update error for id"+id);
            return "ruleName/update";
        }

        ruleNameService.updateRule(ruleNameDTO);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.debug("ruleName/delete");
        RuleNameDTO ruleNameDTO = ruleNameService.checkRule(id);
        ruleNameService.deleteRule(ruleNameDTO);

        model.addAttribute("ruleName", ruleNameService.ruleList());
        return "redirect:/ruleName/list";
    }
}
