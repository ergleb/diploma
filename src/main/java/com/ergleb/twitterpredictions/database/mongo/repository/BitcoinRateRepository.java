package com.ergleb.twitterpredictions.database.mongo.repository;

import com.ergleb.twitterpredictions.database.mongo.entity.BitcoinRate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BitcoinRateRepository extends MongoRepository<BitcoinRate, String> {
}
