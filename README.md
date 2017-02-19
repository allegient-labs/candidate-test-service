# Allegient JavaScript Candidate Test Quote Service
This is a simple web service that will return random price data for a given list of symbols.

There is no checking to see if symbols are valid - any string is valid.  The service will return randomized price data for each symbol and the price will vary each time the service is called.

This is a SpringBoot application.  To run it:

- Build with Maven (mvn clean install)
- Start it with java -jar candidate-test-service-1.0.0-SNAPSHOT.jar
- Test it in a browser with https://localhost:8080/quote?symbols=goog,aapl
  
Any number of symbols can be entered on the URL.

IMPORTANT DISCLAIMER: the data returned from this service is completely random and does not represent actual
stock quotes in any way.
