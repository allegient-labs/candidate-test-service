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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Value;

@Value(staticConstructor="from")
public class QuoteList {

    public static final String DISCLAIMER = "This service is for testing purposes only.  The information returned is randomly generated and does not represent true information.";
    private Stream<Quote> quotes;

    public String getDisclaimer() {
        return DISCLAIMER;
    }

    public String getGeneratedDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public List<Quote> getQuotes() {
        return quotes.collect(Collectors.toList());
    }
}
