H2 Console
http://localhost:8080/h2-console

Health
http://localhost:8080/actuator/health

Metrics
http://localhost:8080/actuator/metrics

Prometheus
http://localhost:8080/actuator/prometheus

Beans
http://localhost:8080/actuator/beans

Mappings
http://localhost:8080/actuator/mappings

Swagger URLs
Swagger UI
http://localhost:8080/swagger-ui.html

or

http://localhost:8080/swagger-ui/index.html
OpenAPI JSON
http://localhost:8080/api-docs

Built-in Actuator Endpoints

| Endpoint               | Purpose                       |
| ---------------------- | ----------------------------- |
| `/actuator`            | Lists all available endpoints |
| `/actuator/health`     | Application health            |
| `/actuator/info`       | Application information       |
| `/actuator/metrics`    | Performance metrics           |
| `/actuator/prometheus` | Prometheus metrics            |
| `/actuator/beans`      | Spring Beans                  |
| `/actuator/mappings`   | Request mappings              |
| `/actuator/env`        | Environment properties        |
| `/actuator/caches`     | Cache information             |




Step 12.5 Open Prometheus
http://localhost:9090
Step 12.6 Check Target Status

Go to:

Status → Targets

Expected Output

spring-boot-product-service

State : UP

If the state is UP, Prometheus is successfully scraping your Spring Boot application's metrics.

Step 12.7 Execute Queries

Search:

products_created_total

Example

products_created_total 8

Search

products_total

Example

products_total 8

Search

products_fetch_time_seconds_count

Example

products_fetch_time_seconds_count 15

Search

products_price_rupees_sum

Example

products_price_rupees_sum 490000
Verify Spring Boot Endpoint

Before starting Prometheus, verify the application is running:

http://localhost:8080/actuator/prometheus

If this endpoint is not accessible, Prometheus cannot scrape metrics.


Step 13.3 Open Grafana
http://localhost:3000
Login

Username

admin

Password

admin

Grafana will ask you to change the password during the first login (recommended).

Step 13.4 Add Prometheus Data Source

Navigate to:

Connections
      ↓
Data Sources
      ↓
Add Data Source

Choose

Prometheus
Configure

If Grafana is running in Docker Compose with Prometheus:

http://prometheus:9090

If Grafana is running locally outside Docker:

http://localhost:9090

Click

Save & Test

Expected

Data source is working
Step 13.5 Create Dashboard

Navigate

Dashboards

↓

New Dashboard

↓

Add Visualization

↓

Select Prometheus
Panel 1
Total Products

Query

products_total

Title

Total Products

Visualization

Stat
Panel 2
Products Created

Query

products_created_total

Visualization

Stat
Panel 3
Average Product Fetch Time

Query

rate(products_fetch_time_seconds_sum[1m])
/
rate(products_fetch_time_seconds_count[1m])

Visualization

Time Series
Panel 4
Product Price Statistics

Query

products_price_rupees_sum

Visualization

Time Series
Panel 5
JVM Heap Memory

Query

jvm_memory_used_bytes

Visualization

Time Series
Panel 6
CPU Usage

Query

system_cpu_usage

Visualization

Gauge
Panel 7
HTTP Requests

Query

http_server_requests_seconds_count

Visualization

Time Series
Panel 8
Cache Statistics

Query

cache_gets_total

Visualization

Time Series

Note: Cache-related metrics appear only after cache activity has occurred and if your cache implementation exports those metrics.

Final Dashboard
---------------------------------------------------------
               PRODUCT MANAGEMENT DASHBOARD
---------------------------------------------------------

+----------------+----------------+
| Total Products | Products Added |
+----------------+----------------+

+--------------------------------------------+
| Product Fetch Time                         |
+--------------------------------------------+

+--------------------------------------------+
| Product Price Statistics                   |
+--------------------------------------------+

+--------------------------------------------+
| JVM Heap Memory                            |
+--------------------------------------------+

+--------------------------------------------+
| CPU Usage                                  |
+--------------------------------------------+

+--------------------------------------------+
| HTTP Requests                              |
+--------------------------------------------+

+--------------------------------------------+
| Cache Statistics                           |
+--------------------------------------------+
Monitoring Flow
Client
   │
   ▼
Spring Boot Application
   │
Micrometer Metrics
   │
   ▼
/actuator/prometheus
   │
   ▼
Prometheus
   │
Stores Time-Series Data
   │
   ▼
Grafana
   │
Dashboards
   │
   ▼
Real-Time Monitoring