package com.nnk.springboot.controllers;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.TradeDTO;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {
    // TODO: Inject Trade service

    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        // TODO: find all Trade, add to model
        List<TradeDTO> trade = tradeService.tradeList();
        model.addAttribute("trade", trade);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade(TradeDTO trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid TradeDTO tradeDTO, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Trade list

        if (!result.hasErrors()) {
            tradeService.addTrade(tradeDTO);
            model.addAttribute("tradeDTO", tradeService.tradeList());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form
        TradeDTO tradeDTO = tradeService.checkTrade(id);
        model.addAttribute("tradeDTO", tradeDTO);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDTO tradeDTO,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list

        if (result.hasErrors()) {
            return "trade/update";
        }

        tradeService.updateTrade(tradeDTO);

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list

        TradeDTO tradeDTO = tradeService.checkTrade(id);
        tradeService.deleteTrade(tradeDTO);

        model.addAttribute("trade", tradeService.tradeList());

        return "redirect:/trade/list";
    }
}
