package com.ergleb.twitterpredictions.scheduling;

import com.ergleb.twitterpredictions.database.mongo.entity.AnalyzedTweet;
import com.ergleb.twitterpredictions.database.mongo.repository.AnalyzedTweetRepository;
import com.vader.sentiment.util.ScoreType;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Getter
@Setter
public class TwitterScheduler {
    private static final Logger log = LoggerFactory.getLogger(TwitterScheduler.class);

    @Inject
    public TwitterScheduler(AnalyzedTweetRepository analyzedTweetRepository) {
        this.analyzedTweetRepository = analyzedTweetRepository;
    }

    private AnalyzedTweetRepository analyzedTweetRepository;

    @Getter
    private Map<Tweet, Map<String, Float>> tweets = new HashMap<>();

    @Scheduled(fixedDelay = 5000L)
    private void cleanTweets() {
        log.info("Scheduled op, tweets with polarity: {}", tweets);
        List<AnalyzedTweet> analyzedTweets = tweets.entrySet().stream().map(x -> {
            Tweet tweet = x.getKey();
            AnalyzedTweet analyzedTweet = new AnalyzedTweet();
            analyzedTweet.setTweetId(tweet.getId());
            analyzedTweet.setText(tweet.getText());
            analyzedTweet.setHashTags(tweet.getEntities().getHashTags());
            analyzedTweet.setUserId(tweet.getFromUser());
            analyzedTweet.setCreatedAt(tweet.getCreatedAt());
            Map<String, Float> polarity = x.getValue();
            analyzedTweet.setPositive(polarity.get(ScoreType.POSITIVE));
            analyzedTweet.setNegative(polarity.get(ScoreType.NEGATIVE));
            analyzedTweet.setCompound(polarity.get(ScoreType.COMPOUND));
            analyzedTweet.setNeutral(polarity.get(ScoreType.NEUTRAL));
            return analyzedTweet;
        }).collect(Collectors.toList());
        log.info("Analyzed tweet list: {}", analyzedTweets);
        analyzedTweetRepository.insert(analyzedTweets);
        tweets = new HashMap<>();
    }
}
