package com.nnk.springboot.controllers;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.CurvePointDTO;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
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
    // TODO: Inject Curve Point service

    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        // TODO: find all Curve Point, add to model
        List<CurvePointDTO> curveList = curvePointService.curvePointList();
        model.addAttribute("curvePoint", curveList);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePointDTO curve) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePointDTO curvePointDTO, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        if (!result.hasErrors()) {
            curvePointService.addCurve(curvePointDTO);
            model.addAttribute("curvePointDTO", curvePointService.curvePointList());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePointDTO curvePointDTO = curvePointService.checkCurve(id);
        model.addAttribute("curvePointDTO", curvePointDTO);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePointDTO curvePointDTO,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        curvePointService.updateCurve(curvePointDTO);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list

        CurvePointDTO curvePointDTO = curvePointService.checkCurve(id);
        curvePointService.deleteCurve(curvePointDTO);

        model.addAttribute("curvePointDTO", curvePointService.curvePointList());
        return "redirect:/curvePoint/list";
    }
}
