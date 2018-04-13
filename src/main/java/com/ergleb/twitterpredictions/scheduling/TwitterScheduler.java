package com.ergleb.twitterpredictions.scheduling;

import com.ergleb.twitterpredictions.streamlisteners.SentimentStreamListener;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
@Getter
@Setter
public class TwitterScheduler {
    private static final Logger log = LoggerFactory.getLogger(TwitterScheduler.class);

    private SentimentStreamListener streamListener;

    @Scheduled(fixedDelay = 5000L)
    private void cleanTweets() {
        if (streamListener != null) {
            log.info("Scheduled op, tweets with polarity: {}", streamListener.getTweets());
            streamListener.setTweets(new HashMap<>());
        } else {
            log.info("StreamListener is null");
        }
    }
}
