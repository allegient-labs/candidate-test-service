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
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;

import com.allegient.candidate.stockquote.domain.Quote;
import com.google.common.cache.Cache;

public class InMemoryDataSource implements QuoteDataSource {

    @Autowired
    Cache<String, Quote> cache;

    @Autowired
    FakeQuoteGenerator quoter;

    @Override
    public Optional<Quote> findLatest(String symbol) {
        Quote quote = getFromCache(symbol);
        return Optional.of(quote);
    }

    private Quote getFromCache(String symbol) {
        Optional<Quote> optionalQuote = optionalGet(symbol);
        Quote updateQuote = updateQuote(symbol, optionalQuote);
        cache.put(symbol, updateQuote);

        return updateQuote;
    }

    private Optional<Quote> optionalGet(String symbol) {
        Quote nullableQuote = cache.getIfPresent(symbol);
        return Optional.ofNullable(nullableQuote);
    }

    private Quote updateQuote(String symbol, Optional<Quote> optionalQuote) {
        return optionalQuote.map(quoter::update).orElseGet(createQuote(symbol));
    }

    private Supplier<Quote> createQuote(String symbol) {
        return () -> quoter.create(symbol);
    }
}
