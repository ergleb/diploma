package com.ergleb.twitterpredictions.scheduling;

import com.ergleb.twitterpredictions.database.mongo.entity.BitcoinRate;
import com.ergleb.twitterpredictions.database.mongo.repository.BitcoinRateRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;

@Component
public class CurrencyScheduler {

    private BitcoinRateRepository bitcoinRateRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    public CurrencyScheduler(BitcoinRateRepository bitcoinRateRepository) {
        this.bitcoinRateRepository = bitcoinRateRepository;
    }

    @Scheduled(fixedDelay = 60000L)
    private void getRates() {
        final String uriPrice = "https://api.coinbase.com/v2/prices/BTC-USD/spot";

        RestTemplate restTemplate = new RestTemplate();
        String price = restTemplate.getForObject(uriPrice, String.class);

        try {
            JsonNode dataNode = objectMapper.readTree(price);
            double btcPrice = dataNode.get("data").get("amount").asDouble();
            Date date = new Date();
            BitcoinRate rate = new BitcoinRate();
            rate.setDate(date);
            rate.setRate(btcPrice);
            log.info("price: {}", btcPrice);
            bitcoinRateRepository.insert(rate);
        } catch (IOException ex) {
            log.error("exception while processing json: {}, ex: {}", price, ex);
        }


        //log.info("price: {}", price);
    }

    private static final Logger log = LoggerFactory.getLogger(CurrencyScheduler.class);
}
