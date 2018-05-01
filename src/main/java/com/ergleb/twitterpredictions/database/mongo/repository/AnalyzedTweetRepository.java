package com.ergleb.twitterpredictions.database.mongo.repository;

import com.ergleb.twitterpredictions.database.mongo.entity.AnalyzedTweet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface AnalyzedTweetRepository extends MongoRepository<AnalyzedTweet, String> {
    public AnalyzedTweet findById(String id);

    public List<AnalyzedTweet> findByCreatedAtBetween(Date from, Date to);
}
