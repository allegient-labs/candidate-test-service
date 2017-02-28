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

public class FakeQuoteGeneratorTest {

    @Test
    public void testCreate() {
        FakeQuoteGenerator generator = new FakeQuoteGenerator();
        generator.pricer = mockPricer(999.0);

        assertThat(generator.create("FAKE"), equalTo(Quote.of("FAKE", 999.0)));
    }

    @Test
    public void testUpdate() {
        Quote quote = Quote.of("FAKE", 999.0);

        FakeQuoteGenerator generator = new FakeQuoteGenerator();
        generator.pricer = mockPricer(quote.getLastTradePrice(), 50.0);

        assertThat(generator.update(quote), equalTo(Quote.of("FAKE", 50.0)));
    }

    private RandomPriceGenerator mockPricer(double initPrice, double updatePrice) {
        RandomPriceGenerator mock = Mockito.mock(RandomPriceGenerator.class);
        Mockito.when(mock.update(initPrice)).thenReturn(updatePrice);

        return mock;
    }

    private RandomPriceGenerator mockPricer(Double price) {
        RandomPriceGenerator mock = Mockito.mock(RandomPriceGenerator.class);
        Mockito.when(mock.create()).thenReturn(price);

        return mock;
    }
}
