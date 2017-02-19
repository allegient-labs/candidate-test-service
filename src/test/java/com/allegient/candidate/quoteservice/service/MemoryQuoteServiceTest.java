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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import com.allegient.candidate.quoteservice.domain.Quote;
import com.allegient.candidate.quoteservice.service.MemoryQuoteService;

public class MemoryQuoteServiceTest {

    @Test
    public void testThatPriceCantBeNegative() {
        double newPrice = MemoryQuoteService.calculateNewPrice(5, -10);
        
        assertThat(newPrice, is(greaterThanOrEqualTo(0.0)));
        assertThat(newPrice, is(lessThanOrEqualTo(1.0)));
    }
    
    @Test
    public void testInitialPriceAndIncrement() {
        MemoryQuoteService mqs = new MemoryQuoteService();
        
        Quote initialQuote = mqs.getQuote("goog");
        assertThat(initialQuote.getLastTradePrice(), is(greaterThanOrEqualTo(0.0)));
        assertThat(initialQuote.getLastTradePrice(), is(lessThanOrEqualTo(100.0)));
        
        Quote subsequentQuote = mqs.getQuote("goog");

        assertThat(initialQuote.getLastTradePrice(), is(not(subsequentQuote.getLastTradePrice())));
        assertThat(Math.abs(initialQuote.getLastTradePrice() - subsequentQuote.getLastTradePrice()), is(lessThanOrEqualTo(10.0)));
    }
}
