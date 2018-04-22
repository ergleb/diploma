package com.ergleb.twitterpredictions.streamlisteners;

import com.ergleb.twitterpredictions.scheduling.TwitterScheduler;
import com.vader.sentiment.analyzer.SentimentAnalyzer;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class SentimentStreamListener implements StreamListener {

    @Getter
    @Setter
    private Map<Tweet, Map<String, Float>> tweets = new HashMap<>();

    private SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();

    private TwitterScheduler twitterScheduler;

    @Inject
    public SentimentStreamListener(TwitterScheduler twitterScheduler) {
        this.twitterScheduler = twitterScheduler;
    }

    @Override
    public void onTweet(Tweet tweet) {
        try {
            log.trace("onTweet start");
            log.trace("Tweet's text: {}", tweet.getText());
            if (tweet.getLanguageCode().equalsIgnoreCase("en")) {
                sentimentAnalyzer.setInputString(tweet.getText());
                sentimentAnalyzer.setInputStringProperties();
                sentimentAnalyzer.analyze();
                log.debug("tweet: {}, \n polarity: {}", tweet, sentimentAnalyzer.getPolarity());
                twitterScheduler.getTweets().put(tweet, sentimentAnalyzer.getPolarity());
            }
            log.trace("onTweet end");
        } catch (Exception ex) {
            log.error("error: {}", ex);
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
        log.warn("Warning: {}", warningEvent.getMessage());
    }

    public static final Logger log = LoggerFactory.getLogger(SentimentStreamListener.class);
}
