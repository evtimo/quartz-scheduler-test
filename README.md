## Getting Started

This is a small Spring Boot app, that allows to get data by configurable Quartz-scheduler from external API, store currency data in H2 locally and calculate exchange rates.

## Getting Start

Clone and open the repository in IDE as a Maven project

## Prerequisites

* Java 8
* Maven

## Install & Run

Before running, check the `apiKey` and choose `currencies` in **application.properties** file!

```sh
$  mvn spring-boot:run
```

## API Documentation - Swagger

http://localhost:8080/swagger-ui.html


## Acceptance testing

1. Run the application, check logs in console - data was saved from external API as soons as app was started
2. Check correctness of "GET /test/exchange" endpoint in [swagger](http://localhost:8080/swagger-ui.html) (no need in Curl or Postman, just SwaggerUI)
3. Wait for ~1 min and run "GET /scheduler/execute" endpoint for data sync (exchange rates update)
4. Check that "GET /test/exchange" endpoint responses updated values for rates

## Built With
* Spring Boot
* [Quartz](quartz-scheduler.org) - for Update Data Scheduler
* [QueryDsl](http://www.querydsl.com/) - for dynamic queries
* [OpenAPI](https://github.com/springdoc/springdoc-openapi) - for API Documentation


![image](https://user-images.githubusercontent.com/22182922/174703523-0994a92f-6e79-4335-8af5-e1266b87fa0c.png)
![image](https://user-images.githubusercontent.com/22182922/174706963-4169209b-fec7-4732-a842-09b886df92cb.png)
