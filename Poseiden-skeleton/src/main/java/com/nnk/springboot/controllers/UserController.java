package com.nnk.springboot.controllers;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.UserDTO;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        List<UserDTO> userDTO = userService.userList();
        model.addAttribute("userDTO", userDTO);
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(UserDTO user) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid UserDTO userDTO, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userService.addUser(userDTO);
            model.addAttribute("userDTO", userService.userList());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        UserDTO userDTO = userService.checkUser(id);
        model.addAttribute("userDTO", userDTO);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDTO userDTO,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        userService.updateUser(userDTO);
        model.addAttribute("userDTO", userService.userList());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        UserDTO userDTO = userService.checkUser(id);
        userService.deleteUser(userDTO);
        model.addAttribute("userDTO", userService.userList());
        return "redirect:/user/list";
    }
}
