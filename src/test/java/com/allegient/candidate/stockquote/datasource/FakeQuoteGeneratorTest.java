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
