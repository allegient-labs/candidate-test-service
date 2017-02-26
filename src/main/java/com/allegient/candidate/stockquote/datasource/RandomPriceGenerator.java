package com.allegient.candidate.stockquote.datasource;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;

class RandomPriceGenerator {

    @Autowired
    ThreadLocalRandom randomizer;

    public double create() {
        return randomizer.nextDouble(0.0, 100.0);
    }

    public double update(double oldPrice) {
        double newPrice = oldPrice + increment();

        if (newPrice < 0) {
            newPrice = create();
        }

        return newPrice;
    }

    private double increment() {
        return randomizer.nextDouble(-10.0, 20.0);
    }

}
