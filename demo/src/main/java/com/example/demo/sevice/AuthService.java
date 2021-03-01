package com.example.demo.sevice;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.NotificationEmail;
import com.example.demo.model.VerificationToken;
import com.example.demo.repo.UserRepo;
import com.example.demo.model.User;
import com.example.demo.repo.VerficationRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VerficationRepo verifyRepo;

    private MailService mailservice;


    public void signup(RegisterRequest registerRequest)
    {
       User user = new User();
       user.setUsername(registerRequest.getUsername());
       user.setPassword(registerRequest.getPassword());
       user.setEmail(registerRequest.getEmail());
       user.setCreated(Instant.now());
       userRepo.save(user);
       String token = generateVerificationToken(user);
       mailservice.sendMail(new NotificationEmail("Please Activate your Account", user.getEmail(),
               "Please click here: " +  "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    public String generateVerificationToken(User user)
    {
        String verificationtoken = UUID.randomUUID().toString();
        VerificationToken verify = new VerificationToken();
        verify.setUser(user);
        verify.setToken(verificationtoken);
        verifyRepo.save(verify);
        return verificationtoken;

    }
}
