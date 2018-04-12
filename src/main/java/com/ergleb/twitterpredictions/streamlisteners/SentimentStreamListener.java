package com.ergleb.twitterpredictions.streamlisteners;

import com.vader.sentiment.analyzer.SentimentAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;

import java.io.IOException;

public class SentimentStreamListener implements StreamListener {
    @Override
    public void onTweet(Tweet tweet) {
        try {
            log.info("Tweet: {}", tweet.getText());
            SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer(tweet.getText());
            sentimentAnalyzer.analyze();
            log.info("polarity: {}", sentimentAnalyzer.getPolarity());
        } catch (IOException ex) {
            log.error("Error while creating sentimentAnalyzer object");
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

    public static final Logger log = LoggerFactory.getLogger(SentimentStreamListener.class);
}
