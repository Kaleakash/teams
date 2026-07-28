                            Java 26

      JSE                       JEE                    JME
      Java Standard Edition     Enterprise Edition    Micro Edition 
      OOPs 
      Exception Handling        Servlet, JSP and EJB 
      Multi threading 
      Collection framework      MVC : Model view controller 
      file handling           Controller -> Servlet 
      jdbc etc                View --> JSP 
      awt , swing, JavaFx etc  Model --> EJB 
      GUI

      Oracle or Mysql or Postgres

      desktop or standalone application 

    Framework 
    Struts  : MVC base 
    JSF : MVC base 
    Spring framework: layer architecture framework. 
    spring core, context, orm, jdbc, rest, security, micro service, cloud etc. 
    
    spring boot etc. 


  spring framework : css 
  spring boot : bootstrap 
    build tool : maven or gradle or ant  





Excellent. We now begin Module 1, covering Slides 5–8, which explain Layered Architecture, Inversion of Control (IoC), Dependency Injection (DI), Spring Beans, and Constructor Injection. These concepts are the heart of the Spring Framework because every Spring Boot application relies on them.

DAY 4 – SPRING BOOT: ENTERPRISE ARCHITECTURE
MODULE 1 – WIRING THE BACKEND
SECTION 1 – Layered Architecture, IoC & Dependency Injection

(Based on Slides 5–8)

Learning Objective

After completing this section, participants will be able to:

• Understand Enterprise Layered Architecture.

• Learn Multi-Module Architecture.

• Understand Inversion of Control (IoC).

• Understand Dependency Injection (DI).

• Learn Spring Beans.

• Understand Constructor Injection.

• Explain why Spring Boot applications are loosely coupled.

Trainer Introduction

Imagine you are building

an Online Banking Application.

The application contains

Customer Module
Trade Module
Payment Module
Loan Module
Notification Module
Security Module

Question

Can everything be written

inside one Java package?

No.

Enterprise applications contain

Lakhs of lines of code

and

hundreds of developers.

To manage this complexity,

applications are divided into

Layers

and

Modules.

Spring Boot is built around

this architecture.

What is Layered Architecture?

Layered Architecture

means

dividing an application

into multiple layers,

where

each layer

has only

one responsibility.

Instead of writing

everything together,

we separate

business logic(service layer),

database logic(dao or repository layer),

REST APIs(web service),

and domain objects(Java bean or record or container).

Traditional Application

Everything together

Controller

Database Code

Business Logic

Validation

Entity

Utility

Email

Kafka

Problems

• Difficult to understand

• Difficult to maintain

• Difficult to test

• High coupling

• Low reusability

Enterprise Layered Architecture

Spring Boot applications

are divided into


Front end technology layer react or angular framework 

backend technology layer spring boot with rest api or express js or python or asp.net 

Data layer layer      : postgres or mongo db  


Client
↓
Controller Layer
↓
Service Layer 
↓
Repository Layer
↓
Database

Each layer communicates

only with

the next layer.

This is the architecture

used by almost every enterprise application.

Real ReconX Architecture

The PPT introduces

the ReconX layered architecture.

React Dashboard
↓
TradeController
↓
TradeService
↓
TradeRepository
↓
PostgreSQL

Every incoming request

passes through

these layers

before returning a response.

Understanding Multi-Module Architecture

Large enterprise applications

are further divided into

Modules.

ReconX contains

API Module        many controller 
↓
Service Module  many service    specification and implementation 
↓
Repository Module many repository 
↓
Domain Module   many repository 
↓
Common Module

Each module

contains

related functionality.

This keeps the project

organized

and scalable.

Why Multi-Module Projects?

Suppose

Trade Module

depends on

Customer Module.

Question

Should Customer Module

depend on

Trade Module?

No.

Dependencies should move

only in one direction.

This reduces

Coupling.

Improves

Maintainability.

ReconX Module Structure

The PPT shows

five Maven modules.

reconx-api
↓
reconx-service
↓
reconx-repository
↓
reconx-domain
↓
reconx-common

Notice

Dependencies always move

downward.

The Domain module

never imports

Spring Framework.

It remains

Pure Java.


IOC and DI 

Inversion of control :
  programming design pattern. it is a concept. In place of creating any object or resources 
  explicitly allow to create by container. if container create it maintain the life of that resource. 
  if we create may be we maintain properly or not. 
  you pull any resource from container use it and leave it. 

  Employee emp = new Employee();

  Employee emp (pull the object from container. )

Dependency Injection :
DI is an implementation of an IOC. 
using 
1. constructor base di 
2. setter base di 

we need to do configuration 
using xml 
using annotation 

@Component ---> Java bean annotation 
@Controller ---> controller base 
@RestController --> web service base 
@Service --> service layer base 
@Repository --> dao or database 

has a relationship 
controller has service layer  @Autowired 
service has repository      @Autowired 
repository has database     @Autowired 


















What is Inversion of Control (IoC)?



Question

Who creates objects

inside a normal Java application?

Answer

Developer.

Example

TradeService service =

new TradeService();

Developer is responsible

for object creation.

This is called

Traditional Programming.

Problem with Manual Object Creation

Suppose

TradeService

depends on

TradeRepository

↓

EmailService

↓

KafkaProducer

Developer writes

TradeRepository repository =

new TradeRepository();

EmailService email =

new EmailService();

KafkaProducer kafka =

new KafkaProducer();

Question

Who manages

all these objects?

Developer.

This becomes difficult

for large applications.

What is IoC?

IoC means

Inversion of Control.

Instead of

Developer creating objects,

Spring Framework

creates objects.

Spring manages

the complete lifecycle

of every object.

Developer

only declares

what is required.

Real-Life Analogy

Imagine

you order food

from a restaurant.

Do you

cook the food?

No.

Restaurant prepares it

and serves it.

Similarly,

Spring prepares

all required objects

and gives them

to your classes.

You simply

use them.

What is a Spring Bean?

Any object

managed by

the Spring Container

is called

a

Spring Bean.

Examples

@Service

@Repository

@Component

@Controller

When Spring starts,

it creates

these objects

automatically.

What is Dependency Injection?

Question

Suppose

TradeService

needs

TradeRepository.

Should TradeService

create Repository?

No.

Repository

should be provided

from outside.

This is called

Dependency Injection.

Understanding Dependency

Example

public class TradeService{

    private TradeRepository repository;

}

Question

What is the dependency?

Answer

TradeRepository.

TradeService

depends on

TradeRepository.

Types of Dependency Injection

Spring supports

three types.

Constructor Injection

Setter Injection

Field Injection

Among these,

Constructor Injection

is the

recommended approach.

Why Constructor Injection?

Example

public class TradeService{

    private final TradeRepository repository;

    public TradeService(

        TradeRepository repository){

        this.repository = repository;

    }

}

Spring automatically

provides

TradeRepository.

Developer

never creates it.

Why Constructor Injection is Best

Advantages

• Dependencies are explicit.

• Fields become final.

• Objects become immutable.

• Easy Unit Testing.

• No Null Dependencies.

This is why

the PPT recommends

Constructor Injection.

Understanding @Component

The PPT shows

@Component

Meaning

Register this class

as a Spring Bean.

When application starts,

Spring creates

its object.

Example

@Component

public class DatabaseHealthIndicator{

}

Spring automatically

creates this object.

Understanding the PPT Code

The PPT contains

@Component("database")

public class DatabaseHealthIndicator

Step 1

@Component

Registers

DatabaseHealthIndicator

inside

Spring Container.

Step 2

Constructor

public DatabaseHealthIndicator(

DataSource ds)

Question

Who creates

DataSource?

Spring.

Question

Who passes

DataSource

to this constructor?

Spring.

Developer writes

nothing.

Step 3

private final DataSource ds;

The dependency

cannot change

after object creation.

Safer

and

thread-safe.

Exactly as demonstrated in the PPT.

Spring Bean Lifecycle

Application Starts

↓

Spring Container Starts

↓

Scans Packages

↓

Finds

@Component

@Service

@Repository

@Controller

↓

Creates Beans

↓

Injects Dependencies

↓

Application Ready

Developer

never manages

these objects manually.

Without Spring
TradeRepository repository =

new TradeRepository();

TradeService service =

new TradeService(repository);

TradeController controller =

new TradeController(service);

Developer

creates

every object.

With Spring

Developer writes

only

@Service

public class TradeService{

}

Spring automatically

creates

TradeService,

TradeRepository,

Controller,

and connects them.

Huge reduction

in boilerplate code.

Real Banking Example

Request arrives

↓

TradeController

↓

TradeService

↓

TradeRepository

↓

Database

Question

Did Controller create

TradeService?

No.

Spring injected it.

Question

Did Service create

Repository?

No.

Spring injected it.

Everything is managed

by the Spring Container.

Why IoC Improves Testing?

Suppose

TradeService

depends on

TradeRepository.

During Unit Testing,

instead of

Real Repository,

Mockito provides

Mock Repository.

Constructor Injection

makes this

extremely easy.

Exactly why

Day 3

used Mockito

with

constructor-based dependencies.

Common Mistakes

• Creating Spring Beans using new.

• Using Field Injection instead of Constructor Injection.

• Making dependencies mutable.

• Mixing Controller and Service logic.

• Allowing modules to depend on each other in both directions.

Best Practices

• Use Layered Architecture.

• Keep dependencies one-way.

• Prefer Constructor Injection.

• Make injected fields private final.

• Keep the Domain module independent of Spring.

• Let Spring manage Bean creation.

Questions to Ask Participants
What is Layered Architecture?
Why do enterprise applications use multiple modules?
What is Inversion of Control?
What is a Spring Bean?
What is Dependency Injection?
Why is Constructor Injection preferred?
Who creates Spring Beans?
What is the responsibility of the Spring Container?
Interview Questions
Question 1

What is Inversion of Control (IoC)?

Answer

Inversion of Control is a design principle where the Spring Framework, rather than the application code, creates, configures, and manages object lifecycles and dependencies.

Question 2

What is Dependency Injection?

Answer

Dependency Injection is a technique in which required dependencies are supplied to a class by the Spring Container instead of being created within the class, promoting loose coupling and easier testing.

Question 3

Why is Constructor Injection preferred over Field Injection?

Answer

Constructor Injection makes dependencies explicit, supports immutable (final) fields, simplifies unit testing with mocks, prevents partially initialized objects, and improves overall maintainability.

Question 4

What is a Spring Bean?

Answer

A Spring Bean is any object whose lifecycle is managed by the Spring IoC Container. Beans are typically declared using annotations such as @Component, @Service, @Repository, or @Controller.

Knowledge Check (Slide 15)

Question

In ReconX, why are dependencies passed through the constructor (for example, DatabaseHealthIndicator(DataSource ds))?

Correct Answer

B. Constructor injection makes dependencies explicit and final, and it allows unit tests to easily inject mock implementations. This improves readability, immutability, and testability.

Key Takeaways

• Enterprise Spring Boot applications are organized into layers and modules.

• The Spring IoC Container creates and manages application objects.

• Spring-managed objects are called Beans.

• Dependency Injection removes the need for manual object creation.

• Constructor Injection is the recommended dependency injection approach.

• Multi-module architecture keeps applications modular, maintainable, and scalable.

Transition to Next Section

The next section covers Multi-Module Maven Projects and Spring Profiles. We'll learn how a single codebase can support multiple environments (dev, uat, and prod), how Maven organizes enterprise applications into independent modules, and how Spring Profiles switch configurations such as H2 and PostgreSQL without changing application code.

next

Excellent. This section covers Slides 9–12, which explain Multi-Module Maven Architecture and Spring Profiles. These are essential concepts in enterprise projects because large applications are split into independent modules, and the same application is deployed to multiple environments such as Development, UAT, and Production without changing the code.

DAY 4 – SPRING BOOT: ENTERPRISE ARCHITECTURE
MODULE 2 – OBSERVABILITY & ERROR HANDLING
SECTION 5 – Spring Boot Actuator, Health Indicators & Global Exception Handling

(Based on Slides 23–28)

Learning Objective

After completing this section, participants will be able to:

• Understand Spring Boot Actuator.

• Learn production monitoring.

• Create Custom Health Indicators.

• Understand Global Exception Handling.

• Learn @ControllerAdvice and @ExceptionHandler.

• Understand RFC 7807 Problem Details.

• Build standardized enterprise error responses.

Trainer Introduction

Suppose

ReconX

is deployed

to Production.

Question

How do we know

whether

the application

is healthy?

Can users

log in?

Is Database

working?

Is Kafka

connected?

Is Disk Space

available?

Without monitoring,

Operations Team

does not know

whether

the application

is healthy.

Spring Boot provides

Actuator.

What is Spring Boot Actuator?

Spring Boot Actuator

is a production-ready module

that provides

information

about

the running application.

It exposes endpoints

that help developers

and operations teams

monitor the application.

Why Do We Need Actuator?

Suppose

Application

starts successfully.

After two hours,

Database goes down.

Question

Will users know immediately?

Maybe not.

Question

Will the Operations Team know?

Yes,

if Actuator

is enabled.

It continuously reports

application health.

Real Banking Example

ReconX

runs inside Production.

Operations Team opens

/actuator/health

Response

UP

Everything is working.

If Database fails

Response

DOWN

Operations Team immediately investigates.

Common Actuator Endpoints

Spring Boot provides

/actuator/health

/actuator/info

/actuator/metrics

/actuator/env

/actuator/beans

/actuator/mappings

Each endpoint

provides

different information.

/actuator/health

Most frequently used endpoint.

Purpose

Checks

application health.

Example Response

{
  "status":"UP"
}

If a critical dependency fails

{
  "status":"DOWN"
}

Monitoring tools

read this endpoint

continuously.

/actuator/info

Provides

application details.

Example

{
  "application":"ReconX",
  "version":"1.0.0",
  "environment":"Production"
}

Useful

for deployment verification.

/actuator/metrics

Shows runtime metrics

such as

• JVM Memory

• CPU Usage

• Request Count

• Garbage Collection

• Active Threads

These metrics

can be visualized

using Grafana.

What is a Health Indicator?

Sometimes

built-in health checks

are not enough.

Suppose

ReconX

must verify

Database

Kafka

External Pricing API.

Spring allows us

to create

Custom Health Indicators.

Custom Health Indicator

The PPT shows

a class similar to

@Component

public class DatabaseHealthIndicator

implements HealthIndicator

Meaning

Spring registers

a custom health check.

Whenever

/actuator/health

is called,

Spring executes

this class.

How a Health Indicator Works

Application Starts

↓

Spring creates

DatabaseHealthIndicator

↓

Health Endpoint called

↓

Database Connection Checked

↓

Return

UP

or

DOWN

Automatically.

Real Banking Example

Suppose

PostgreSQL

is unavailable.

Custom Health Indicator

returns

{
  "status":"DOWN",
  "details":{
    "database":"Connection Failed"
  }
}

Operations Team

receives an alert

before users report problems.

What is Exception Handling?

Question

Suppose

user requests

Trade

TRD9999

Trade

does not exist.

Without exception handling

Application returns

500 Internal Server Error

Not helpful.

Enterprise applications

return

meaningful error messages.

Traditional Exception Handling

Controller

try{

   ...

}catch(Exception e){

}

Problems

• Repeated code.

• Difficult maintenance.

• Inconsistent responses.

Global Exception Handling

Spring Boot provides

@ControllerAdvice

Meaning

Handle exceptions

for

every controller

from one place.

No repeated

try-catch blocks.

@ExceptionHandler

Example

@ExceptionHandler(
TradeNotFoundException.class)

Whenever

TradeNotFoundException

occurs,

Spring automatically

calls

this method.

No manual handling

inside controllers.

Real ReconX Flow

Client

↓

TradeController

↓

TradeService

↓

Trade Not Found

↓

Throw

TradeNotFoundException

↓

@ControllerAdvice

↓

Standard Error Response

↓

Client

Why Global Exception Handling?

Advantages

• Centralized error handling.

• Cleaner Controllers.

• Consistent responses.

• Easier maintenance.

• Better logging.

• Easier monitoring.

What is RFC 7807?

RFC 7807

is an international standard

for

REST API error responses.

Instead of

returning

random JSON,

every API

returns

the same structure.

Standard Problem Details Response

Example

{
  "type":"about:blank",
  "title":"Trade Not Found",
  "status":404,
  "detail":"Trade TRD1001 not found",
  "instance":"/api/trades/TRD1001"
}

Every client

understands

this format.

Exactly as recommended

in enterprise REST APIs.

Meaning of Each Field
type

Type of error.

title

Short description.

status

HTTP Status Code.

detail

Detailed explanation.

instance

API URL

where error occurred.

Real Banking Example

Client requests

GET /api/trades/TRD9999

Trade does not exist.

Response

{
  "title":"Trade Not Found",
  "status":404,
  "detail":"Trade TRD9999 does not exist"
}

Client

immediately understands

the problem.

Complete Error Flow

Client

↓

REST API

↓

Controller

↓

Service

↓

Exception

↓

@ControllerAdvice

↓

RFC 7807 Response

↓

Client

This keeps

every API

consistent.

Why Standard Error Responses?

Suppose

Frontend Team

calls

20 APIs.

If every API

returns

different error formats,

Frontend becomes

difficult.

RFC 7807

ensures

every API

returns

the same structure.

Common Mistakes

• Writing try-catch blocks in every controller.

• Returning stack traces to clients.

• Returning HTTP 200 for failures.

• Using different error formats across APIs.

• Ignoring application health monitoring.

Best Practices

• Enable Spring Boot Actuator in production.

• Create custom Health Indicators for important dependencies.

• Use @ControllerAdvice for centralized exception handling.

• Return appropriate HTTP status codes.

• Follow RFC 7807 for consistent error responses.

• Log exceptions internally, but expose only necessary information to clients.

Questions to Ask Participants
What is Spring Boot Actuator?
Why do we need /actuator/health?
What is a Health Indicator?
Why should we use @ControllerAdvice?
What is @ExceptionHandler?
What is RFC 7807?
Why should all APIs return the same error format?
Why should stack traces not be exposed to clients?
Interview Questions
Question 1

What is Spring Boot Actuator?

Answer

Spring Boot Actuator provides production-ready endpoints for monitoring and managing applications, including health checks, metrics, environment information, and application details.

Question 2

What is the purpose of @ControllerAdvice?

Answer

@ControllerAdvice centralizes exception handling across all controllers, allowing applications to return consistent error responses without repeating try-catch blocks.

Question 3

What is RFC 7807?

Answer

RFC 7807 defines a standardized JSON structure for REST API error responses, including fields such as type, title, status, detail, and instance, making error handling consistent across services.

Question 4

Why are Custom Health Indicators useful?

Answer

Custom Health Indicators allow applications to verify the health of critical dependencies such as databases, Kafka, messaging systems, or external APIs, enabling proactive monitoring and alerting.

Knowledge Check (Slides 27–28)

Question

Why does ReconX implement a custom DatabaseHealthIndicator and use RFC 7807?

Correct Answer

C. The custom Health Indicator allows Operations teams to monitor database availability through Actuator, while RFC 7807 ensures every API returns a consistent, standards-based error response that is easy for clients to understand and process.

Key Takeaways

• Spring Boot Actuator provides production-ready monitoring endpoints.

• /actuator/health is the primary endpoint used to verify application health.

• Custom Health Indicators extend monitoring to business-specific dependencies.

• @ControllerAdvice centralizes exception handling.

• @ExceptionHandler maps exceptions to appropriate HTTP responses.

• RFC 7807 standardizes REST API error responses, improving consistency across clients and services.

• These features make Spring Boot applications easier to monitor, maintain, and operate in production environments.

Day 4 Final Summary

By the end of Day 4, participants should be able to:

• Build a layered Spring Boot backend using IoC and Dependency Injection.

• Organize enterprise applications using Multi-Module Maven architecture.

• Configure multiple environments with Spring Profiles.

• Map Java objects to database tables using JPA and Hibernate.

• Design entity relationships and expose DTOs instead of entities.

• Use MapStruct for compile-time object mapping.

• Build repositories with Spring Data JPA and create dynamic search functionality using Specifications.

• Monitor production applications using Spring Boot Actuator and Custom Health Indicators.

• Implement centralized exception handling with @ControllerAdvice.

• Return standards-compliant error responses using RFC 7807 Problem Details.

This completes the Day 4 – Spring Boot: Enterprise Architecture trainer handbook based on the presentation.