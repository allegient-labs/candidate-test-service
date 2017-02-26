package com.allegient.candidate.stockquote.datasource;

import org.springframework.beans.factory.annotation.Autowired;

import com.allegient.candidate.stockquote.domain.Quote;

public class FakeQuoteGenerator {

    @Autowired
    public RandomPriceGenerator pricer;

    public Quote create(String symbol) {
        return Quote.of(symbol, pricer.create());
    }

    public Quote update(Quote quote) {
        double updatedPrice = pricer.update(quote.getLastTradePrice());
        return quote.update(updatedPrice);
    }
}
