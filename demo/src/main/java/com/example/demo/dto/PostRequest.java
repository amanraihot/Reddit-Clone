package com.example.demo.dto;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequest {

    private long id;
    private String subreddit;
    private String title;
    private String url;
    private  String body;
}
