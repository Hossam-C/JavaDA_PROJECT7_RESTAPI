package com.nnk.springboot.controllers;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.TradeDTO;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {

    @Autowired
    private TradeService tradeService;

    private static final Logger logger = LogManager.getLogger(TradeController.class);

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        logger.debug("tradeList");
        List<TradeDTO> trade = tradeService.tradeList();
        model.addAttribute("trade", trade);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade(TradeDTO trade) {

        logger.debug("trade/addTradeForm");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid TradeDTO tradeDTO, BindingResult result, Model model) {

        logger.debug("trade/validate");
        if (!result.hasErrors()) {
            tradeService.addTrade(tradeDTO);
            model.addAttribute("tradeDTO", tradeService.tradeList());
            return "redirect:/trade/list";
        }
        logger.error("validate error for id"+tradeDTO.getTradeId());
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("trade/updateForm");
        TradeDTO tradeDTO = tradeService.checkTrade(id);
        model.addAttribute("tradeDTO", tradeDTO);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDTO tradeDTO,
                             BindingResult result, Model model) {
        logger.debug("trade/update");
        if (result.hasErrors()) {
            logger.error("update error for id"+id);
            return "trade/update";
        }

        tradeService.updateTrade(tradeDTO);

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.debug("trade/delete");
        TradeDTO tradeDTO = tradeService.checkTrade(id);
        tradeService.deleteTrade(tradeDTO);

        model.addAttribute("trade", tradeService.tradeList());

        return "redirect:/trade/list";
    }
}
