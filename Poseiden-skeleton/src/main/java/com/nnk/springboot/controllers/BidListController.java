package com.nnk.springboot.controllers;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListController {

    @Autowired
    private BidListService bidListService ;

    private static final Logger logger = LogManager.getLogger(BidListController.class);



    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        logger.debug("bidList");
        List<BidListDTO> bidListDTO = bidListService.bidList();
        model.addAttribute("bidListDTO", bidListDTO);


        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidListDTO bid) {
        logger.debug("bidList/addBidForm");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListDTO bidListDTO, BindingResult result, Model model) {
        logger.debug("bidList/validate");
        if (!result.hasErrors()) {
            bidListService.checkBidByAccount(bidListDTO.getAccount());
            bidListService.addBid(bidListDTO);
            model.addAttribute("bidListDTO", bidListService.bidList());
            return "redirect:/bidList/list";
        }

        logger.error("validate error for id"+bidListDTO.getId());
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("bidList/updateForm");
        BidListDTO bidListDTO = bidListService.checkBid(id);
        model.addAttribute("bidListDTO", bidListDTO);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDTO bidListDTO,
                             BindingResult result, Model model) {

        logger.debug("bidList/update");
        if (result.hasErrors()) {
            logger.error("update error for id"+id);
            return "bidList/update";
        }

        bidListService.updateBid(bidListDTO);

        model.addAttribute("bidListDTO", bidListService.bidList());

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        logger.debug("bidList/delete");
        BidListDTO bidListDTO = bidListService.checkBid(id);
        bidListService.deleteBid(bidListDTO);

        model.addAttribute("bidList", bidListService.bidList());

        return "redirect:/bidList/list";
    }
}
