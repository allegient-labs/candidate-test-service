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
package com.allegient.candidate.quoteservice.domain;

import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.junit.Test;

import com.allegient.candidate.quoteservice.domain.Quote;
import com.allegient.candidate.quoteservice.service.QuoteCache;

public class QuoteCacheTest {

    @Test
    public void testThatCapacityIsNotExceeded() {
        int cacheCapacity = 25;
        QuoteCache quoteCache = IntStream.rangeClosed(1, cacheCapacity * 2)
        .mapToObj(this::quoteFromInt)
        .collect(new QuoteCollector(cacheCapacity));
        
        // make sure the capacity has not been exceeded
        assertThat(quoteCache.size(), is(cacheCapacity));
        
        // make sure that only the newer items are in the cache
        IntStream.rangeClosed(1, cacheCapacity)
        .forEach(i -> {
            assertThat(quoteCache.get("q" + i), is(nullValue()));
        });

        IntStream.rangeClosed(cacheCapacity + 1, cacheCapacity *2)
        .forEach(i -> {
            assertThat(quoteCache.get("q" + i), is(notNullValue()));
        });
    }
    
    private Quote quoteFromInt(int i) {
        return Quote.of("q" + i, ThreadLocalRandom.current().nextDouble());
    }
}
