package com.ergleb.twitterpredictions.streamlisteners;

import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;

import java.util.*;

public class ListStreamListener implements StreamListener {

    private List<String> tweets = new ArrayList<>();

    @Override
    public void onTweet(Tweet tweet) {
        tweets.add(tweet.getText());
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
