package com.example.demo.sevice;

import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import  com.example.demo.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
public class UserDetailServiceImp implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;

    public UserDetails loadUserByUsername(String username)
    {
        Optional<User> userOptional = userRepo.findByUsername(username);

        User user = userOptional.orElseThrow(()->new UsernameNotFoundException("No user found"));

        return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }

}
