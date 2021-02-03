package com.nnk.springboot.controllers;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.RatingDTO;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingController {

    @Autowired
    private RatingService ratingService;

    private static final Logger logger = LogManager.getLogger(RatingController.class);

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        logger.debug("ratingList");
        List<RatingDTO> rating = ratingService.ratingList();
        model.addAttribute("rating", rating);

        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(RatingDTO ratingDTO) {

        logger.debug("rating/addRateForm");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid RatingDTO ratingDTO, BindingResult result, Model model) {
        logger.debug("rating/validate");
        if (!result.hasErrors()) {
            ratingService.addRating(ratingDTO);
            model.addAttribute("ratingDTO", ratingService.ratingList());
            return "redirect:/rating/list";
        }
        logger.error("validate error for id"+ratingDTO.getId());
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("rating/updateForm");
        RatingDTO ratingDTO = ratingService.checkRating(id);
        model.addAttribute("ratingDTO", ratingDTO);

        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDTO ratingDTO,
                             BindingResult result, Model model) {

        logger.debug("rating/update");
        if (result.hasErrors()) {
            logger.error("update error for id"+id);
            return "rating/update";
        }

        ratingService.updateRating(ratingDTO);

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.debug("rating/delete");
        RatingDTO ratingDTO = ratingService.checkRating(id);
        ratingService.deleteRating(ratingDTO);

        model.addAttribute("rating", ratingService.ratingList());

        return "redirect:/rating/list";
    }
}
