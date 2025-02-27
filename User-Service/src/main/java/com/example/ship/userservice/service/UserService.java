package com.example.ship.userservice.service;


import com.example.ship.userservice.model.Users;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    public final Map<Long, Users> map=new HashMap<>();
    public UserService(){
        map.put(1L, new Users(1L,"jack@yopmail.com","Jackson"));
        map.put(2L, new Users(2L,"sunny@yopmail.com","sunny"));
        map.put(3L, new Users(3L,"peter@yopmail.com","peter"));
    }


    public Users getUserById(Long id){
        Users users = map.get(id);
        return users;
    }
}
