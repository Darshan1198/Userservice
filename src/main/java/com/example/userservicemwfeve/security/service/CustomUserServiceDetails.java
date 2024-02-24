package com.example.userservicemwfeve.security.service;

import com.example.userservicemwfeve.models.CustomUserDetails;
import com.example.userservicemwfeve.models.User;
import com.example.userservicemwfeve.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserServiceDetails implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserServiceDetails(UserRepository  userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        if(userOptional.isEmpty()){
            throw  new UsernameNotFoundException("User by  email: " +username+"doesn't exists");
        }

        CustomUserDetails userDetails = new CustomUserDetails(userOptional.get()); // create the  custom  userDetails
        return  userDetails;


    }
}
