package com.allegient.candidate.stockquote.datasource;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mockito.Mockito;

import com.allegient.candidate.stockquote.domain.Quote;

public class InMemoryDataSourceTest {

    @Test
    public void testFindLatestWithEmptyCache() {
        Quote expectedQuote = Quote.of("FAKE", 1.0);

        FakeQuoteGenerator mockQuoter = Mockito.mock(FakeQuoteGenerator.class);
        Mockito.when(mockQuoter.create("FAKE")).thenReturn(expectedQuote);

        InMemoryDataSource dataSource = new InMemoryDataSource();
        dataSource.cache = new QuoteCache(2);
        dataSource.quoter = mockQuoter;

        assertThat(dataSource.findLatest("FAKE").get(), equalTo(expectedQuote));
        assertThat(dataSource.cache.get("FAKE"), equalTo(expectedQuote));
    }

    @Test
    public void testFindLatestWithUpdateCachedData() {
        Quote originalQuote = Quote.of("FAKE", 1.0);
        Quote updatedQuote = Quote.of("FAKE", 2.0);

        // cache has seed data
        QuoteCache cache = new QuoteCache(2);
        cache.put("FAKE", originalQuote);

        // quoter get seed data and returns updated data
        FakeQuoteGenerator mockQuoter = Mockito.mock(FakeQuoteGenerator.class);
        Mockito.when(mockQuoter.update(originalQuote)).thenReturn(updatedQuote);

        InMemoryDataSource dataSource = new InMemoryDataSource();
        dataSource.quoter = mockQuoter;
        dataSource.cache = cache;

        // both dataSource and cache return updated quote
        assertThat(dataSource.findLatest("FAKE").get(), equalTo(updatedQuote));
        assertThat(cache.get("FAKE"), equalTo(updatedQuote));
    }

}
