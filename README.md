# Allegient JavaScript Candidate Test Quote Service
[![Build Status](https://travis-ci.org/allegient-labs/candidate-test-service.svg?branch=master)](https://travis-ci.org/allegient-labs/candidate-test-service)
[![Coverage Status](https://coveralls.io/repos/github/allegient-labs/candidate-test-service/badge.svg?branch=master)](https://coveralls.io/github/allegient-labs/candidate-test-service?branch=master)

## What Is This?
This is a simple web service that will return random price data for a given list of symbols.

There is no checking to see if symbols are valid - any string is valid.  The service will return randomized price data for each symbol and the price will vary each time the service is called.

This is a SpringBoot application.  To run it:

- Build using Maven with => `mvn clean install`
- Start it with `java -jar candidate-test-service-1.0.0-SNAPSHOT.jar`
- Test it in a browser with `http://localhost:8080/quote?symbols=goog,aapl`
  
Any number of symbols can be entered on the URL.

IMPORTANT DISCLAIMER: the data returned from this service is completely random and does not represent actual
stock quotes in any way.

## Troubleshooting

* What to do if the project compiles/builds using Maven but shows errors in IDE?
  * This is probably because Project Lombok integration with IDEs. Follow the IDE instruction at [this link](https://projectlombok.org/download.html).

