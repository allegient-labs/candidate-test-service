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