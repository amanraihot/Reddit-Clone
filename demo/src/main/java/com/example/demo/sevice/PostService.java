package com.example.demo.sevice;

import com.example.demo.Exception.SubredditNotFoundException;
import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.model.Post;
import com.example.demo.model.Subreddit;
import com.example.demo.repo.PostRepo;
import com.example.demo.repo.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private AuthService authService;

    @Autowired
    private SubredditRepository subredditRepository;

    public PostResponse save(PostRequest postRequest)
    {
        System.out.println(postRequest.getSubreddit());
        Subreddit subreddit =  subredditRepository.findByName(postRequest.getSubreddit())
                .orElseThrow(()->new SubredditNotFoundException(postRequest.getSubreddit()));
        Post post = new Post();
        post.setSubreddit(subreddit);
        post.setTitle(postRequest.getTitle());
        post.setUrl(postRequest.getUrl());
        post.setBody(postRequest.getBody());
        post.setUser(authService.getCurrentUser());
        post.setCreatedDate(Instant.now());
        postRepo.save(post);
        return new PostResponse(post.getSubreddit().getName(),post.getTitle(),post.getBody());

    }
}
