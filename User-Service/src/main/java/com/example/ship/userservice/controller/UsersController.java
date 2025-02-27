package com.example.ship.userservice.controller;


import com.example.ship.userservice.DTO.UserResponseDTO;
import com.example.ship.userservice.model.Users;
import com.example.ship.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {


    private final UserService userService;
    private final ModelMapper mapper;
    @Autowired
    public UsersController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        Users users= userService.getUserById(id);
        UserResponseDTO userResponseDTO = mapper.map(users,UserResponseDTO.class);
        return ResponseEntity.ok(userResponseDTO) ;
    }

}
