package com.example.userservicemwfeve.services;

import com.example.userservicemwfeve.models.Token;
import com.example.userservicemwfeve.models.User;
import com.example.userservicemwfeve.repositories.TokenRepository;
import com.example.userservicemwfeve.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.PrivateKey;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


@Service
public class UserService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //When  we  try to autowire the becryptpasswordencoder
    //it  will throw  an  error so we need  to create the  bean for this
    public UserService(UserRepository  userRepository, BCryptPasswordEncoder  bCryptPasswordEncoder, TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }
    public User signUp(String fullname, String  email, String  password ){
        User  u = new User();
        u.setEmail(email);
        u.setName(fullname);
        u.setHashPassword(bCryptPasswordEncoder.encode(password));
        User user = userRepository.save(u);

        return  user;
    }

    public Token login(String email,String password){
        Optional<User>  userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            // throw  user not  exists Exception
            return  null;
        }

        User  user  = userOptional.get(); // to get the  encoded password
        if(!bCryptPasswordEncoder.matches(password,user.getHashPassword())){
            //throw  password not found  exception
            return  null;
        }

        LocalDate today  = LocalDate.now();
        LocalDate thirtyDayslater = today.plus(30, ChronoUnit.DAYS);
        //convert local date  to date
        java.util.Date expiryDate  = Date.from(thirtyDayslater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Token  token = new Token();
        token.setUser(user);
        token.setExpiryAt(expiryDate);
        token.setValue(RandomStringUtils.random(128));
        // TODO: convert  the  above  token  to JWT token
        Token  savedtoken  = tokenRepository.save(token);
        return savedtoken;
    }

    public  void logout(String token ){
        Optional<Token>  token1  = tokenRepository.findByValueAndDeletedEqualsAndExpiryAtGreaterThan(token,false, new java.util.Date());
        if( token1.isEmpty()){
            //throw TokenNotExistsOrAlreadyExpiredException();
            return;
        }

        Token tkn  = token1.get();
        tkn.setDeleted(true);
        tokenRepository.save(tkn);
        return;
    }


    public User validateToken( String token){
        Optional<Token> tkn  = tokenRepository.findByValueAndDeletedEqualsAndExpiryAtGreaterThan(token,false, new java.util.Date());
        if(tkn.isEmpty()){
            return null;
        }

        // TODO:2 Instead  of validating  via the  DB  as  the  TOken is  now  a  JWT
        // TODO : Validate the  token using  JWT
        // TODO 3: Jwt  wont be  able to handle  log out(jWT  ONLY HAS  eXPIRY AT AND  USER  id)
        // JUt DOESNOT  KNOW  WHETHER  USER  HAS  ALREADY LOGED  OUT
        //  IF YOU ARE  USING  SELF  VALIDATING  jwt  WILL YOU BE  ABLE  TO HANDLE  lOG OUTS
        //  No ( because token may be  valid As per  JWT its  expiry date may be  greate then curren  date
        // but  just  person clicked on logout ( this is  going to be  con of Self validating  JWT)
        return tkn.get().getUser();
    }
}
