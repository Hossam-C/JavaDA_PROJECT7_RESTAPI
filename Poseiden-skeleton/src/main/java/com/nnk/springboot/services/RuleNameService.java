package com.nnk.springboot.services;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.RuleNameDTO;

import java.util.List;

public interface RuleNameService {

    public List<RuleNameDTO> ruleList();

    public void addRule(RuleNameDTO ruleNameDTO);

    public void updateRule(RuleNameDTO ruleNameDTO);

    public void deleteRule(RuleNameDTO ruleNameDTO);

    public RuleNameDTO checkRule(Integer id);
}
