package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name="token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="tokenId")
    private long id;

    @Column(name="tokenUrl")
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private Instant created;
    
}
