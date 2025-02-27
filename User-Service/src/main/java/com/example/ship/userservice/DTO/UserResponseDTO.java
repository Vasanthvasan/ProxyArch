package com.example.ship.userservice.DTO;


import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;


@ToString
@Data
public class UserResponseDTO {


    private String email;
    private String firstName;
}
