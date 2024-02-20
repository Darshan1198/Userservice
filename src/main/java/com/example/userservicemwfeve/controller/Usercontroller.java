package com.example.userservicemwfeve.controller;


import com.example.userservicemwfeve.dtos.LoginRequestDto;
import com.example.userservicemwfeve.dtos.LogoutRequestDto;
import com.example.userservicemwfeve.dtos.SignUpRequestDto;
import com.example.userservicemwfeve.dtos.UserDto;
import com.example.userservicemwfeve.models.Token;
import com.example.userservicemwfeve.models.User;
import com.example.userservicemwfeve.services.UserService;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.event.spi.PreInsertEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class Usercontroller {

    private UserService userService;
    public Usercontroller(UserService  userService){
        this.userService = userService;
    }
    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto  requestDto){
        // check  if email and  password in DB
        // if  yes  return  user
        // else  throw  some  error
        return userService.login(requestDto.getEmail(),requestDto.getPassword());
    }
    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto request){
        //no need to hashpassword for now
        //Just store  user as is in the  DB
        // for  Now  no  need  to have  email  verification  either
        String  email  = request.getEmail();
        String  password  = request.getPassword();
        String  name = request.getName();
        return UserDto.from(userService.signUp(name, email,password));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logOut (@RequestBody LogoutRequestDto request){
        // delete token if  exists -->200
        // if  doesn't exist give  404
        userService.logout(request.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token")  String token){
        return UserDto.from(userService.validateToken(token));
    }

}
