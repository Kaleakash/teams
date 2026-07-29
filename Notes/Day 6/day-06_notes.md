What is Performance?

Performance refers to how fast, efficiently, and reliably an application responds to 
user requests while using system resources effectively.

In simple words:

Performance is about making an application fast, scalable, and efficient.

What is Observability?

Observability is the ability to understand what is happening inside 
an application by collecting and analyzing data such as metrics, logs, and traces.

In simple words:

Observability helps us monitor,
 diagnose, and troubleshoot applications without opening the source code.


Spring Boot

Definition

Spring Boot is a Java framework built on top of the Spring Framework 
that simplifies application development by providing auto-configuration, 
embedded servers, and production-ready features.

Why do we need Spring Boot?

Without Spring Boot, developers manually configure:

Tomcat
Dispatcher Servlet              : FrontController 
XML Configuration
Dependency Management
Database Configuration

Spring Boot automates all of these.

How it works

Developer
↓
Spring Boot
↓
Auto Configuration
↓
Embedded Tomcat
↓
Application Starts


mvnw clean 
mvnw spring-boot:run or using IDE run the main class 

Verify

Open

http://localhost:8080




Spring Data JPA
Definition

Spring Data JPA simplifies database operations by providing ready-made 
CRUD methods without writing SQL queries.

Why do we need it?

Instead of writing

INSERT

UPDATE

DELETE

SELECT

Spring Boot provides

save()

findAll()

findById()

delete()

Running Steps

@Entity
↓
JpaRepository
↓
Repository.save()
↓
Database


Open H2 Console

http://localhost:8080/h2-console

Execute

SELECT * FROM PRODUCTS;

Verify table is created.




Bean Validation
Definition

Bean Validation validates incoming user data before saving it into the database.

Why?

Avoid invalid data.

Example

Price = -5000 
Quantity = -10 
Name = "" 

Annotations
@NotBlank
@Positive
@Min
@Max
@Email
@Pattern

@Valid annotation part of spring boot to do validation for spring validator 

Running Steps
User
↓
JSON
↓
@Valid
↓
Validation
↓
Save into Database

testing open the post man or curl or soap-ui 


sample data 
{
"name":"",
"price":-100
}


Global Exception Handling
Definition

Global Exception Handling catches all exceptions at 
one central place and returns a standard error response.

Why?

Instead of
Stack Trace

User receives
404

Product Not Found

Real-world Example

ATM
↓
Account Not Found
↓
Proper Error Message

instead of crashing.

Flow

Controller
↓
Service
↓
Exception
↓
@RestControllerAdvice           if controller is type of @RestController 
↓
JSON Response


Open

GET /products/100

Verify

404

Product Not Found



Swagger

Swagger
Definition

Swagger automatically generates REST API documentation.

Why?

Developers can test APIs without Postman.

Open

http://localhost:8080/swagger-ui/index.html

Running Steps

Dependency
↓
OpenApiConfig
↓
Run Project
↓
Open Swagger

open the browser and type below URL as 

http://localhost:8080/swagger-ui/index.html


and test all rest api methods. 



Spring Cache
Definition

Spring Cache stores frequently accessed data in memory, 
reducing database calls and improving performance.

Why?

Without Cache

Request
↓
Database
↓

Response
Every request hits the database.

With Cache

First Request
↓
Database
↓
Cache
↓
Second Request
↓
Cache
↓
Response


Annotations
                                all annotation you need to use on server layer methods. 
@Cacheable
@CachePut
@CacheEvict

@EnableCache in main class 

Cache Annotations Used
1. @Cacheable
@Cacheable(value = "products", key = "#id")

If the product is already in cache:

Return from cache

Database is NOT called

2. @CachePut
@CachePut(value = "products", key = "#id")

Updates
Database
Cache
Both remain synchronized.

3. @CacheEvict
@CacheEvict(value = "products", key = "#id")

Removes the deleted product from cache.

4. Clear Complete Cache
@CacheEvict(value="products", allEntries=true)

Used after inserting a new product because the cached product list may now be outdated.


Running Steps
@EnableCaching
↓
@Cacheable
↓
Run
↓
Call API Twice
↓
Second Call Uses Cache

Call


GET /products/1

Observe

Fetching Product from Database...

Step 6

Call again

GET /products/1

Observe

No database call.

Returned from Cache.



Spring Boot Actuator

Definition

Actuator provides production-ready monitoring endpoints for Spring Boot applications.

Why?

Monitor

Health
Memory
CPU
Requests
Beans
Environment

Important Endpoints
/actuator

/health
/metrics
/prometheus
/caches
/beans
/mappings


Running Steps

Add Dependency
↓
Run Application
↓
Open
/actuator/health


http://localhost:8080/actuator

Verify

/health
/metrics
/prometheus



Health Indicator
Definition

Health Indicator allows developers to create 
custom health checks for application components.

Real-world Use Case

Check

Database
Kafka
Redis
RabbitMQ
External APIs

Flow

Actuator
↓
HealthIndicator
↓
UP
or
DOWN




Running Steps
Create

implements HealthIndicator
↓
Run
↓
Open
/actuator/health

Open

http://localhost:8080/actuator/health
Verify
Status : UP


Micrometer

Definition

Micrometer is the metrics collection library used by Spring Boot to collect 
application and business metrics.

Why?

Collect

CPU
Memory
Request Count
Database Calls
Business Metrics

Flow

Application
↓
Micrometer
↓
Metrics
↓
Prometheus

To view the output as 

Open

/actuator/metrics



Counter
Definition

Counter counts how many times an event occurs.

Example

Products Created
Orders Created
Users Registered
Payments Completed

Running Steps

Counter.increment()
↓
Create Product
↓
Counter +1

Open

/actuator/prometheus

Search

products

Verify

2



Timer

Definition

Timer measures the execution time of a method or API.

Example
GET /products

↓

120 ms
Running Steps

Start Time
↓
Method
↓
End Time
↓
Record Duration


Open

/actuator/prometheus

Search

products_fetch_time


Gauge
Definition

Gauge represents the current value of a metric that can increase or decrease.

Example
Current Products
Current Users
Current Memory

Running Steps
Repository.count()
↓
Gauge.set()
↓
Prometheus

Open

/actuator/prometheus

Search

products current total 

Observe the value increase or decrease.

Distribution Summary
Definition

Distribution Summary records the distribution of numeric values and provides statistics such as count, total, average, and maximum.

Example

Product Price
1000
2000
3000

Average

Maximum
Running Steps
record(price)
↓
Statistics Generated


Open

/actuator/prometheus

Search

products_price

Observe

Count
Sum
Average (derived)
Maximum (if configured)

Prometheus

Definition

Prometheus is an open-source monitoring tool that collects metrics 
from applications at regular intervals using a pull model.

Why?

Store metrics over time.

Flow

Spring Boot
↓
/actuator/prometheus
↓
Prometheus
↓
Database

Running Steps

Running Steps

Step 1

Create
prometheus.yml

Step 2

Create
docker-compose.yml

Step 3

Start
docker compose up -d

it pull the images and run all container part of docker compose 
file in background 
after few minutes to check all images 

docker images
to check running container 

docker ps

Step 4
Open
http://localhost:9090

Step 5

Navigate
Status
↓
Targets

Verify
UP

Step 6
Execute Query
products_*




Grafana
Definition

Grafana is a visualization tool that displays metrics 
collected by Prometheus using dashboards and charts.

Why?

Visualize

CPU
Memory
Request Count
Business Metrics
JVM
Database Metrics


Flow
Prometheus
↓
Grafana
↓
Dashboard

Running Steps

Step 1
Start Grafana

docker compose up -d
Step 2
Open
http://localhost:3000

Step 3
Login
Username : admin
Password : admin

Step 4
In Connection option 
Add Data Source
Prometheus
URL
http://prometheus:9090

Step 5
Click
Save & Test

Step 6
Create Dashboard

Step 7
Add Panels

products_created_total
products_total
products_fetch_time_seconds_count
jvm_memory_used_bytes
http_server_requests_seconds_count
system_cpu_usage

Step 8

Generate Product API traffic.

Step 9
Refresh the dashboard.

Observe real-time graphs updating as requests are processed.



Complete Observability Architecture

                   Client
                      │
                      ▼
            Spring Boot REST API
                      │
                      ▼
               Service Layer
                      │
      ┌───────────────┴───────────────┐
      ▼                               ▼
 Spring Cache                   Micrometer Metrics
      │                               │
      ▼                               ▼
  Faster Response            Counter / Timer / Gauge /
                             Distribution Summary
                      │
                      ▼
          Spring Boot Actuator
      (/health, /metrics, /prometheus)
                      │
                      ▼
                Prometheus Server
          (Scrapes every 15 seconds)
                      │
                      ▼
              Time-Series Database
                      │
                      ▼
              Grafana Dashboard
                      │
                      ▼
         Real-Time Monitoring & Alerts