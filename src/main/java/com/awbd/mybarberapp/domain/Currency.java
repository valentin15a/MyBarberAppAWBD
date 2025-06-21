package com.awbd.mybarberapp.domain;

import lombok.Getter;

@Getter
public enum Currency {
    USD("USD $"), EUR("EUR"), GBP("GBP");

    private final String description;

    Currency(String description) {
        this.description = description;
    }
}
