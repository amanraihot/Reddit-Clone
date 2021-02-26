package com.example.demo.model;


import com.sun.istack.Nullable;
import lombok.*;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.ManyToAny;
import static javax.persistence.FetchType.LAZY;


import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "postId")
    private long id;

    @Column(name="title")
    @NonNull
    private String title;

    @Column(name="url")
    @Nullable
    private String url;

    @Column(name="body")
    @Nullable
    private String  body;

    @ManyToOne(fetch= LAZY)
    @JoinColumn(name = "userId" ,referencedColumnName = "userId")
    private  User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="subredditId",referencedColumnName = "subredditId")
    private  Subreddit subreddit;

    private Instant createdDate;

}
