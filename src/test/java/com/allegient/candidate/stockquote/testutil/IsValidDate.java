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
package com.allegient.candidate.stockquote.testutil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class IsValidDate extends TypeSafeMatcher<String> {

    @Override
    public void describeTo(Description description) {
        description.appendText("valid date in ISO_LOCAL_DATE_TIME format");
    }

    @Override
    protected boolean matchesSafely(String item) {
        try {
            LocalDateTime.parse(item, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    protected void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText(item + " is an invalid date");
    }

    public static IsValidDate isValidDate() {
        return new IsValidDate();
    }
}
