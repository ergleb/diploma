package com.ergleb.twitterpredictions.services;

import com.ergleb.twitterpredictions.scheduling.TwitterScheduler;
import com.ergleb.twitterpredictions.streamlisteners.ListStreamListener;
import com.ergleb.twitterpredictions.streamlisteners.LogStreamListener;
import com.ergleb.twitterpredictions.streamlisteners.SentimentStreamListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StreamingService {

    private Map<String, List<StreamListener>> listenersMap = new HashMap<>();

    public List<StreamListener> stream(String filterWords) {

        if (!listenersMap.containsKey(filterWords)) {
            List<StreamListener> streamListeners = new ArrayList<>();
            //streamListeners.add(new LogStreamListener());
            streamListeners.add(new ListStreamListener(10));
            SentimentStreamListener sentimentStreamListener = new SentimentStreamListener();
            twitterScheduler.setStreamListener(sentimentStreamListener);
            streamListeners.add(sentimentStreamListener);

            twitter.streamingOperations().filter(filterWords, streamListeners);
            listenersMap.put(filterWords, streamListeners);
            return streamListeners;
        } else {
            return listenersMap.get(filterWords);
        }
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
    protected StreamingService (Twitter twitter, TwitterScheduler twitterScheduler) {
        this.twitter = twitter;
        this.twitterScheduler = twitterScheduler;
    }

}
