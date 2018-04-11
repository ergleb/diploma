package com.ergleb.twitterpredictions.streamlisteners;

import lombok.Getter;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;

import java.util.ArrayList;
import java.util.List;

public class ListStreamListener implements StreamListener {

    @Getter
    private List<Tweet> tweets = new ArrayList<>();
    private final int limit;

    public ListStreamListener(int limit) {
        this.limit = limit;
    }

    @Override
    public void onTweet(Tweet tweet) {
        tweets.add(tweet);
        if (tweets.size() > limit) {
            tweets.remove(0);
        }
    }

    @Override
    public void onDelete(StreamDeleteEvent deleteEvent) {

    }

    @Override
    public void onLimit(int numberOfLimitedTweets) {

    }

    @Override
    public void onWarning(StreamWarningEvent warningEvent) {

    }


}
