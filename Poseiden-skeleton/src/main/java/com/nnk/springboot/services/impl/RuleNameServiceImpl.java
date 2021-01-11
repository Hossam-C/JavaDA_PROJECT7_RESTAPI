package com.nnk.springboot.services.impl;

import com.nnk.springboot.DTO.RuleNameDTO;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class RuleNameServiceImpl implements RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Override
    public List<RuleNameDTO> ruleList(){

        List<RuleNameDTO>  ruleList = new ArrayList<>();

        List<RuleName> ruleNameList = ruleNameRepository.findAll();

        for ( RuleName rlist : ruleNameList) {
            RuleNameDTO ruleNameDTO = new RuleNameDTO();
            ruleNameDTO.setId(rlist.getId());
            ruleNameDTO.setName(rlist.getName());
            ruleNameDTO.setDescription(rlist.getDescription());
            ruleNameDTO.setJson(rlist.getJson());
            ruleNameDTO.setTemplate(rlist.getTemplate());
            ruleNameDTO.setSqlStr(rlist.getSqlStr());
            ruleNameDTO.setSqlPart(rlist.getSqlPart());

            ruleList.add(ruleNameDTO);
        }


        return ruleList;
    }

    @Override
    public void addRule(RuleNameDTO ruleNameDTO){

        RuleName ruleName = new RuleName();
        ruleName.setName(ruleNameDTO.getName());
        ruleName.setDescription(ruleNameDTO.getDescription());
        ruleName.setJson(ruleNameDTO.getJson());
        ruleName.setTemplate(ruleNameDTO.getTemplate());
        ruleName.setSqlStr(ruleNameDTO.getSqlStr());
        ruleName.setSqlPart(ruleNameDTO.getSqlPart());

        ruleNameRepository.save(ruleName);

    }

    @Override
    public void updateRule(RuleNameDTO ruleNameDTO) {

        RuleName ruleName = new RuleName();
        ruleName.setId(ruleNameDTO.getId());
        ruleName.setName(ruleNameDTO.getName());
        ruleName.setDescription(ruleNameDTO.getDescription());
        ruleName.setJson(ruleNameDTO.getJson());
        ruleName.setTemplate(ruleNameDTO.getTemplate());
        ruleName.setSqlStr(ruleNameDTO.getSqlStr());
        ruleName.setSqlPart(ruleNameDTO.getSqlPart());

        ruleNameRepository.save(ruleName);


    }

    @Override
    public void deleteRule(RuleNameDTO ruleNameDTO){

        RuleName ruleName = new RuleName();
        ruleName.setId(ruleNameDTO.getId());
        ruleName.setName(ruleNameDTO.getName());
        ruleName.setDescription(ruleNameDTO.getDescription());
        ruleName.setJson(ruleNameDTO.getJson());
        ruleName.setTemplate(ruleNameDTO.getTemplate());
        ruleName.setSqlStr(ruleNameDTO.getSqlStr());
        ruleName.setSqlPart(ruleNameDTO.getSqlPart());


        ruleNameRepository.delete(ruleName);

    }

    @Override
    public RuleNameDTO checkRule(Integer id){
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
        RuleNameDTO ruleNameDTO = new RuleNameDTO();

        ruleNameDTO.setId(ruleName.getId());
        ruleNameDTO.setName(ruleName.getName());
        ruleNameDTO.setDescription(ruleName.getDescription());
        ruleNameDTO.setJson(ruleName.getJson());
        ruleNameDTO.setTemplate(ruleName.getTemplate());
        ruleNameDTO.setSqlStr(ruleName.getSqlStr());
        ruleNameDTO.setSqlPart(ruleName.getSqlPart());


        return ruleNameDTO;
    }
}
