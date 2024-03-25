# eLunchApp - meal ordering app

A Java/Spring Boot implementation of backend service for the meal ordering app.

## Table of contents
* [General Information](#general-information)
* [Technologies used](#technologies)
* [Project details](#implementation-details)
* [Data model design](#data-model-design)

## General information
The project has been developed along with the ['Kurs Java - praktyczny projekt aplikacji'](https://strefakursow.pl/kursy/programowanie/kurs_java_-_praktyczny_projekt_aplikacji.html) training by Karol Janaszek.

The purpose of this project was mainly educational. It served me to expand my knowledge of REST application and Spring Boot.

I chose this training due to its comprehensive approach of building a backend layer of web service. 


## Technologies

- Java
- Spring Boot
- Hibernate
- MySQL
- Maven
</br>

## Implementation details

Project implementation included: 

1. Data model and database scheme design.
2. Configuration of database connectivity, JPA vendor (Hibernate) and JSON - Object mapping.
3. API design and dedicated documentation (OpenAPI 3.0.1, YAML format).
4. Controller, Service and Repository classes for HTTP requests handling, business logic, and interaction with database, respectively.
5. Design patterns:
   - DTO supporting data transfer between frontend layer (not implemented within the project) and database. 
   - Builder pattern supporting creation of Entity and DTO objects as well as methods for object conversion between those types.
6. Global exception handling using @ControllerAdvice annotation
7. Example implementation of:
   - custom validation annotations
   - validation groups
   - data caching
   - application events handling using Spring Boot Event Listener
8. Unit tests for chosen controllers and event listener. 

## Data model design

* [Database scheme in PDF](src/main/resources/eLunchApp_database_scheme.pdf)





