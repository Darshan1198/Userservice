package com.example.userservicemwfeve.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogoutRequestDto {
    private String token; // for  which  token  to log out
}
