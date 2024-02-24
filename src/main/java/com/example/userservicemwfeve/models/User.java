package com.example.userservicemwfeve.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;


@Entity
@Getter
@Setter
public class User extends BaseModel {
    private String  name;

    private String  hashPassword;

    private String  email;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role>  roles;

    private boolean isEmailVerified;



}

