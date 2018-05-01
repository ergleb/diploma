package com.ergleb.twitterpredictions.database.mongo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.Tweet;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "tweets")
public class AnalyzedTweet {

    @Id
    private String id;

    private long tweetId;
    private String text;
    private List<HashTagEntity> hashTags;
    private String userId;
    private Date createdAt;

    //private Tweet tweet;
    private double positive;
    private double negative;
    private double neutral;
    private double compound;
}
