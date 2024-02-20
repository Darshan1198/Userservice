package com.example.userservicemwfeve.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import javax.xml.crypto.Data;
import java.util.Date;


@Setter
@Getter
@Entity
public class Token extends BaseModel {
    private String   value;

    @ManyToOne
    private User  user;

    private Date expiryAt;

}
