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

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.allegient.candidate.stockquote.domain.Quote;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Configuration
public class DataSourceConfig {

    @Bean
    @Scope("prototype")
    public QuoteDataSource dataSource() {
        return new InMemoryDataSource();
    }

    @Configuration
    static class DataSourceInternalConfig {
        private static final int CACHE_CAPACITY = 100;

        @Bean
        @Scope("singleton")
        public Cache<String, Quote> quoteCache() {
            return CacheBuilder.newBuilder().maximumSize(CACHE_CAPACITY).build();
        }

        @Bean
        @Scope("prototype")
        public FakeQuoteGenerator fakeQuoteGenerator() {
            return new FakeQuoteGenerator();
        }

        @Bean
        @Scope("prototype")
        public RandomPriceGenerator randomPriceGenerator() {
            return new RandomPriceGenerator();
        }

        @Bean
        @Scope("prototype")
        public ThreadLocalRandom threadLocalRandom() {
            return ThreadLocalRandom.current();
        }
    }
}
