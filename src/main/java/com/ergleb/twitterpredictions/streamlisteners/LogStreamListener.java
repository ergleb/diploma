package com.ergleb.twitterpredictions.streamlisteners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;

public class LogStreamListener implements StreamListener {
    private static final Logger log = LoggerFactory.getLogger(LogStreamListener.class);

    @Override
    public void onTweet(Tweet tweet) {
        log.debug("New Tweet: {}", tweet.getText());
    }

    @Override
    public void onDelete(StreamDeleteEvent deleteEvent) {

    }

    @Override
    public void onLimit(int numberOfLimitedTweets) {

    }

    @Override
    public void onWarning(StreamWarningEvent warningEvent) {
        log.warn("Warning: {}", warningEvent.getMessage());
    }
}
