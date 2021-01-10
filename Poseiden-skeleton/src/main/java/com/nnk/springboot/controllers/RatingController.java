package com.nnk.springboot.controllers;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.RatingDTO;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingController {
    // TODO: Inject Rating service

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // TODO: find all Rating, add to model
        List<RatingDTO> rating = ratingService.ratingList();
        model.addAttribute("rating", rating);

        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(RatingDTO ratingDTO) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid RatingDTO ratingDTO, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        if (!result.hasErrors()) {
            ratingService.addRating(ratingDTO);
            model.addAttribute("ratingDTO", ratingService.ratingList());
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        RatingDTO ratingDTO = ratingService.checkRating(id);
        model.addAttribute("ratingDTO", ratingDTO);

        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDTO ratingDTO,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list

        if (result.hasErrors()) {
            return "rating/update";
        }

        ratingService.updateRating(ratingDTO);

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list

        RatingDTO ratingDTO = ratingService.checkRating(id);
        ratingService.deleteRating(ratingDTO);

        model.addAttribute("rating", ratingService.ratingList());

        return "redirect:/rating/list";
    }
}
