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

import org.springframework.beans.factory.annotation.Autowired;

class RandomPriceGenerator {

    @Autowired
    ThreadLocalRandom randomizer;

    public double create() {
        return randomizer.nextDouble(0.0, 100.0);
    }

    public double update(double oldPrice) {
        double newPrice = oldPrice + increment();

        if (newPrice < 0) {
            newPrice = create();
        }

        return newPrice;
    }

    private double increment() {
        return randomizer.nextDouble(-10.0, 20.0);
    }

}
