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
package com.allegient.candidate.stockquote.app;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;
import org.mockito.Mockito;

import com.allegient.candidate.stockquote.datasource.QuoteDataSource;
import com.allegient.candidate.stockquote.domain.Quote;
import com.allegient.candidate.stockquote.domain.QuoteList;

public class QuoteFinderTest {
    @Test
    public void testGetSymbol() {
        Quote expectedQuote = Quote.of("FAKE", 111.11);

        QuoteFinder finder = new QuoteFinder();
        finder.dataSource = mockDataSource(expectedQuote);

        Optional<Quote> actualQuote = finder.find("FAKE");
        assertThat(actualQuote.get(), equalTo(expectedQuote));
    }

    @Test
    public void testGetSymbolWithWhitespace() {
        Quote expectedQuote = Quote.of("FAKE", 111.11);

        QuoteFinder finder = new QuoteFinder();
        finder.dataSource = mockDataSource(expectedQuote);

        Optional<Quote> actualQuote = finder.find("  fake  ");
        assertThat(actualQuote.get(), equalTo(expectedQuote));
    }

    @Test
    public void testGetSymbolStream() {
        List<Quote> expectedQuotes = Arrays.asList(Quote.of("FAKE1", 11), Quote.of("FAKE2", 22));

        QuoteFinder finder = new QuoteFinder();
        finder.dataSource = mockDataSource(expectedQuotes.stream());

        QuoteList actualQuoteList = finder.find(Stream.of("fake1", "FAKE2"));
        assertThat(actualQuoteList.getQuotes(), equalTo(expectedQuotes));
    }

    @Test
    public void testGetSymbolStreamWithInvalidSymbols() {
        List<Quote> expectedQuotes = Arrays.asList(Quote.of("FAKE1", 11), Quote.of("FAKE2", 22));

        QuoteFinder finder = new QuoteFinder();
        finder.dataSource = mockDataSource(expectedQuotes.stream());
        Mockito.when(finder.dataSource.findLatest("INVALID")).thenReturn(Optional.empty());

        QuoteList actualQuoteList = finder.find(Stream.of("fake1", "INVALID", "FAKE2"));
        assertThat(actualQuoteList.getQuotes(), equalTo(expectedQuotes));
    }

    private QuoteDataSource mockDataSource(Quote quote) {
        return mockDataSource(Stream.of(quote));
    }

    private QuoteDataSource mockDataSource(Stream<Quote> quotes) {
        QuoteDataSource mock = Mockito.mock(QuoteDataSource.class);

        quotes.forEach(quote -> {
            Mockito.when(mock.findLatest(quote.getSymbol())).thenReturn(Optional.of(quote));
        });

        return mock;
    }
}
