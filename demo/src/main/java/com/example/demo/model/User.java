package com.example.demo.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="userId")
    private long userId;

    @Column(name = "username")
    @NotBlank(message = "Username is mandotry")
    private  String username;

    @Column(name="password")
    @NotBlank(message = "PASSWORD IS DAMN NECESSARY")
    private String password;

    @Email
    @NotEmpty(message = "Email is mandotry")
    private String email;

    private Instant created;

    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private Collection<Post> posts;



}
