package com.ergleb.twitterpredictions.services;

import com.ergleb.twitterpredictions.scheduling.TwitterScheduler;
import com.ergleb.twitterpredictions.streamlisteners.ListStreamListener;
import com.ergleb.twitterpredictions.streamlisteners.SentimentStreamListener;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StreamingService {

    @Getter
    private Map<String, List<StreamListener>> listenersMap = new HashMap<>();

    public List<StreamListener> stream(String filterWords) {

        if (!listenersMap.containsKey(filterWords)) {
            List<StreamListener> streamListeners = new ArrayList<>();
            SentimentStreamListener sentimentStreamListener = new SentimentStreamListener(twitterScheduler);
            streamListeners.add(sentimentStreamListener);
            streamListeners.add(new ListStreamListener(10));
            //streamListeners.add(new LogStreamListener());
            log.debug("SentimentSL: {}", sentimentStreamListener);
            twitter.streamingOperations().filter(filterWords, streamListeners);
            listenersMap.put(filterWords, streamListeners);
            return streamListeners;
        } else {
            return listenersMap.get(filterWords);
        }
    }

    public Map<String, String> getListeners () {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, List<StreamListener>> entry: listenersMap.entrySet()) {
            for (StreamListener listener : entry.getValue()) {
                if (listener instanceof ListStreamListener) {
                    List<Tweet> tweets = ((ListStreamListener) listener).getTweets();
                    String tweetText = "";
                    if (tweets.size() > 1) {
                        tweetText = tweets.get(tweets.size() - 1).getText();
                    }
                    result.put(entry.getKey(), tweetText);
                }
            }
        }
        return result;
    }

    public List<StreamListener> getListeners(String filter) {
        return listenersMap.get(filter);
    }

    public boolean streamExists(String filter) {
        return listenersMap.containsKey(filter);
    }

    private Twitter twitter;

    private TwitterScheduler twitterScheduler;

    @Inject
    protected StreamingService(Twitter twitter, TwitterScheduler twitterScheduler) {
        this.twitter = twitter;
        this.twitterScheduler = twitterScheduler;
    }

    public static final Logger log = LoggerFactory.getLogger(StreamingService.class);

}
