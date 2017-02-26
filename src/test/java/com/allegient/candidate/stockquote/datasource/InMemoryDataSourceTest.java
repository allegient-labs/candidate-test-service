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
