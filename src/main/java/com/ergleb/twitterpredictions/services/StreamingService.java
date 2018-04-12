package com.ergleb.twitterpredictions.services;

import com.ergleb.twitterpredictions.streamlisteners.ListStreamListener;
import com.ergleb.twitterpredictions.streamlisteners.LogStreamListener;
import com.ergleb.twitterpredictions.streamlisteners.SentimentStreamListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class StreamingService {

    private Map<String, List<StreamListener>> listenersMap = new HashMap<>();

    public List<StreamListener> stream(String filterWords) {

        if (!listenersMap.containsKey(filterWords)) {
            List<StreamListener> streamListeners = new ArrayList<>();
            //streamListeners.add(new LogStreamListener());
            streamListeners.add(new ListStreamListener(10));
            streamListeners.add(new SentimentStreamListener());

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

    @Autowired
    private Twitter twitter;

    @Autowired
    private ConnectionRepository connectionRepository;

}
