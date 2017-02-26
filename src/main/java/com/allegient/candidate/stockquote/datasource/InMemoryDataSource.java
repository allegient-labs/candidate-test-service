package com.allegient.candidate.stockquote.datasource;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;

import com.allegient.candidate.stockquote.domain.Quote;

public class InMemoryDataSource implements QuoteDataSource {

    @Autowired
    QuoteCache cache;

    @Autowired
    FakeQuoteGenerator quoter;

    @Override
    public Optional<Quote> findLatest(String symbol) {
        Quote quote = getFromCache(symbol);
        return Optional.of(quote);
    }

    private Quote getFromCache(String symbol) {
        Optional<Quote> optionalQuote = cache.optionalGet(symbol);
        Quote updateQuote = updateQuote(symbol, optionalQuote);
        cache.put(symbol, updateQuote);

        return updateQuote;
    }

    private Quote updateQuote(String symbol, Optional<Quote> optionalQuote) {
        return optionalQuote.map(quoter::update).orElseGet(createQuote(symbol));
    }

    private Supplier<Quote> createQuote(String symbol) {
        return () -> quoter.create(symbol);
    }

}
