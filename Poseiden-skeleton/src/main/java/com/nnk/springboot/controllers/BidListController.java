package com.nnk.springboot.controllers;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.services.BidListService;
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
    // TODO: Inject Bid service

    @Autowired
    private BidListService bidListService ;



    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view
        List<BidListDTO> bidListDTO = bidListService.bidList();
        model.addAttribute("bidListDTO", bidListDTO);


        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidListDTO bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListDTO bidListDTO, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        if (!result.hasErrors()) {
            bidListService.addBid(bidListDTO);
            model.addAttribute("bidListDTO", bidListService.bidList());
            return "redirect:/bidList/list";
        }


        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidListDTO bidListDTO = bidListService.checkBid(id);
        model.addAttribute("bidListDTO", bidListDTO);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDTO bidListDTO,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid

        if (result.hasErrors()) {
            return "bidList/update";
        }

        bidListService.updateBid(bidListDTO);

        model.addAttribute("bidListDTO", bidListService.bidList());

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list

        BidListDTO bidListDTO = bidListService.checkBid(id);
        bidListService.deleteBid(bidListDTO);

        model.addAttribute("bidList", bidListService.bidList());

        return "redirect:/bidList/list";
    }
}
