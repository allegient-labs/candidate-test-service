# Allegient JavaScript Candidate Test Quote Service
[![Build Status](https://travis-ci.org/allegient-labs/candidate-test-service.svg?branch=master)](https://travis-ci.org/allegient-labs/candidate-test-service)
[![Coverage Status](https://coveralls.io/repos/github/allegient-labs/candidate-test-service/badge.svg?branch=master)](https://coveralls.io/github/allegient-labs/candidate-test-service?branch=master)

## What Is This?
This is a simple web service that will return random price data for a given list of symbols.

There is no checking to see if symbols are valid - any string is valid.  The service will return randomized price data for each symbol and the price will vary each time the service is called.

This is a SpringBoot application.  To run it:

- Build using Maven with => `mvn clean install`
- Start it with `java -jar candidate-test-service-1.0.0-SNAPSHOT.jar`
- Test it in a browser with `http://localhost:8080/randomQuote/quote?symbols=goog,aapl`
- API information is available at `http://localhost:8080/randomQuote/swagger-ui.html`
  
Any number of symbols can be entered on the URL.

IMPORTANT DISCLAIMER: the data returned from this service is completely random and does not represent actual
stock quotes in any way.

## Docker
*Copied from [this blog post](https://exampledriven.wordpress.com/2016/06/24/spring-boot-docker-example/)*

- Install Docker Compose => https://docs.docker.com/compose/install/
- `mvn clean install -Pdocker` to create docker images
- `docker images` to confirm this app is available as an image in docker
- `docker-compose up` to start the app
- `http://localhost:8080/randomQuote/quote?symbols=goog,aapl` test in browser
