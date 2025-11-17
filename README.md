# Backend dev technical test
We want to offer a new feature to our customers showing similar products to the one they are currently seeing. To do this we agreed with our front-end applications to create a new REST API operation that will provide them the product detail of the similar products for a given one. [Here](./similarProducts.yaml) is the contract we agreed.

We already have an endpoint that provides the product Ids similar for a given one. We also have another endpoint that returns the product detail by product Id. [Here](./existingApis.yaml) is the documentation of the existing APIs.

**Create a Spring boot application that exposes the agreed REST API on port 5000.**

![Diagram](./assets/diagram.jpg "Diagram")

Note that _Test_ and _Mocks_ components are given, you must only implement _yourApp_.

## Testing and Self-evaluation
You can run the same test we will put through your application. You just need to have docker installed.

First of all, you may need to enable file sharing for the `shared` folder on your docker dashboard -> settings -> resources -> file sharing.

Then you can start the mocks and other needed infrastructure with the following command.
```
docker-compose up -d simulado influxdb grafana
```
Check that mocks are working with a sample request to [http://localhost:3001/product/1/similarids](http://localhost:3001/product/1/similarids).

To execute the test run:
```
docker-compose run --rm k6 run scripts/test.js
```
Browse [http://localhost:3000/d/Le2Ku9NMk/k6-performance-test](http://localhost:3000/d/Le2Ku9NMk/k6-performance-test) to view the results.

## Evaluation
The following topics will be considered:
- Code clarity and maintainability
- Performance
- Resilience

## Solution – Similar Products API
This project implements the required endpoint:
GET /product/{productId}/similar
It returns detailed information about similar products using the APIs provided by the mock server.
##Architecture
Hexagonal (ports & adapters):

## Technologies:
- Java 17
- spring Boot 3
- WebFlux
- Project Reactor
- WebClient

## Implementation Summary

### WebClient
Configured with connection and read/write timeouts (500ms) to ensure resilience and non-blocking behavior.

### Service
Business logic implemented with WebFlux:
- Retrieves similar product IDs
- Fetches product details in parallel
- Fully reactive (no blocking)
- Handles:
  - 200 success
  - 404 not found
  - 500 external errors

### Controller
Maps domain results to the correct HTTP responses.

## Running the Application
### Build
mvn clean package -DskipTests

### Run
java -jar similar-products/target/similar-products-1.0-SNAPSHOT.jar


Endpoint available at:
http://localhost:5000/product/{productId}/similar


Example:
[http://localhost:5000/product/1/similar](http://localhost:5000/product/1/similar).
