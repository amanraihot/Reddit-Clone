package com.example.demo.model;

import lombok.Data;
import lombok.Generated;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.TypeResolutionStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name="vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "voteId")
    private long voteId;

    private VoteType voteType;

    @ManyToOne(fetch = LAZY)
    @NotNull
    @JoinColumn(name = "postId",referencedColumnName = "postId")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="userId",referencedColumnName = "userId")
    private User user;

}
