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
package com.allegient.candidate.quoteservice.app;

import static com.allegient.candidate.quoteservice.testutil.IsValidDate.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.allegient.candidate.quoteservice.app.Application;
import com.allegient.candidate.quoteservice.domain.QuoteList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class QuoteControllerTest {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testNoParameter() throws Exception {
		mockMvc.perform(get("/quote")).andExpect(status().is(400));
	}

	@Test
	public void testNoSymbols() throws Exception {
		mockMvc.perform(get("/quote?symbols=")).andExpect(status().is(200))
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.disclaimer", is(QuoteList.DISCLAIMER)))
				.andExpect(jsonPath("$.generatedDate", isValidDate()));
	}

	@Test
	public void testOneSymbol() throws Exception {
		mockMvc.perform(get("/quote?symbols=goog")).andExpect(status().is(200))
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.disclaimer", is(QuoteList.DISCLAIMER)))
				.andExpect(jsonPath("$.generatedDate", isValidDate()))
				.andExpect(jsonPath("$.quotes[0].symbol", is("GOOG")))
				.andExpect(jsonPath("$.quotes[0].lastTradePrice", is(greaterThanOrEqualTo(0.0))));
	}

	@Test
	public void testTwoSymbols() throws Exception {
		mockMvc.perform(get("/quote?symbols=goog,aapl")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.disclaimer", is(QuoteList.DISCLAIMER)))
				.andExpect(jsonPath("$.generatedDate", isValidDate()))
				.andExpect(jsonPath("$.quotes[0].symbol", is("GOOG")))
				.andExpect(jsonPath("$.quotes[0].lastTradePrice", is(greaterThanOrEqualTo(0.0))))
				.andExpect(jsonPath("$.quotes[1].symbol", is("AAPL")))
				.andExpect(jsonPath("$.quotes[1].lastTradePrice", is(greaterThanOrEqualTo(0.0))));
	}
}
