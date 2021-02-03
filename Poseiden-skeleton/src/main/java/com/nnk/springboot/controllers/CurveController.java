package com.nnk.springboot.controllers;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.CurvePointDTO;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {

    @Autowired
    private CurvePointService curvePointService;

    private static final Logger logger = LogManager.getLogger(CurveController.class);

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        logger.debug("curvePointList");
        List<CurvePointDTO> curveList = curvePointService.curvePointList();
        model.addAttribute("curvePoint", curveList);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePointDTO curve) {

        logger.debug("curveList/addCurveForm");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePointDTO curvePointDTO, BindingResult result, Model model) {
        logger.debug("curvePoint/validate");
        if (!result.hasErrors()) {
            curvePointService.addCurve(curvePointDTO);
            model.addAttribute("curvePointDTO", curvePointService.curvePointList());
            return "redirect:/curvePoint/list";
        }
        logger.error("validate error for id"+curvePointDTO.getId());
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("curvePoint/addUpdateForm");
        CurvePointDTO curvePointDTO = curvePointService.checkCurve(id);
        model.addAttribute("curvePointDTO", curvePointDTO);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePointDTO curvePointDTO,
                             BindingResult result, Model model) {
        logger.debug("curvePoint/update");
        if (result.hasErrors()) {
            logger.error("update error for id"+id);
            return "curvePoint/update";
        }

        curvePointService.updateCurve(curvePointDTO);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {

        logger.debug("curvePoint/delete");
        CurvePointDTO curvePointDTO = curvePointService.checkCurve(id);
        curvePointService.deleteCurve(curvePointDTO);

        model.addAttribute("curvePointDTO", curvePointService.curvePointList());
        return "redirect:/curvePoint/list";
    }
}
