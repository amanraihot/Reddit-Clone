package com.example.demo.model;

import com.sun.xml.bind.v2.schemagen.episode.Package;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Builder
@Table(name="subreddit")
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="subredditId")
    private long subredditId;

    @Column(name="name")
    @NotBlank(message ="Community name is mandotry")
    private String name;

    @Column(name="body")
    @NotBlank(message ="Whats the community all about?")
    private String body;

    @OneToMany(fetch = LAZY,mappedBy = "subreddit")
    private List<Post> posts;

    @ManyToOne
    private User user;

    private Instant createdDate;


}
