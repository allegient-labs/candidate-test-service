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

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.allegient.candidate.stockquote.datasource.QuoteCache;
import com.allegient.candidate.stockquote.domain.Quote;

public class QuoteCollector implements Collector<Quote, QuoteCache, QuoteCache> {

    private int cacheCapacity;

    public QuoteCollector(int cacheCapacity) {
        this.cacheCapacity = cacheCapacity;
    }

    @Override
    public Supplier<QuoteCache> supplier() {
        return () -> new QuoteCache(cacheCapacity);
    }

    @Override
    public BiConsumer<QuoteCache, Quote> accumulator() {
        return (qc, q) -> qc.put(q.getSymbol(), q);
    }

    @Override
    public BinaryOperator<QuoteCache> combiner() {
        return (left, right) -> {
            left.putAll(right);
            return left;
        };
    }

    @Override
    public Function<QuoteCache, QuoteCache> finisher() {
        return qc -> qc;
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT);
    }
}
