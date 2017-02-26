/**
 *    Copyright 2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.allegient.candidate.stockquote.datasource;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import com.allegient.candidate.stockquote.domain.Quote;

// call it RandomPriceGenerator and FakeQuoteGenerator
// this package also needs it's own spring config class
public class RandomizedQuoteDataSource implements QuoteDataSource {

    @Override
    public Quote findLatest(String symbol) {
        return findLatest(symbol, Optional.empty());
    }

    private Quote findLatest(String symbol, Optional<Quote> lastValue) {
        return lastValue.map(this::update).orElseGet(create(symbol));
    }

    private Quote update(Quote quote) {
        double newPrice = calculateNewPrice(quote.getLastTradePrice(), increment());
        return Quote.of(quote.getSymbol(), newPrice);
    }

    private Supplier<Quote> create(String symbol) {
        return () -> Quote.of(symbol, initialPrice());
    }

    /**
     * @param oldPrice
     *            the previous price
     * @return a new price based on adding a random increment to the previous
     *         price. The new price will never be less than 0
     */
    public static double calculateNewPrice(double oldPrice, double increment) {
        double newPrice = oldPrice + increment;
        if (newPrice < 0) {
            newPrice = ThreadLocalRandom.current().nextDouble();
        }
        return newPrice;
    }

    /**
     * @return a price between 0 and 100
     */
    private double initialPrice() {
        return ThreadLocalRandom.current().nextDouble() * 100;
    }

    /**
     * @return an increment between -10 and 10
     */
    private double increment() {
        return ThreadLocalRandom.current().nextDouble(20.0) - 10.0;
    }

}
