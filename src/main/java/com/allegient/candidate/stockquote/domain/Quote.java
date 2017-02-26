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
package com.allegient.candidate.stockquote.domain;

public class Quote {
    private String symbol;
    private double lastTradePrice;

    public static Quote of(String symbol, double lastTradePrice) {
        Quote quote = new Quote();
        quote.symbol = symbol;
        quote.lastTradePrice = lastTradePrice;
        return quote;
    }

    public Quote update(double updatedPrice) {
        return Quote.of(getSymbol(), updatedPrice);
    }

    public String getSymbol() {
        return symbol;
    }

    public double getLastTradePrice() {
        return lastTradePrice;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(lastTradePrice);

        int result = 1;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + ((symbol == null) ? 0 : symbol.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Quote)) {
            return false;
        }

        Quote other = (Quote) obj;
        if (Double.doubleToLongBits(lastTradePrice) != Double.doubleToLongBits(other.lastTradePrice)) {
            return false;
        }

        if (symbol == null && other.symbol != null) {
            return false;
        }

        if (symbol != null && !symbol.equals(other.symbol)) {
            return false;
        }

        return true;
    }
}
