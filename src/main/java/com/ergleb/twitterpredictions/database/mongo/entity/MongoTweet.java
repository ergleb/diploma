package com.ergleb.twitterpredictions.database.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.social.twitter.api.Entities;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MongoTweet {

    @Id
    private String mongoId;

    private long id;
    private String idStr;
    private String text;
    private Date createdAt;
    private String fromUser;
    private String profileImageUrl;
    private Long toUserId;
    private Long inReplyToStatusId;
    private Long inReplyToUserId;
    private String inReplyToScreenName;
    private long fromUserId;
    private String languageCode;
    private String source;
    private Integer retweetCount;
    private boolean retweeted;
    private Tweet retweetedStatus;
    private boolean favorited;
    private Integer favoriteCount;
    private Entities entities;
    private TwitterProfile user;
}
