package com.ergleb.twitterpredictions.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyScheduler {

    @Scheduled (fixedDelay = 5000L)
    private void getRates() {
        final String uriPrice = "https://api.coinbase.com/v2/prices/BTC-USD/spot";

        RestTemplate restTemplate = new RestTemplate();
        String price = restTemplate.getForObject(uriPrice, String.class);

        log.debug("price: {}", price);
    }

    private static final Logger log = LoggerFactory.getLogger(CurrencyScheduler.class);
}
