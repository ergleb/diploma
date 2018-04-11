package com.ergleb.twitterpredictions.controllers;


import com.ergleb.twitterpredictions.services.StreamingService;
import com.ergleb.twitterpredictions.streamlisteners.ListStreamListener;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/twitter/stream")
public class TwitterStreamingController {

    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    private StreamingService streamingService;

    @Inject
    public TwitterStreamingController(Twitter twitter, ConnectionRepository connectionRepository, StreamingService streamingService) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
        this.streamingService = streamingService;
    }

    @RequestMapping(value = "/{filter}")
    public String stream(Model model, @PathVariable String filter) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }


        model.addAttribute("filter", filter);
        List<StreamListener> listeners = streamingService.stream(filter);
        for (StreamListener listener : listeners) {
            if (listener instanceof ListStreamListener) {
                model.addAttribute("tweets", ((ListStreamListener) listener).getTweets());
            }
        }

        return "stream";
    }
}
