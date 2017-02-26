package com.allegient.candidate.stockquote.datasource;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Map.Entry;

import com.allegient.candidate.stockquote.domain.Quote;

public class QuoteCache extends LinkedHashMap<String, Quote> {
    private static final long serialVersionUID = 6758763368850280897L;
    private int capacity;

    public QuoteCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    public Optional<Quote> optionalGet(String symbol) {
        return Optional.ofNullable(get(symbol));
    }

    @Override
    protected boolean removeEldestEntry(Entry<String, Quote> eldest) {
        return size() > capacity;
    }
}