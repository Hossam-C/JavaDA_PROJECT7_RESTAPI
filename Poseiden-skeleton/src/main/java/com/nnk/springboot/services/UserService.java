package com.nnk.springboot.services;

import com.nnk.springboot.DTO.BidListDTO;
import com.nnk.springboot.DTO.UserDTO;
import com.nnk.springboot.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    public List<UserDTO> userList();

    public void addUser(UserDTO userDTO);

    public void updateUser(UserDTO userDTO);

    public void deleteUser(UserDTO userDTO);

    public UserDTO checkUser(Integer id);


}
