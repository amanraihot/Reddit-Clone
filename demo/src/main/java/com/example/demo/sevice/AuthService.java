package com.example.demo.sevice;

import com.example.demo.Exception.SpringRedditException;
import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.NotificationEmail;
import com.example.demo.model.VerificationToken;
import com.example.demo.repo.UserRepo;
import com.example.demo.model.User;
import com.example.demo.repo.VerficationRepo;
import com.example.demo.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VerficationRepo verifyRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailservice;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    public void signup(RegisterRequest registerRequest)
    {
       User user = new User();
       user.setUsername(registerRequest.getUsername());
       user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
       user.setEmail(registerRequest.getEmail());
       user.setCreated(Instant.now());
       user.setEnabled(false);
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

    public void  verifyAccount(String token)
    {
        Optional<VerificationToken> verificationToken = verifyRepo.findByToken(token);
        fetchUserandEnable(verificationToken.orElseThrow(()-> new SpringRedditException("Invalid Token")) );
    }

    public void   fetchUserandEnable(VerificationToken verificationToken)
    {
        String username = verificationToken.getUser().getUsername();
        User user = userRepo.findByUsername(username).orElseThrow(()->new SpringRedditException("User Not found"));
        user.setEnabled(true);
        userRepo.save(user);
    }
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .username(loginRequest.getUsername())
                .build();
    }
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepo.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }


}
