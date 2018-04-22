package com.ergleb.twitterpredictions.database.mongo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.social.twitter.api.Tweet;

@Getter
@Setter
@ToString(exclude = "tweet")
@Document(collection = "tweets")
public class AnalyzedTweet {

    @Id
    private String id;

    private Tweet tweet;
    private double positive;
    private double negative;
    private double neutral;
    private double compound;
}
