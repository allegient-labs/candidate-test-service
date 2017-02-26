package com.allegient.candidate.stockquote.datasource;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.mockito.Mockito;

public class RandomPriceGeneratorTest {

    @Test
    public void testCreate() {
        RandomPriceGenerator generator = new RandomPriceGenerator();
        generator.randomizer = mockRandomizer(70.0);

        assertThat(generator.create(), equalTo(70.0));
    }

    @Test
    public void testUpdate() {
        RandomPriceGenerator generator = new RandomPriceGenerator();
        generator.randomizer = mockRandomizer(0.0, 10.0);

        assertThat(generator.update(70.0), equalTo(80.0));
    }

    @Test
    public void testUpdateWithNegativeIncrement() {
        RandomPriceGenerator generator = new RandomPriceGenerator();
        generator.randomizer = mockRandomizer(70.0, -8.0);

        assertThat(generator.update(6.0), equalTo(70.0));
    }

    private ThreadLocalRandom mockRandomizer(double fakeRandomValue, double fakeRandomIncr) {
        ThreadLocalRandom mock = mockRandomizer(fakeRandomValue);
        Mockito.when(mock.nextDouble(-10.0, 20.0)).thenReturn(fakeRandomIncr);

        return mock;
    }

    private ThreadLocalRandom mockRandomizer(double fakeRandomValue) {
        ThreadLocalRandom mock = Mockito.mock(ThreadLocalRandom.class);
        Mockito.when(mock.nextDouble(0.0, 100.0)).thenReturn(fakeRandomValue);

        return mock;
    }
}

