package com.ergleb.twitterpredictions.controllers;


import com.ergleb.twitterpredictions.streamlisteners.ListStreamListener;
import com.ergleb.twitterpredictions.streamlisteners.LogStreamListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/twitter/stream")
public class TwitterStreamingController {

    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    @Inject
    public TwitterStreamingController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping (value = "/{filter}")
    public String stream (Model model, @PathVariable String filter){
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }

        List<StreamListener> streamListeners = new ArrayList<>();
        streamListeners.add(new LogStreamListener());
        streamListeners.add(new ListStreamListener());

        model.addAttribute("filter", filter);

        twitter.streamingOperations().filter(filter, streamListeners);
        return "stream";
    }
}
