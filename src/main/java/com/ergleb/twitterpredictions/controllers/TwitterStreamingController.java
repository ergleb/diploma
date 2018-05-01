package com.ergleb.twitterpredictions.controllers;


import com.ergleb.twitterpredictions.services.StreamingService;
import com.ergleb.twitterpredictions.streamlisteners.ListStreamListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("")
    public String stream(Model model, @RequestParam String filter) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }
        log.debug("filter: {}", filter);

        try {
            model.addAttribute("filter", filter);
            List<StreamListener> listeners = streamingService.stream(filter);
            for (StreamListener listener : listeners) {
                if (listener instanceof ListStreamListener) {
                    model.addAttribute("tweets", ((ListStreamListener) listener).getTweets());
                }
            }
        } catch (Exception ex) {
            log.error("exc: {}", ex);
        }


        return "stream";
    }

    @RequestMapping("/active")
    public String activeStreams (Model model) {
        Map<String, String> streamListeners = streamingService.getListeners();
        model.addAttribute("streamListeners", streamListeners);
        return "active";
    }

    private static final Logger log = LoggerFactory.getLogger(TwitterStreamingController.class);
}
