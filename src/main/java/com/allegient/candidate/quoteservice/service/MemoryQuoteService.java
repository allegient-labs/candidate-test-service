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
package com.allegient.candidate.quoteservice.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.allegient.candidate.quoteservice.domain.Quote;
import com.allegient.candidate.quoteservice.domain.QuoteList;

public class MemoryQuoteService implements QuoteService {

	@Autowired
	private RandomizedQuoteDataSource dataSource;

	private QuoteCache quotes = new QuoteCache(100);

	@Override
	public QuoteList get(Stream<String> symbols) {
		Stream<Quote> quotes = symbols.map(this::get);
		return QuoteList.from(quotes);
	}
    
    @Override
    public Quote get(String symbol) {
        symbol = symbol.toUpperCase().trim();

        Optional<Quote> optionalQuote = quotes.optionalGet(symbol);
		Quote quote = dataSource.retrieve(symbol, optionalQuote);
        quotes.put(symbol, quote);

        return quote;
    }
}
