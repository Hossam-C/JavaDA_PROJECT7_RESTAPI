package com.nnk.springboot.services.impl;


import com.nnk.springboot.DTO.UserDTO;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> userList(){

        logger.debug("userList");

        List<UserDTO>  userListDTO = new ArrayList<>();

        List<User> userlist = userRepository.findAll();

        for ( User ulist : userlist) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(ulist.getId());
            userDTO.setUsername(ulist.getUsername());
            userDTO.setPassword(ulist.getPassword());
            userDTO.setFullname(ulist.getFullname());
            userDTO.setRole(ulist.getRole());
            userListDTO.add(userDTO);
        }


        return userListDTO;
    }

    @Override
    public void addUser(UserDTO userDTO){

        logger.debug("addUser");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setFullname(userDTO.getFullname());
        user.setRole(userDTO.getRole());

        userRepository.save(user);

    }

    @Override
    public void updateUser(UserDTO userDTO) {

        logger.debug("updateUser");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();

        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setFullname(userDTO.getFullname());
        user.setRole(userDTO.getRole());

        userRepository.save(user);


    }

    @Override
    public void deleteUser(UserDTO userDTO){

        logger.debug("deleteUser");

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFullname(userDTO.getFullname());
        user.setRole(userDTO.getRole());


        userRepository.delete(user);

    }

    @Override
    public UserDTO checkUser(Integer id){

        logger.debug("checkUser");

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword("");
        userDTO.setFullname(user.getFullname());
        userDTO.setRole(user.getRole());


        return userDTO;
    }

}
