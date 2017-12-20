# spring-boot-kafka
Demo of spring boot and spring kafka

## Running tests
```mvn clean test``` 

## Running app
```docker-compose up```

```mvn spring-boot:run``` 

## Send some events to processing
```curl -X POST http://localhost:8080/events -H 'content-type: application/json' -d '{"quantity": 2}'```
