package com.ergleb.twitterpredictions.database.mongo.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "rate")
@Getter
@Setter
public class BitcoinRate {

    @Id
    private String id;
    private double rate;
    private Date date;
}
