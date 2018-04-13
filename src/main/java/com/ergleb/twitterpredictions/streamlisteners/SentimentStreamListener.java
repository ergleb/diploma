package com.ergleb.twitterpredictions.streamlisteners;

import com.ergleb.twitterpredictions.scheduling.TwitterScheduler;
import com.vader.sentiment.analyzer.SentimentAnalyzer;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SentimentStreamListener implements StreamListener {

    @Getter
    @Setter
    private Map<Tweet, Map<String, Float>> tweets = new HashMap<>();

    @Override
    public void onTweet(Tweet tweet) {
        try {
            SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer(tweet.getText());
            sentimentAnalyzer.analyze();
            tweets.put(tweet, sentimentAnalyzer.getPolarity());
        } catch (IOException ex) {
            log.error("Error while creating sentimentAnalyzer object");
        } catch (NullPointerException ex) {
            log.error("NPE: {}", Arrays.asList(ex.getStackTrace()));
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

    @Scheduled(fixedDelay = 10000L)
    private void cleanTweets () {
        log.info("Scheduled op, tweets with polarity: {}", tweets);
        tweets = new HashMap<>();
    }

    public static final Logger log = LoggerFactory.getLogger(SentimentStreamListener.class);
}
