package com.allegient.candidate.stockquote.datasource;

import com.allegient.candidate.stockquote.domain.Quote;

public interface QuoteDataSource {

    Quote findLatest(String symbol);

}