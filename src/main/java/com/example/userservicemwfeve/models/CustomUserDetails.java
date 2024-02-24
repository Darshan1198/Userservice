package com.example.userservicemwfeve.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@JsonDeserialize
public class CustomUserDetails implements UserDetails {

//    private  User  user;
    private  List<CustomGrantedAuthority>  authorities;

    //Instead  of  user.getHashPassword();
    private   String  password;

    private   String  username;
    private   boolean  accountNonExpired;

    private   boolean accountNonLocked;
    private   boolean  credentialsNonExpired;

    private  boolean  enabled ;



    public  CustomUserDetails(){

    }

    public   CustomUserDetails  (User user){
//         this.user  = user;
        this.accountNonExpired  = true;
        this.accountNonLocked = true;
        this.enabled  = true;
        this.credentialsNonExpired  = true;
        this.password  = user.getHashPassword();
        this.username   =user.getEmail();

        List<CustomGrantedAuthority> grantedAuthorities  = new ArrayList<>();
        for (Role role: user.getRoles()){
            grantedAuthorities.add(new CustomGrantedAuthority(role));
        }

        this.authorities  = grantedAuthorities;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<CustomGrantedAuthority> grantedAuthorities  = new ArrayList<>();
//        for (Role role: user.getRoles()){
//            grantedAuthorities.add(new CustomGrantedAuthority(role));
//        }
//        return  grantedAuthorities;
        return   authorities;
    }

    @Override
    public String getPassword() {
//        return user.getHashPassword();
        return   password;

    }

    @Override
    public String getUsername() {
//        return user.getEmail();
        return  username;

    }

    @Override
    public boolean isAccountNonExpired() {
//        return true; //always  return  true
        return   accountNonExpired;

    }

    @Override
    public boolean isAccountNonLocked() {
//        return true;
        return   accountNonLocked;

    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return true;  //password  expired?
        return  credentialsNonExpired;

    }

    @Override
    public boolean isEnabled() {
//        return true;
        return  enabled;

    }
}
