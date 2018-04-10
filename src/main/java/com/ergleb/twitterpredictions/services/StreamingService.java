package com.ergleb.twitterpredictions.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.FilterStreamParameters;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Twitter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamingService {

    private Map<String, List<StreamListener>> listenersMap = new HashMap<>();

    public void stream (String filterWords, List<StreamListener> listeners) {
        if (!listenersMap.containsKey(filterWords)) {
            twitter.streamingOperations().filter(filterWords, listeners);
            listenersMap.put(filterWords, listeners);
        }
    }

    public List<StreamListener> getListeners (String filter) {
        return listenersMap.get(filter);
    }

    public boolean streamExists (String filter) {
        return listenersMap.containsKey(filter);
    }

    @Autowired
    private Twitter twitter;

    @Autowired
    private ConnectionRepository connectionRepository;

}
