DAY 3 – FUNCTIONAL JAVA & THE RECON ENGINE
INTRODUCTION – DAY 3 OVERVIEW

(Based on Slides 2–4)

Learning Objective

After completing Day 3, participants will be able to:

• Understand Functional Programming in Java.
• Use Collections and the Streams API.
• Write Lambda Expressions.
• Use Functional Interfaces such as Predicate, Function, Consumer and Supplier.
• Use Collectors for grouping, aggregation and analytics.
• Calculate VWAP (Volume Weighted Average Price).
• Build asynchronous applications using CompletableFuture.
• Understand Optional for null-safe programming.
• Write Unit Tests using JUnit 5.
• Mock dependencies using Mockito.
• Perform Integration Testing using Testcontainers.
• Measure code coverage using JaCoCo.


Yesterday (Day 2),

we created our Domain Model.
We built Trade Objects
Money Objects
TradeRef
Enums
Builder Pattern
Factory Pattern
SOLID Principles

Question
Can our application reconcile trades now?
Answer

No.

We have data, but nothing is processing it.

Today,

we build the Heart of ReconX.

The Reconciliation Engine.
The engine receives

Internal Trades
↓
External Trades
↓
Compares them
↓
Returns
MATCH
or
BREAK

This is exactly what real Investment Banks do every day.

What We Built So Far

Day 1
↓
Database
↓
Tables
↓
Indexes
↓
Liquibase


Day 2
↓
Java Objects
↓
Trade Hierarchy
↓
Validation
↓
Business Rules


Day 3
↓
Processing Engine
↓
Streams
↓
Parallel Processing
↓
Testing


Now the application finally starts doing real work.

What is the Recon Engine?
The Recon Engine is the core business component of ReconX.
Its responsibility is

Receive Internal Trades
↓
Receive External Trades
↓
Compare Both Trades
↓
Identify Matches
↓
Identify Breaks
↓

Store Results Everything else
REST API
Kafka
React
Grafana

only interacts with this engine.
The engine performs the actual reconciliation.
Real-Time Banking Example
Suppose Deutsche Bank receives
Internal Trade
Trade Ref : TRD1001
Quantity : 100
Price : 250

External System sends
Trade Ref : TRD1001
Quantity : 100
Price : 250

Recon Engine
↓
Compares both trades
↓

Result
MATCH



What Will We Learn Today?

Module 1
Functional Java

Topics

• Streams
• Lambda Expressions
• Functional Interfaces
• Collectors
• VWAP Calculation
• CompletableFuture

Module 2
Testing
Topics
• JUnit 5
• AssertJ
• Mockito
• Testcontainers
• JaCoCo

These topics are used in almost every modern Java enterprise application.

Where Day 3 Fits

The PPT explains the complete journey.

Day 1
Database
↓
Day 2
Business Objects
↓
Day 3
Business Processing
↓
Day 4
Spring Boot
↓
Day 5
REST APIs
↓
Day 6
Performance
↓
Day 7
Frontend
↓
Day 8
React
↓
Day 9
Kafka
↓
Day 10
Docker & CI/CD
Notice

This approach connects theory with real enterprise implementation.


Why Functional Programming?

Traditional Java

for(Trade trade : trades){
    if(trade.getPrice()>100){
        result.add(trade);
    }
}

Modern Java

trades.stream().filter(t -> t.getPrice()>100).toList();

Question
Which code is easier to understand?
The second one.
Functional Programming focuses on
"What to do" instead of "How to do it."

This makes code cleaner and easier to maintain.
What Makes Functional Java Powerful?

Instead of writing Loops
Counters
Temporary Variables
Manual Collections

we use
Streams
↓
Lambdas
↓
Collectors
↓
Parallel Processing
↓



Module 1 – Functional Programming in Java 8
Part 1 – Java 8 Interface Enhancements

Before Java 8, interfaces could contain only abstract methods.
Example
interface Calculator {
    int add(int a, int b);
}

Every class implementing this interface had to provide the implementation.
Problem Before Java 8
Suppose Java already has an interface.

interface Vehicle {
    void start();
}

Thousands of developers have implemented it.
class Car implements Vehicle{
    public void start(){
        System.out.println("Car Started");
    }
}
Now Java wants to add a new method.

void stop();
Immediately every existing class breaks.
Car
Bike
Bus
Truck
...
must implement stop()
Thousands of compilation errors.
Java needed a solution.
Java 8 Solution

Java 8 introduced
default methods
static methods

Default Method
A default method has a body.
Implementing classes may override it, but they are not required to.

Example
interface Vehicle {
    void start();
    default void stop() {
        System.out.println("Vehicle Stopped");
    }
}
class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("Car Started");
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle vehicle = new Car();
        vehicle.start();
        vehicle.stop();
    }
}

Output
Car Started
Vehicle Stopped

Notice

Car never implemented
stop()
Still it works.
Why Default Method?
To add new functionality to an existing interface without breaking old implementations.

Static Method
A static method belongs to the interface itself.
It is called using the interface name.
Example
interface Calculator {
    static int add(int a, int b) {
        return a + b;
    }
}
public class Main {
    public static void main(String[] args) {
        System.out.println(
                Calculator.add(10,20)
        );
    }
}

Output

30


Difference
| Default Method                  | Static Method               |
| ------------------------------- | --------------------------- |
| Called using object             | Called using interface name |
| Can be overridden               | Cannot be overridden        |
| Provides default implementation | Utility/helper method       |


Banking Example
interface TradeService {
    void processTrade();
    default void logTrade() {
        System.out.println("Trade Logged");
    }
    static void version() {
        System.out.println("ReconX Version 1.0");
    }
}

Why Java 8 Enhanced Interfaces?

Because Java wanted to
Add new methods
Keep old applications working
Avoid breaking existing implementations
Next Topic

Once students understand why Java enhanced interfaces, then introduce:

Interface
↓
Exactly One Abstract Method
↓
Functional Interface
↓


Functional Interface
What is a Functional Interface?

A Functional Interface is an interface that contains exactly one abstract method.
In simple words,
One Interface = One Abstract Method
Why Do We Need Functional Interfaces?
Before Java 8, we used classes to implement interfaces.
Example

interface Greeting {
    void sayHello();
}

To use this interface, we had to create a class.
class GreetingImpl implements Greeting {
    @Override
    public void sayHello() {
        System.out.println("Hello");
    }
}

Then create an object.

Greeting greeting = new GreetingImpl();
greeting.sayHello();

Too much code for one small method.
Java 8 introduced Functional Interfaces so that we can use 
Lambda Expressions instead of creating separate implementation classes.

Rules of Functional Interface
A Functional Interface
Must have only one abstract method

It can also have
default methods
static methods

because they already have implementations.

Valid Functional Interface

@FunctionalInterface
interface Greeting {
    void sayHello();
}

Only one abstract method.
This is a Functional Interface.

Complete Working Example

Greeting.java

@FunctionalInterface
interface Greeting {
    void sayHello();
}

GreetingImpl.java
class GreetingImpl implements Greeting {
    @Override
    public void sayHello() {
        System.out.println("Hello Java");
    }
}

Main.java
public class Main {
    public static void main(String[] args) {
        Greeting greeting = new GreetingImpl();
        greeting.sayHello();
    }
}

Output
Hello Java

Another Example

@FunctionalInterface
interface Calculator {
    int add(int a, int b);
}
Implementation
class CalculatorImpl implements Calculator {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}

Main
public class Main {
    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();
        System.out.println(calculator.add(10,20));
        Calculator c2 = (a,b)->a+b;
        System.out.println(c2.add(1,2));
    }
}

Output
30

Why @FunctionalInterface?
The annotation
@FunctionalInterface tells the compiler

This interface must always contain exactly one abstract method.
If someone accidentally adds another abstract method,
the compiler gives an error.

Example
@FunctionalInterface
interface Greeting {
    void sayHello();
    void sayBye();
}

Compilation Error
Unexpected @FunctionalInterface annotation
Greeting is not a functional interface

Multiple abstract methods found
Functional Interface with Default Method

This is perfectly valid.

@FunctionalInterface
interface Greeting {
    void sayHello();
    default void welcome() {
        System.out.println("Welcome");
    }
}

Why?    Because
default void welcome()
already has an implementation.
Only void sayHello(); is abstract.
So the interface is still functional.
Functional Interface with Static Method

Also valid.
@FunctionalInterface
interface Calculator {
    int add(int a,int b);
    static void version(){
        System.out.println("Version 1.0");
    }
}


Still only one abstract method.

Invalid Functional Interface
interface Demo {
    void m1();
    void m2();
}

Question
Can we use Lambda?
No.
Because there are two abstract methods.
Real Banking Example

Suppose the bank validates trades.

@FunctionalInterface
interface TradeValidator {
    boolean validate(String tradeRef);
}

Implementation
class TradeValidatorImpl implements TradeValidator {
    @Override
    public boolean validate(String tradeRef) {
        return tradeRef.startsWith("TRD");
    }
}

Main

public class Main {
    public static void main(String[] args) {
        TradeValidator validator =new TradeValidatorImpl();
        System.out.println(
                validator.validate("TRD101")
        );
    }
}

Output
true

Why Functional Interfaces?
Without Functional Interface

Interface
↓
Implementation Class
↓
Object
↓

Call Method
Too many steps. With Functional Interface + Lambda (next topic)

Interface
↓
Lambda
↓
Done

No implementation class required.
Common Functional Interfaces in Java
Java already provides many Functional Interfaces.



Predicate
↓
Condition

----------------

Function
↓
Transformation

----------------

Consumer
↓
Uses Object

----------------

Supplier
↓
Creates Object



We'll learn these after Lambda Expressions.

Advantages
Less code
Easy to read
Supports Lambda Expressions
Supports Functional Programming
Used heavily in Stream API
Used throughout Spring Boot


Easy Way to Remember
Normal Interface
↓
Many Abstract Methods
Cannot use Lambda

----------------------------

Functional Interface
↓
One Abstract Method
↓
Can use Lambda
✔
One-Line Rule
A Functional Interface is an interface with exactly one abstract method, making it suitable for Lambda Expressions.




What is a Lambda Expression?
A Lambda Expression is a short way to implement a Functional Interface.
Instead of creating a separate implementation class, we can write the implementation directly.
Lambda Expression = Anonymous Function
It has no method name, no class name, and is used to pass behavior as data.
Why Were Lambda Expressions Introduced?
Before Java 8, implementing a Functional Interface required creating a class.

Example

interface Greeting {
    void sayHello();
}

Implementation Class

class GreetingImpl implements Greeting {
    @Override
    public void sayHello() {
        System.out.println("Hello Java");
    }
}

Main

Greeting greeting = new GreetingImpl();
greeting.sayHello();

This is a lot of code for one small method.
Java 8 introduced Lambda Expressions to reduce this unnecessary code.

Using Lambda
Instead of creating a class, simply write

Greeting greeting = () -> System.out.println("Hello Java");
greeting.sayHello();

Much shorter and easier to read.
Lambda Syntax
General Syntax

(parameters) -> expression

or

(parameters) -> {
    statements
}

Understanding the Syntax

Example

(name) -> System.out.println(name);

Part	Meaning
(name)	Input Parameter ->	Lambda Operator
System.out.println(name)	Method Implementation

Example 1 – No Parameter

Functional Interface
@FunctionalInterface
interface Greeting {
    void sayHello();
}
Main Class
public class Main {
    public static void main(String[] args) {
        Greeting greeting = () -> System.out.println("Hello Java 8");
        greeting.sayHello();
    }

}
Output
Hello Java 8

Example 2 – One Parameter

Functional Interface
@FunctionalInterface
interface Message {
    void print(String name);
}

Main Class
public class Main {
    public static void main(String[] args) {
        Message message = name -> System.out.println("Hello " + name);
        message.print("Akash");
    }
}
Output
Hello Akash

Notice

For one parameter, parentheses are optional.

name -> System.out.println(name)

is also correct.

Example 3 – Multiple Parameters

Functional Interface

@FunctionalInterface
interface Calculator {
    int add(int a, int b);
}

Main Class
public class Main {
    public static void main(String[] args) {
        Calculator calculator = (a, b) -> a + b;
        System.out.println(calculator.add(10, 20));
    }
}

Output
30

Example 4 – Multiple Statements
When more than one statement is required, use { }.

@FunctionalInterface
interface Square {
    int findSquare(int number);
}

public class Main {
    public static void main(String[] args) {
        Square square = number -> {
            System.out.println("Finding Square");
            return number * number;
        };
        System.out.println(square.findSquare(5));
    }
}

Output
Finding Square
25

Real Banking Example
Suppose a bank wants to check whether a trade amount is greater than ₹10,00,000.

Functional Interface
@FunctionalInterface
interface TradeValidator {
    boolean validate(double amount);
}

Main Class
public class Main {
    public static void main(String[] args) {
        TradeValidator validator = amount -> amount > 1000000;
        System.out.println(validator.validate(500000));
        System.out.println(validator.validate(2500000));
    }
}

Output
false
true

Before Java 8 vs Java 8

Method Reference

Sometimes a Lambda only calls an existing method.
Instead of
name -> System.out.println(name)
we can write

System.out::println

This is called a Method Reference.
It makes the code even shorter.

Real-Time Banking Example

Suppose ReconX has a list of trade IDs.

List<String> trades = List.of(
        "TRD101",
        "TRD102",
        "TRD103"
);

Print using Lambda
trades.forEach(trade -> System.out.println(trade));

Print using Method Reference
trades.forEach(System.out::println);

Both produce the same output.

A Lambda Expression is a short and 
concise way to implement a Functional Interface without creating a 
separate implementation class.



Predefined Functional Interfaces
Why Predefined Functional Interfaces?
Suppose every developer creates their own interfaces.

interface Validator{
    boolean validate(String value);
}

interface Checker{
    boolean check(String value);
}

interface Filter{
    boolean filter(String value);
}

Everyone is creating different interfaces for the same job.

Java 8 solved this problem by providing ready-made Functional Interfaces.
The most commonly used are

Predicate
Function
Consumer
Supplier

Easy Way to Remember
Input
        │
        ▼
Predicate
Check Condition
↓
true / false

-------------------------

Function
Convert
↓
Another Object

-------------------------

Consumer
Use Object
↓
No Return

-------------------------

Supplier

Create Object
↓
Return Object

These four interfaces are available in

java.util.function
1. Predicate
What is Predicate?

A Predicate checks a condition.
It always returns
true    or  false

Method

boolean test(T t)
Simple Example

Question
Is the number greater than 10?
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Predicate<Integer> greaterThanTen =number -> number > 10;
        System.out.println(greaterThanTen.test(5));
        System.out.println(greaterThanTen.test(15));
    }
}

Output
false
true

Banking Example

Check whether trade amount is greater than ₹10 Lakhs.

import java.util.function.Predicate;
public class Main {
    public static void main(String[] args) {
        Predicate<Double> highValueTrade =amount -> amount > 1000000;
        System.out.println(highValueTrade.test(500000));
        System.out.println(highValueTrade.test(2500000));
    }
}

Output

false
true

Predicate in Stream
List<Integer> numbers = List.of(5,10,15,20);

numbers.stream()
       .filter(number -> number > 10)
       .forEach(System.out::println);

Output

15
20

Predicate is used inside
filter()

2. Function
What is Function?
A Function converts one object into another.
Method
R apply(T t)

Simple Example

Convert String into Uppercase.
import java.util.function.Function;
public class Main {
    public static void main(String[] args) {
        Function<String,String> upper =name -> name.toUpperCase();
        System.out.println(
                upper.apply("akash")
        );
    }
}

Output

AKASH
Banking Example

Convert Trade object into Trade Reference.
import java.util.function.Function;
public class Main {
    public static void main(String[] args) {
        Trade trade = new Trade("TRD101");
        Function<Trade,String> tradeRef =Trade::tradeRef;
        System.out.println(
                tradeRef.apply(trade)
        );
    }
}

record Trade(String tradeRef){}

Output

TRD101
Function in Stream
List<String> names = List.of("Akash","Rahul","Amit");

names.stream().map(String::toUpperCase).forEach(System.out::println);

Output

AKASH
RAHUL
AMIT

Function is used inside

map()

3. Consumer
What is Consumer?
A Consumer uses an object.
It does not return anything.

Method
void accept(T t)

Simple Example

import java.util.function.Consumer;
public class Main {
    public static void main(String[] args) {
        Consumer<String> printer =System.out::println;
        printer.accept("Hello Java");
    }
}

Output

Hello Java
Banking Example

Print Trade Reference.

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        Consumer<Trade> printer =trade -> System.out.println(trade.tradeRef());
        printer.accept(new Trade("TRD101"));
    }
}

record Trade(String tradeRef){}

Output

TRD101
Consumer in Stream
List<String> trades =List.of("TRD101","TRD102","TRD103");

trades.forEach(System.out::println);

Consumer is used inside
forEach()

4. Supplier

What is Supplier?
A Supplier creates or supplies an object.
Input
Nothing

Output
One Object

Method

T get()
Simple Example
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        Supplier<String> company =
                () -> "ReconX";
        System.out.println(company.get());
    }
}

Output

ReconX
Banking Example

Generate Trade ID.

import java.util.UUID;
import java.util.function.Supplier;
public class Main {
    public static void main(String[] args) {
        Supplier<String> tradeId =() -> UUID.randomUUID().toString();
        System.out.println(tradeId.get());
    }
}

Output

e4c7a2c4-fad8-4d42-b2c4-3efb9e9c0d81

(Output will be different every time.)

Summary Table

| Functional Interface | Input      | Output         | Method     | Stream Use      |
| -------------------- | ---------- | -------------- | ---------- | --------------- |
| Predicate            | One Object | boolean        | `test()`   | `filter()`      |
| Function             | One Object | Another Object | `apply()`  | `map()`         |
| Consumer             | One Object | Nothing        | `accept()` | `forEach()`     |
| Supplier             | Nothing    | One Object     | `get()`    | Object Creation |



Source :

Collection 
Array 

How Streams Use Functional Interfaces


List
      │
      ▼
stream()
      │
      ▼
filter()

Predicate
      │
      ▼
map()

Function
      │
      ▼
forEach()

Consumer
      │
      ▼
Done

Supplier is generally used before the stream starts to create or supply objects.


Next Topic

Now students are ready to learn Stream API, where they'll see these interfaces working together:

source ---> stream --> one or many intermediate operator --> terminal operator 

stream()

filter() → Predicate

map() → Function

sorted()---->

collect()
toList()

forEach() → Consumer

This makes the connection between Functional Programming and real-world Java development very clear.


Immutable Data

This produces Cleaner Code Better Performance Easier Testing

Real ReconX Flow

Internal Trades
        +
External Trades
        ↓
Streams API
        ↓
Matching Engine
        ↓
Collectors
        ↓
Recon Results
        ↓
Database
        ↓
Dashboard

Every major topic today contributes to this pipeline.


Transition to Module 1

The next section begins 
Module 1 – Functional Java, starting with Collections & the Streams API. We will learn how to process collections declaratively, build efficient lookup maps with Collectors.toMap(), eliminate manual loops, and implement the trade-matching pipeline used by the ReconX Reconciliation Engine.

DAY 3 – FUNCTIONAL JAVA & THE RECON ENGINE
MODULE 1 – FUNCTIONAL JAVA
SECTION 1 – Collections & the Streams API

(Based on Slides 5–8)

Learning Objective

After completing this section, participants will be able to:
• Understand Java Collections and Streams.
• Learn why Streams were introduced.
• Understand Stream Pipelines.
• Use map(), filter(), collect() and Collectors.toMap().
• Build lookup Maps efficiently.
• Understand parallelStream().
• Read and understand the ReconX reconciliation pipeline.


Before Java 8,

every collection operation required Manual Loops.

Example

List<Trade> result = new ArrayList<>();
for(Trade trade : trades){
    if(trade.getPrice().compareTo(new BigDecimal("100"))>0){
        result.add(trade);
    }
}

Question

What is wrong with this code?
Nothing. But imagine 100 operations.

Developers write
100 loops.
Large applications become difficult to maintain.
Java 8 introduced

Streams.
Now we describe
What we want, instead of
How to iterate.

What is a Collection?
A Collection is an object that stores multiple elements together.

Examples
ArrayList
LinkedList
HashSet
TreeSet
HashMap
TreeMap

Suppose
ReconX imports
10,000 trades.
We cannot create
Trade trade1;
Trade trade2;
Trade trade3;

Instead

List<Trade> trades;

One collection stores
Thousands of objects.

Real Banking Example
Suppose today's trade file contains

TRD001
TRD002
TRD003
TRD004
TRD005

Instead of creating five variables,

store them inside

List<Trade>

Collections are designed to manage groups of objects efficiently.

Why Were Streams Introduced?

Traditional Collection Processing
for()
if()
add()
counter++
temporary variables
Large amount of code.

Java Streams provide
Declarative Programming.
Example

trades.stream().filter(t -> t.price()>100).toList();

Read it like English.

"Take trades,filter expensive trades,return list."

What is a Stream?
A Stream is not a collection.
A Stream is a Pipeline that processes data.

Think of it like a factory assembly line.

Collection
↓
Stream
↓
Filter
↓
Map
↓
Collect
↓
Result


Data flows through the pipeline.

Important Characteristics of Streams

Streams
• Do not store data.
• Process data from collections.
• Process elements one by one.
• Support lazy evaluation.
• Can run in parallel.
• Usually do not modify the original collection.


Java Stream Pipeline
What is a Stream Pipeline?

A Stream Pipeline is a sequence of operations performed on data.

It has three stages.

Source
   ↓
Intermediate Operations
   ↓
Terminal Operation

Think of it like a water pipeline.

Water Tank
      ↓
Water Filter
      ↓
Tap

Similarly,

Collection
      ↓
Filter / Map / Sort
      ↓
Result

Stage 1 – Source
A Stream always starts with a Source.
The source provides data to the Stream.

Example

List<Trade> trades = List.of(...);
trades.stream();

Here,

List<Trade>
↓
Stream<Trade>

stream() converts a collection into a Stream.

Stage 2 – Intermediate Operations
Intermediate operations process the data.
Common operations are:

filter()
map()
sorted()
distinct()
limit()
skip()

These operations do not execute immediately.
They simply build the pipeline.

Stage 3 – Terminal Operation
A terminal operation finishes the pipeline and produces the final result.

Examples:

.toList()               java 16 onwards 
.collect(...)
.count()
.forEach(...)
.findFirst()
.anyMatch(...)

Without a terminal operation, nothing happens.

Example
List<String> names = List.of("John", "Peter", "David");

names.stream().filter(name -> name.length() > 4);

Output

Nothing.

Why?

Because there is no terminal operation.
Now add one.

List<String> result = names.stream()
        .filter(name -> name.length() > 4)
        .toList();

System.out.println(result);

Output

[Peter, David]
Stream Pipeline Example

Suppose we have trades.

TRD101  MATCHED
TRD102  PENDING
TRD103  MATCHED
TRD104  FAILED

Requirement

Return only matched trades.

List<Trade> matchedTrades = trades.stream()
        .filter(t -> t.status() == TradeStatus.MATCHED)
        .toList();

How It Works
List<Trade>
      │
      ▼
stream()
      │
      ▼
filter()
      │
      ▼
toList()
      │
      ▼

Matched Trades
No loops.
No temporary list.
Very clean code.

Understanding filter()

What is filter()?
filter() selects only the elements that satisfy a condition.

Syntax
.filter(condition)

If the condition is true, the element is kept.
If the condition is false, it is removed.

Example 1
List<Integer> numbers = List.of(10, 20, 30, 40, 50);

List<Integer> result = numbers.stream()
        .filter(n -> n > 25)
        .toList();

System.out.println(result);

Output

[30, 40, 50]

Banking Example
Return matched trades.

List<Trade> matchedTrades = trades.stream()
        .filter(t -> t.status() == TradeStatus.MATCHED)
        .toList();

Only matched trades are returned.

Another Banking Example

Return trades whose price is greater than 500.

List<Trade> expensiveTrades = trades.stream()
        .filter(t -> t.price().compareTo(new BigDecimal("500")) > 0)
        .toList();

Only expensive trades remain.

Understanding map()

What is map()?
map() transforms one object into another.
It changes the data, not the number of elements.

Example

Suppose we have
Trade Object
We only want
Trade Reference

List<String> tradeRefs = trades.stream()
        .map(Trade::tradeRef)
        .toList();

Input
Trade

Output

String
Number Example

List<Integer> numbers = List.of(1,2,3,4);

List<Integer> squares = numbers.stream()
        .map(n -> n * n)
        .toList();

System.out.println(squares);

Output

[1, 4, 9, 16]

filter()                    vs      map()
filter()	                        map()
Selects data	                    Transforms data
Removes unwanted elements	        Changes each element
Number of elements may decrease	    Number of elements usually stays the same

Example

.filter(t -> t.status() == TradeStatus.MATCHED)

Keeps only matched trades.

.map(Trade::tradeRef)

Converts each Trade into its trade reference.

Understanding collect()

Before Java 16, we used

.collect(Collectors.toList())

to convert a Stream into a List.

List<String> names = List.of("John","Peter","David");

List<String> result = names.stream()
        .filter(name -> name.length() > 4)
        .collect(Collectors.toList());

Output

[Peter, David]
Java 16

Java introduced

.toList()

Now we can simply write

List<String> result = names.stream()
        .filter(name -> name.length() > 4)
        .toList();

Cleaner and easier to read.

When to use collect()?
Use collect() when you need something other than a simple List.

Examples
Collectors.toSet()
Creates a Set.

Collectors.toMap()
Creates a Map.

Collectors.groupingBy()
Groups data.

Collectors.joining()
Joins Strings.

Understanding Collectors.toMap()

Suppose we have Trade Object
We want to search by Trade Reference

Instead of searching through a List every time,

we create a Map.

Key
↓
Trade Reference
Value
↓

Trade Object
Without toMap()
for(Trade trade : trades){
    if(trade.tradeRef().equals("TRD9000")){
        return trade;
    }
}

Time Complexity
O(n)
Every trade may need to be checked.

With toMap()
Map<String, Trade> tradeMap = trades.stream()
        .collect(Collectors.toMap(
                Trade::tradeRef,
                Function.identity()
        ));

Searching
Trade trade = tradeMap.get("TRD9000");
Time Complexity

O(1)

Very fast.
Understanding Function.identity()


Many students find this confusing.

Actually, Function.identity()
means
t -> t

Both are exactly the same.

Example
Collectors.toMap(
        Trade::tradeRef,
        Function.identity()
)

is equivalent to

Collectors.toMap(
        Trade::tradeRef,
        trade -> trade
)

Here,

Key → trade.tradeRef()
Value → Complete Trade object

Summary
Method	Purpose

stream()	            Creates a Stream from a collection
filter()	            Selects elements based on a condition
map()	                Transforms one object into another
sorted()	            Sorts the elements
distinct()	            Removes duplicate elements
collect()	            Collects elements into List, Set, Map, etc.
toList()	            Collects elements into an unmodifiable List (Java 16+)
Collectors.toMap()	    Converts a Stream into a Map
Function.identity()	    Returns the same object (t -> t)



TradeStatus.java

public enum TradeStatus {

    MATCHED,
    PENDING,
    FAILED

}

Trade.java (Java Record)
import java.math.BigDecimal;

public record Trade(

        String tradeRef,
        String symbol,
        BigDecimal price,
        int quantity,
        TradeStatus status

) {
}

Main.java
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<Trade> trades = List.of(

                new Trade(
                        "TRD101",
                        "INFY",
                        new BigDecimal("450"),
                        100,
                        TradeStatus.MATCHED
                ),

                new Trade(
                        "TRD102",
                        "TCS",
                        new BigDecimal("700"),
                        200,
                        TradeStatus.PENDING
                ),

                new Trade(
                        "TRD103",
                        "WIPRO",
                        new BigDecimal("900"),
                        300,
                        TradeStatus.MATCHED
                ),

                new Trade(
                        "TRD104",
                        "HCL",
                        new BigDecimal("300"),
                        150,
                        TradeStatus.FAILED
                )

        );

        // ---------------------------------------------------
        // Example 1 : filter()
        // ---------------------------------------------------

        System.out.println("Matched Trades");

        List<Trade> matchedTrades = trades.stream()
                .filter(t -> t.status() == TradeStatus.MATCHED)
                .toList();

        matchedTrades.forEach(System.out::println);

        // ---------------------------------------------------
        // Example 2 : map()
        // ---------------------------------------------------

        System.out.println("\nTrade References");

        List<String> tradeRefs = trades.stream()
                .map(Trade::tradeRef)
                .toList();

        tradeRefs.forEach(System.out::println);

        // ---------------------------------------------------
        // Example 3 : filter() + map()
        // ---------------------------------------------------

        System.out.println("\nMatched Trade References");

        List<String> matchedRefs = trades.stream()
                .filter(t -> t.status() == TradeStatus.MATCHED)
                .map(Trade::tradeRef)
                .toList();

        matchedRefs.forEach(System.out::println);

        // ---------------------------------------------------
        // Example 4 : Expensive Trades
        // ---------------------------------------------------

        System.out.println("\nPrice > 500");

        List<Trade> expensiveTrades = trades.stream()
                .filter(t -> t.price().compareTo(new BigDecimal("500")) > 0)
                .toList();

        expensiveTrades.forEach(System.out::println);

        // ---------------------------------------------------
        // Example 5 : count()
        // ---------------------------------------------------

        long count = trades.stream()
                .filter(t -> t.status() == TradeStatus.MATCHED)
                .count();

        System.out.println("\nMatched Trade Count : " + count);

        // ---------------------------------------------------
        // Example 6 : sorted()
        // ---------------------------------------------------

        System.out.println("\nSorted By Price");

        trades.stream()
                .sorted((t1, t2) -> t1.price().compareTo(t2.price()))
                .forEach(System.out::println);

        // ---------------------------------------------------
        // Example 7 : Collectors.toList()
        // ---------------------------------------------------

        List<Trade> matchedTradeList = trades.stream()
                .filter(t -> t.status() == TradeStatus.MATCHED)
                .collect(Collectors.toList());

        System.out.println("\nCollectors.toList()");
        matchedTradeList.forEach(System.out::println);

        // ---------------------------------------------------
        // Example 8 : Collectors.toMap()
        // ---------------------------------------------------

        Map<String, Trade> tradeMap = trades.stream()
                .collect(Collectors.toMap(
                        Trade::tradeRef,
                        Function.identity()
                ));

        System.out.println("\nSearch Trade TRD103");

        System.out.println(tradeMap.get("TRD103"));

    }
}


Easy Way to Remember

Collection

↓
stream()
↓
filter()      → Select required data
↓
map()         → Transform data
↓
sorted()      → Sort data
↓
toList() / collect()
↓

Final Result


Understanding parallelStream()

Normal Stream

trades.stream()

Processes One element at a time.

Parallel Stream

trades.parallelStream()

Splits work across Multiple CPU Cores.

Suitable for Large independent tasks.

Real Banking Example

Suppose
ReconX reconciles

500000 trades.

Sequential
CPU Core 1
↓
500000 Trades

Parallel

Core 1  125000
Core 2  125000
Core 3  125000
Core 4  125000

Total processing time reduces significantly.

When Should We Use parallelStream()?

Good Choice
• Read-only operations.
• Large collections.
• Independent calculations.
• CPU-intensive work.

Avoid

• Shared mutable variables.
• Database updates inside streams.
• Small collections.

ReconX Trade Reconciliation (Simple Version)
What is Reconciliation?

Reconciliation means comparing two systems.

Suppose a bank receives trades from two systems.

Internal System
TRD101
TRD102
TRD103
External System
TRD101
TRD102
TRD104

The bank wants to know which trades match.

Expected Output
TRD101 -> MATCH

TRD102 -> MATCH

TRD103 -> BREAK
Step 1 : Trade Class
public record Trade(String tradeRef) {
}
Step 2 : Main Class
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        // Internal Trades
        List<Trade> internal = List.of(
                new Trade("TRD101"),
                new Trade("TRD102"),
                new Trade("TRD103")
        );

        // External Trades
        List<Trade> external = List.of(
                new Trade("TRD101"),
                new Trade("TRD102"),
                new Trade("TRD104")
        );

        // Convert External List to Map
        Map<String, Trade> externalMap = external.stream()
                .collect(Collectors.toMap(
                        Trade::tradeRef,
                        trade -> trade
                ));

        // Compare Trades
        internal.stream()
                .forEach(trade -> {

                    if (externalMap.containsKey(trade.tradeRef())) {
                        System.out.println(trade.tradeRef() + " -> MATCH");
                    } else {
                        System.out.println(trade.tradeRef() + " -> BREAK");
                    }

                });

    }
}
Output
TRD101 -> MATCH
TRD102 -> MATCH
TRD103 -> BREAK
How the Program Works
Step 1

Create two lists.

Internal Trades

TRD101
TRD102
TRD103
External Trades

TRD101
TRD102
TRD104
Step 2

Convert the External List into a Map.

Map<String, Trade> externalMap = external.stream()
        .collect(Collectors.toMap(
                Trade::tradeRef,
                trade -> trade
        ));

The Map looks like this:

TRD101 → Trade
TRD102 → Trade
TRD104 → Trade

Step 3

Process each Internal Trade.

internal.stream()

One by one,

TRD101

↓

TRD102

↓

TRD103
Step 4

Check whether the trade exists in the Map.

externalMap.containsKey(trade.tradeRef())

If it exists

MATCH

Otherwise

BREAK
Flow Diagram
Internal Trades

TRD101
TRD102
TRD103

        │

        ▼

External Map

TRD101 ✓

TRD102 ✓

TRD104 ✓

        │

        ▼

TRD101 → MATCH

TRD102 → MATCH

TRD103 → BREAK
Why Convert List to Map?

Without a Map:

TRD101

↓

Search whole External List

With a Map:

TRD101

↓

Direct Lookup

Searching becomes much faster.

Easy Way to Remember
External List
      │
      ▼
Create Map
      │
      ▼
Process Internal Trades
      │
      ▼
Found?
   │
 ┌─┴─┐
 │   │
Yes  No
 │    │
MATCH BREAK
One-Line Summary

ReconX first converts External Trades into a Map, then checks every Internal Trade in that Map. If found, it is MATCH; otherwise, it is BREAK.




Transition to Next Section

The next section introduces Lambda Expressions and Functional Interfaces. We'll learn how behavior can be passed as data, how interfaces such as Predicate, Function, Consumer, and Supplier work, and how the ReconX engine delegates matching logic to interchangeable reconciliation rules



---------------------------------------------------------------------------------------------

DAY 3 – FUNCTIONAL JAVA & THE RECON ENGINE
MODULE 1 – FUNCTIONAL JAVA
SECTION 3 – Collectors, Grouping & VWAP

(Based on Slides 15–19)

Learning Objective

After completing this section, participants will be able to:

• Understand Collectors.
• Group data using groupingBy().
• Partition data using partitioningBy().
• Calculate statistical values.
• Understand downstream collectors.
• Calculate VWAP (Volume Weighted Average Price).
• Apply aggregation in enterprise trading applications.


Till now, we learned

Streams
↓
Filter
↓
Map
↓


What is a Collector?
A Collector collects the result produced by a Stream.
Think of a Stream as water flowing through a pipe.
At the end of the pipe, we need a bucket to store the water.
That bucket is called a Collector.



List
    │
    ▼
Stream Processing
    │
    ▼
Collector
    │
    ▼
Final Result


Why Do We Need Collectors?

Suppose the business asks:

Show all Trade References.
Group trades by Currency.
Count trades for each Currency.
Calculate total Quantity.
Separate Matched and Break Trades.

Collectors perform all these tasks easily.

Trade Class

We'll use this class for every example.

public record Trade(

        String tradeRef,
        String currency,
        int quantity,
        double price,
        boolean matched

) {
}

Sample Data
List<Trade> trades = List.of(

        new Trade("TRD101","INR",100,120.5,true),
        new Trade("TRD102","USD",200,210.0,false),
        new Trade("TRD103","INR",300,150.0,true),
        new Trade("TRD104","EUR",400,170.0,false),
        new Trade("TRD105","USD",250,190.0,true)

);

Collecting into List

Suppose Business wants only Trade References.

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<Trade> trades = List.of(
                new Trade("TRD101","INR",100,120.5,true),
                new Trade("TRD102","USD",200,210.0,false),
                new Trade("TRD103","INR",300,150.0,true)
        );

        List<String> tradeRefs = trades.stream()
                .map(Trade::tradeRef)
                .collect(Collectors.toList());

        System.out.println(tradeRefs);

    }
}
Output
[TRD101, TRD102, TRD103]

groupingBy()
Business Requirement

Show Trades grouped by Currency.

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<Trade> trades = List.of(
                new Trade("TRD101","INR",100,120.5,true),
                new Trade("TRD102","USD",200,210.0,false),
                new Trade("TRD103","INR",300,150.0,true),
                new Trade("TRD104","USD",400,170.0,true)
        );

        Map<String,List<Trade>> result = trades.stream()
                .collect(Collectors.groupingBy(Trade::currency));

        result.forEach((currency,list) ->
                System.out.println(currency + " -> " + list));

    }
}
Output
INR -> [TRD101, TRD103]

USD -> [TRD102, TRD104]




groupingBy() with counting()
Business Requirement

How many trades exist per Currency?

import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        Map<String,Long> count = trades.stream()
                .collect(Collectors.groupingBy(
                        Trade::currency,
                        Collectors.counting()
                ));

        System.out.println(count);

    }
}
Output
{INR=2, USD=2, EUR=1}


groupingBy() with summingInt()
Business Requirement

Calculate total Quantity for every Currency.

import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        Map<String,Integer> quantity = trades.stream()
                .collect(Collectors.groupingBy(
                        Trade::currency,
                        Collectors.summingInt(Trade::quantity)
                ));

        System.out.println(quantity);

    }
}
Output
{INR=400, USD=450, EUR=400}

partitioningBy()
Business Requirement

Separate Matched and Break Trades.

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        Map<Boolean,List<Trade>> result = trades.stream()
                .collect(Collectors.partitioningBy(
                        Trade::matched
                ));

        System.out.println("Matched");
        System.out.println(result.get(true));

        System.out.println();

        System.out.println("Break");
        System.out.println(result.get(false));

    }
}
Output
Matched

TRD101
TRD103
TRD105

Break

TRD102
TRD104

groupingBy() vs partitioningBy()

| groupingBy()      | partitioningBy()         |
| ----------------- | ------------------------ |
| Multiple groups   | Only two groups          |
| Any key           | Boolean key              |
| Example: Currency | Example: Matched / Break |


Statistics using summarizingDouble()
Business Requirement

Calculate trade price statistics.

import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        DoubleSummaryStatistics stats = trades.stream()
                .collect(Collectors.summarizingDouble(
                        Trade::price
                ));

        System.out.println("Count : " + stats.getCount());
        System.out.println("Sum   : " + stats.getSum());
        System.out.println("Average : " + stats.getAverage());
        System.out.println("Maximum : " + stats.getMax());
        System.out.println("Minimum : " + stats.getMin());

    }
}
Output
Count : 5

Sum : 840.5

Average : 168.1

Maximum : 210.0

Minimum : 120.5

How ReconX Uses Collectors

Trade List
      │
      ▼
Stream
      │
      ▼
Collectors
      │
      ├── toList()
      ├── groupingBy()
      ├── counting()
      ├── summingInt()
      ├── partitioningBy()
      └── summarizingDouble()
      │
      ▼

Dashboard Reports




What is VWAP? 
VWAP stands for Volume Weighted Average Price

It is one of the most commonly used calculations in the stock market and banking systems.
Instead of calculating a simple average, VWAP gives more importance to trades with higher quantities.
Why Not Use Simple Average?
Suppose a bank executed only two trades.

| Trade  | Price | Quantity |
| ------ | ----: | -------: |
| TRD101 |   100 |       10 |
| TRD102 |   200 |      100 |


Simple Average

(100 + 200) / 2

= 150


According to this,
Average Price = 150
But is this correct?
No.
Because the second trade has 100 shares, while the first trade has only 10 shares.
Both trades should not have equal importance.


What Should We Do?
The trade with the higher quantity should contribute more to the average.

That's why banks use VWAP.

VWAP Formula (Volume Weighted Average Price)

VWAP = Total Trade Value
       -----------------
       Total Quantity

where

Trade Value = Price × Quantity
Manual Calculation

Suppose today's trades are

| Trade  | Price | Quantity |
| ------ | ----: | -------: |
| TRD101 |   100 |       10 |
| TRD102 |   200 |      100 |


Step 1

Calculate Trade Value
TRD101
100 × 10  =  1000

TRD102
200 × 20  = 4000

Step 2

Total Trade Value
1000 + 4000=    5000

Step 3
Total Quantity
10 + 20 =   30

Step 4

Calculate VWAP
5000 / 30=166.67

Final Result
Simple Average = 150

VWAP = 166.67

VWAP is more accurate because larger trades have a greater impact.

Java Working Example

Trade Class
public record Trade(
        String tradeRef,
        double price,
        int quantity
) {
}

Main Class
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Trade> trades = List.of(
                new Trade("TRD101",100,10),
                new Trade("TRD102",200,20)
        );

        // Total Trade Value
        double totalValue = trades.stream()
                .mapToDouble(trade ->
                        trade.price() * trade.quantity())
                .sum();

        // Total Quantity
        int totalQuantity = trades.stream()
                .mapToInt(Trade::quantity)
                .sum();

        // VWAP
        double vwap = totalValue / totalQuantity;

        System.out.println("Total Value : " + totalValue);
        System.out.println("Total Quantity : " + totalQuantity);
        System.out.println("VWAP : " + vwap);
    }
}

Output
Total Value : 5000.0
Total Quantity : 30

VWAP : 166.66666666666666



How ReconX Uses VWAP

Suppose two trading systems send the following summary.

Internal System
VWAP = 166.66

External System
VWAP = 166.67

Difference

0.01

If the difference is within the allowed tolerance,

MATCH

Otherwise,

BREAK



Transition to Next Section

The next section introduces CompletableFuture and Asynchronous Programming. We'll learn how enterprise applications execute multiple independent tasks concurrently, combine results, avoid blocking threads, and improve reconciliation throughput by processing trades asynchronously using Java's CompletableFuture API.

DAY 3 – FUNCTIONAL JAVA & THE RECON ENGINE
MODULE 1 – FUNCTIONAL JAVA
SECTION 4 – CompletableFuture & Asynchronous Programming

(Based on Slides 20–24)

Learning Objective



CompletableFuture (Asynchronous Programming)
Learning Objective

After completing this section, students will be able to:

Understand synchronous and asynchronous programming.
Learn why CompletableFuture was introduced.
Execute tasks in the background.
Run multiple tasks simultaneously.
Wait for all tasks to finish.
Handle exceptions.
Understand how enterprise banking applications use CompletableFuture.
What is Synchronous Programming?

In Synchronous Programming, one task starts only after the previous task finishes.
Example
Suppose a bank application performs three tasks.

Read Internal Trades
↓
Read External Trades
↓
Read Exchange Rates

Each task takes 2 seconds.

Internal Trades      2 sec
↓
External Trades      2 sec
↓
Exchange Rates       2 sec
↓

Total Time = 6 Seconds

Every task waits for the previous one.
Real Banking Example

ReconX performs

Read Internal Trades
Read External Trades
Read Exchange Rates

If they execute one after another,
the reconciliation starts only after all three finish.
This makes the application slow.

What is Asynchronous Programming?
In Asynchronous Programming, multiple independent tasks run at the same time.

Internal Trades
      ↘
External Trades
      ↗
Exchange Rates


All three start together.

Time Comparison
Synchronous
Task 1 (2 sec)
↓
Task 2 (2 sec)
↓
Task 3 (2 sec)
↓

Total = 6 Seconds

Asynchronous
Task 1
↘
Task 2
↗
Task 3
↓
All run together
↓

Total ≈ 2 Seconds

This is much faster.

Why CompletableFuture?

Before Java 8, developers used

Thread
Runnable vs Callable
Future

Managing these classes required more code and was harder to combine tasks.
Java 8 introduced CompletableFuture to make asynchronous programming simpler.

What is CompletableFuture?
A CompletableFuture represents a result that will be available later.
Think of ordering food online.
Order Food
↓
Restaurant Starts Cooking
↓
You continue watching TV
↓
Food Arrives
↓
Eat

You don't stand in the kitchen waiting.

Similarly,

your program starts a background task and continues doing other work.
First Working Example
Suppose loading trades takes 5 seconds.

import java.util.concurrent.CompletableFuture;
public class Main {
    public static void main(String[] args) {
        System.out.println("Application Started");
        CompletableFuture<String> future =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                    return "Trades Loaded";
                });
        System.out.println("Application Continues...");
        System.out.println(future.join());
    }
}

Output

Application Started
Application Continues...
(wait 5 seconds)
Trades Loaded

Understanding the Program

Step 1
CompletableFuture.supplyAsync(...)
starts a background task.

Step 2
Thread.sleep(5000);
simulates loading trades.

Step 3
Main thread continues immediately.
Application Started

↓
Background Task Starts

↓

Application Continues

The program does not wait.

Step 4
future.join();
waits for the background task to finish.
What does join() do?
join() waits until the task completes and then returns the result.
String result = future.join();
If the result is ready,
it returns immediately.
Otherwise,
it waits. Running Multiple Tasks

Suppose ReconX loads

Internal Trades
External Trades
Exchange Rates

Each task takes 2 seconds.

Working Example

import java.util.concurrent.CompletableFuture;
public class Main {
    public static void main(String[] args) {
        CompletableFuture<String> internal =
                CompletableFuture.supplyAsync(() -> {
                    sleep();
                    return "Internal Trades";
                });

        CompletableFuture<String> external =
                CompletableFuture.supplyAsync(() -> {
                    sleep();
                    return "External Trades";
                });

        CompletableFuture<String> rates =
                CompletableFuture.supplyAsync(() -> {
                    sleep();
                    return "Exchange Rates";
                });

        System.out.println(internal.join());
        System.out.println(external.join());
        System.out.println(rates.join());
    }

    static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }
}

Output
Internal Trades
External Trades
Exchange Rates

Although each task takes 2 seconds, all three finish in approximately 2 seconds, not 6 seconds.

Waiting for All Tasks

Java provides

CompletableFuture.allOf()

CompletableFuture.allOf(
        internal,
        external,
        rates
).join();

Meaning

Wait until
Internal
External

Exchange Rates all finish.

Only then continue.

Complete Example

CompletableFuture.allOf(
        internal,
        external,
        rates
).join();

System.out.println("All Tasks Completed");

Output

All Tasks Completed
Exception Handling

Suppose database connection fails.
Without handling, the application crashes.
Using
exceptionally()

we can provide a fallback.

CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Database Error");
        }).exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return "No Trades";
        });

System.out.println(future.join());
Output
Database Error
No Trades
The application continues running.
thenApply()

Suppose trades are loaded.
Now convert them into uppercase.

CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> "internal trades")
                .thenApply(String::toUpperCase);
System.out.println(future.join());

Output
INTERNAL TRADES

Meaning
Load Data
↓
Modify Data
↓
Return Result
thenAccept()

Suppose we only want to print the result.
CompletableFuture.supplyAsync(() -> "Trades Loaded")
        .thenAccept(System.out::println)
        .join();

Output
Trades Loaded
No value is returned.
thenCombine()
Suppose two tasks run independently.

CompletableFuture<Integer> a =
        CompletableFuture.supplyAsync(() -> 10);

CompletableFuture<Integer> b =
        CompletableFuture.supplyAsync(() -> 20);

CompletableFuture<Integer> result =
        a.thenCombine(b, Integer::sum);

System.out.println(result.join());

Output

30

Meaning

Task A
↓
10
----------------
Task B
↓
20
----------------
Combine
↓
30

Complete ReconX Flow

Application Starts
        │
        ▼
Load Internal Trades
        │
        ▼
Load External Trades
        │
        ▼
Load Exchange Rates
        │
        ▼
CompletableFuture.allOf()
        │
        ▼
join()
        │
        ▼
Reconciliation Engine
        │
        ▼

Dashboard

Where is CompletableFuture Used?

Enterprise applications use it for

Database Queries
REST API Calls
Microservices
Kafka Consumers
Email Sending
File Processing
Report Generation
Parallel Banking Calculations

ExecutorService vs CompletableFuture


| ExecutorService            | CompletableFuture                                                 |
| -------------------------- | ----------------------------------------------------------------- |
| Executes background tasks  | Executes background tasks and processes results                   |
| Manual Future handling     | Fluent API                                                        |
| More boilerplate code      | Less code                                                         |
| Difficult task composition | Easy chaining with `thenApply()`, `thenAccept()`, `thenCombine()` |
| Harder exception handling  | Built-in `exceptionally()`                                        |



One-Line Summary

CompletableFuture enables applications to execute independent tasks in parallel, improving performance and making asynchronous programming simple, readable, and ideal for enterprise applications like ReconX.


Transition to Next Section

The next section covers Optional, Null Safety, and Modern Java Best Practices. We'll learn how Optional eliminates NullPointerException, how to use methods such as of(), ofNullable(), orElse(), orElseGet(), orElseThrow(), and ifPresent(), and how ReconX uses Optional to safely retrieve trades and avoid null-related bugs.

DAY 3 – FUNCTIONAL JAVA & THE RECON ENGINE
MODULE 1 – FUNCTIONAL JAVA
SECTION 5 – Optional & Null Safety

(Based on Slides 21–22 / Ticket IH039)

Learning Objective

After completing this section, participants will be able to:

• Understand why NullPointerException occurs.
• Learn the purpose of Optional.
• Use Optional safely
• Understand of(), ofNullable(), and empty().
• Learn map(), flatMap(), filter(), orElse(), orElseGet(), and orElseThrow().
• Apply Optional in Spring Boot and ReconX.


Optional (Avoiding NullPointerException)
Learning Objective

After completing this section, students will be able to:

Understand why NullPointerException occurs.
Learn why Java 8 introduced Optional.
Create Optional objects.
Safely retrieve values.
Provide default values.
Throw custom exceptions when data is missing.
Transform Optional values.
Use Optional in enterprise banking applications.
Why was Optional Introduced?

One of the most common exceptions in Java is

NullPointerException (NPE)

It happens when we try to use an object that does not exist.

Example Without Optional
public class Main {

    public static void main(String[] args) {

        String tradeRef = null;

        System.out.println(tradeRef.length());

    }

}
Output
Exception in thread "main"

java.lang.NullPointerException

The application crashes.

Real Banking Example

Suppose a client searches for

TRD9999

Database

No Record Found

Old code

Trade trade = repository.findTrade("TRD9999");

System.out.println(trade.tradeRef());

Since trade is null, the application crashes.

What is Optional?

Optional is a container that may contain a value or may be empty.

Think of it as a gift box.

Optional

┌─────────────┐

│   Trade     │

└─────────────┘

or

Optional

┌─────────────┐

│   Empty     │

└─────────────┘

Instead of returning null, methods return an Optional.

Creating Optional

Java provides three factory methods.

1. Optional.of()

Use it only when the object definitely exists.

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Optional<String> trade =
                Optional.of("TRD101");

        System.out.println(trade);

    }

}
Output
Optional[TRD101]
Wrong Usage
Optional<String> trade =
        Optional.of(null);

Output

NullPointerException

Optional.of() does not allow null.

2. Optional.ofNullable()

This is the most commonly used method.

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        String trade = null;

        Optional<String> optional =
                Optional.ofNullable(trade);

        System.out.println(optional);

    }

}
Output
Optional.empty

If the value exists

Optional[TRD101]

If it is missing

Optional.empty

No exception.

3. Optional.empty()

Represents no value.

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Optional<String> trade =
                Optional.empty();

        System.out.println(trade);

    }

}
Output
Optional.empty
Checking a Value
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Optional<String> trade =
                Optional.of("TRD101");

        if (trade.isPresent()) {

            System.out.println(trade.get());

        }

    }

}

Output

TRD101
Why is get() Dangerous?
Optional<String> trade =
        Optional.empty();

System.out.println(trade.get());

Output

NoSuchElementException

So avoid get() in real projects.

Using orElse()

Suppose the trade is missing.

Return a default value.

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Optional<String> trade =
                Optional.empty();

        String result =
                trade.orElse("UNKNOWN TRADE");

        System.out.println(result);

    }

}
Output
UNKNOWN TRADE
Real Banking Example

Client searches

TRD9999

Database

No Record

Instead of

null

Return

UNKNOWN TRADE

The application continues safely.

Using orElseGet()

Suppose creating the default object is expensive.

import java.util.Optional;

public class Main {

    static String createTrade() {

        System.out.println("Creating Default Trade...");

        return "DEFAULT TRADE";

    }

    public static void main(String[] args) {

        Optional<String> trade =
                Optional.empty();

        String result =
                trade.orElseGet(Main::createTrade);

        System.out.println(result);

    }

}
Output
Creating Default Trade...

DEFAULT TRADE

createTrade() runs only if the Optional is empty.

orElse() vs orElseGet()

| orElse()                             | orElseGet()                               |
| ------------------------------------ | ----------------------------------------- |
| Default value is created immediately | Default value is created only when needed |
| Best for simple values               | Best for expensive object creation        |



Sometimes missing data is an error.

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Optional<String> trade =
                Optional.empty();

        String result =
                trade.orElseThrow(() ->
                        new RuntimeException("Trade Not Found"));

        System.out.println(result);

    }

}
Output
Exception in thread "main"

Trade Not Found

This is the preferred enterprise approach.

Using ifPresent()

Execute code only if the value exists.

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Optional<String> trade =
                Optional.of("TRD101");

        trade.ifPresent(System.out::println);

    }

}
Output
TRD101

No if(trade != null) required.

Using map()

Transform the value.

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Optional<String> trade =
                Optional.of("trd101");

        Optional<String> result =
                trade.map(String::toUpperCase);

        System.out.println(result);

    }

}
Output
Optional[TRD101]
Using filter()

Keep the value only if it satisfies a condition.

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Optional<Integer> amount =
                Optional.of(1500000);

        Optional<Integer> result =
                amount.filter(a -> a > 1000000);

        System.out.println(result);

    }

}
Output
Optional[1500000]

If the amount were 500000, the output would be:

Optional.empty
Optional Chaining

One of the most useful patterns.

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Optional<String> trade =
                Optional.of("TRD101");

        String result = trade
                .map(String::toLowerCase)
                .orElse("UNKNOWN");

        System.out.println(result);

    }

}
Output
trd101

Flow

Optional

↓

map()

↓

orElse()

↓

Final Value
Real ReconX Example
Optional<Trade> trade =
        repository.findByTradeRef("TRD101");

trade.ifPresent(this::processTrade);

If the trade exists:

Process Trade

If the trade is missing:

Nothing Happens

No NullPointerException.

Without Optional
Trade trade = repository.find();

if (trade != null) {

    processTrade(trade);

}
With Optional
repository.find()
          .ifPresent(this::processTrade);

Cleaner and safer.

Where is Optional Used?

Enterprise applications use Optional in:

Spring Boot Repository methods
Database queries
REST API responses
Microservices
Banking applications
Search operations
User profile lookup
Product lookup
Trade lookup

Optional vs Null


| Null                             | Optional                           |
| -------------------------------- | ---------------------------------- |
| Can cause `NullPointerException` | Helps avoid `NullPointerException` |
| Manual null checks               | Built-in methods                   |
| Less expressive                  | More readable                      |
| Error-prone                      | Safer approach                     |




Recommended

• Repository return values
• Service lookups
• Cache lookups
• Search operations
• Configuration retrieval


DAY 3 – FUNCTIONAL JAVA & THE RECON ENGINE
MODULE 2 – TESTING THE ENGINE
SECTION 1 – Test Pyramid, TDD, JUnit 5 & AssertJ

(Based on Slides 23–25)

Learning Objective

After completing this section, participants will be able to:


Learning Objective

After completing this section, participants will be able to:

Understand why software testing is important.
Learn the Test Pyramid.
Understand Test Driven Development (TDD).
Write Unit Tests using JUnit 5.
Write readable assertions using AssertJ.
Follow the Arrange – Act – Assert (AAA) pattern.
Why Do We Need Testing?

Suppose you wrote a Java program.

It compiles successfully.

Question

Does that mean the program is correct?

No.

Compilation only checks

Syntax Errors
Missing Semicolons
Missing Brackets
Compilation Errors

It does not check business logic.

Example

Suppose the bank calculates Trade Notional.

Formula

Price × Quantity

Input

Price = 100

Quantity = 10

Expected

1000

But your program returns

900

The program compiles successfully.

But the business result is wrong.

Testing finds this problem.

Real Banking Example

ReconX compares

Internal Trade

↓

External Trade

↓

MATCH

Tomorrow a developer changes the matching logic.

Now the same trade becomes

BREAK

Compilation succeeds.

Without testing,

nobody notices.

Customers report the issue later.

With testing,

the build fails immediately.

What is Software Testing?

Software Testing means

Checking whether the software behaves exactly as expected.

Testing verifies

Correct Output
Correct Business Logic
Correct Exceptions
Correct Integration
Correct Performance

The goal is

Confidence before deployment
Types of Testing

There are three main types.

Unit Testing

↓

Integration Testing

↓

End-to-End Testing
1. Unit Testing

Unit Testing checks

only one class

or

one method.

Example

TradeService

Test only

calculateNotional()

Do not connect

Database
REST API
Kafka
UI

Unit tests are

Fast
Easy
Cheap
2. Integration Testing

Checks whether multiple components work together.

Example

TradeService

↓

TradeRepository

↓

PostgreSQL

Now we are testing interaction.

3. End-to-End Testing

Tests the complete application.

React UI

↓

REST API

↓

Spring Boot

↓

Database

↓

Response

Everything works together.

Test Pyramid
        End-to-End

       Few Tests

-------------------------

    Integration Tests

      Moderate Tests

-------------------------

       Unit Tests

      Thousands
Why More Unit Tests?

Because they are

Fast
Reliable
Easy to maintain
No database required

Enterprise projects usually contain

5000+

Unit Tests

200

Integration Tests

20

End-to-End Tests
What is TDD?

TDD means

Test Driven Development

Instead of writing code first,

we write the test first.

TDD Cycle
RED

↓

Write Test

↓

Test Fails

↓

GREEN

↓

Write Code

↓

Test Passes

↓

REFACTOR

↓

Improve Code

Repeat.

Traditional Development
Write Code

↓

Run Program

↓

Find Bug
TDD
Write Test

↓

Fail

↓

Write Code

↓

Pass

↓

Improve

Every feature has a test.

What is JUnit 5?

JUnit 5 is the standard Java testing framework.

It helps us

Execute Tests
Verify Output
Check Exceptions
Run Multiple Tests

Almost every Spring Boot project uses JUnit 5.

First JUnit Example
Calculator.java
public class Calculator {

    public int add(int a, int b) {

        return a + b;

    }

}
CalculatorTest.java
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testAddition() {

        Calculator calculator = new Calculator();

        int result = calculator.add(10,20);

        assertEquals(30,result);

    }

}
Output
Test Passed

JUnit automatically runs the test.

Understanding @Test
@Test
void testAddition(){

}

means

This method is a Test Case.

JUnit automatically executes it.

Arrange – Act – Assert (AAA)

Every Unit Test follows

Arrange

↓

Act

↓

Assert
Example
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testAddition() {

        // Arrange
        Calculator calculator = new Calculator();

        // Act
        int result = calculator.add(10,20);

        // Assert
        assertEquals(30,result);

    }

}
Understanding AAA
Arrange

Prepare data.

Calculator calculator = new Calculator();
Act

Call the method.

calculator.add(10,20);
Assert

Verify the result.

assertEquals(30,result);
Real Banking Example
Trade.java
public class Trade {

    public double calculateNotional(double price,int quantity){

        return price * quantity;

    }

}
TradeTest.java
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeTest {

    @Test
    void shouldCalculateNotional(){

        // Arrange
        Trade trade = new Trade();

        // Act
        double result =
                trade.calculateNotional(100,10);

        // Assert
        assertEquals(1000,result);

    }

}
Output
Test Passed
Why AssertJ?

JUnit provides

assertEquals(expected,actual);

AssertJ provides

assertThat(actual).isEqualTo(expected);

It reads like English.

Much easier.

AssertJ Example
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CalculatorTest {

    @Test
    void testAddition(){

        Calculator calculator = new Calculator();

        int result =
                calculator.add(10,20);

        assertThat(result)
                .isEqualTo(30);

    }

}
Common AssertJ Methods
Equality
assertThat(result)
        .isEqualTo(100);
Boolean
assertThat(flag)
        .isTrue();
String
assertThat(name)
        .startsWith("TRD");
Collection
assertThat(list)
        .hasSize(5);
Null
assertThat(object)
        .isNull();
Testing Exceptions

Suppose division by zero occurs.

Calculator.java
public class Calculator {

    public int divide(int a,int b){

        return a/b;

    }

}
CalculatorTest.java
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CalculatorTest {

    @Test
    void shouldThrowException(){

        Calculator calculator =
                new Calculator();

        assertThatThrownBy(() ->
                calculator.divide(10,0))
                .isInstanceOf(
                        ArithmeticException.class);

    }

}
Output
Test Passed

Because the expected exception occurred.

Real Banking Example

Suppose Trade Price is mandatory.

If the developer forgets Price,

the application should throw an exception.

Instead of creating an invalid Trade,

the test verifies that the exception is thrown.

Complete Testing Flow
Developer

↓

Write Test

↓

Run Test

↓

Fail

↓

Write Code

↓

Pass

↓

Refactor

↓

Commit Code

↓

CI/CD Pipeline

↓

Production

JUnit vs AssertJ

| JUnit            | AssertJ                      |
| ---------------- | ---------------------------- |
| Executes Tests   | Provides readable assertions |
| `@Test`          | `assertThat()`               |
| `assertEquals()` | `isEqualTo()`                |
| Basic Assertions | Rich Fluent API              |



One-Line Summary

Software testing verifies that business logic works correctly. JUnit 5 is used to execute tests, AssertJ provides readable assertions, and the Arrange–Act–Assert pattern helps write clean, maintainable unit tests.

Transition to Next Section

The next section covers Mockito, ArgumentCaptor, and Testcontainers. We'll learn how to isolate business logic using mocks, verify interactions between components, capture method arguments, and run integration tests against a real PostgreSQL database inside Docker using Testcontainers.

DAY 3 – FUNCTIONAL JAVA & THE RECON ENGINE
MODULE 2 – TESTING THE ENGINE
SECTION 2 – Mockito, Testcontainers & JaCoCo

(Based on Slides 26–30)

Learning Objective

After completing this section, participants will be able to:

Suppose we have the following application.

TradeController

        │

        ▼

TradeService

        │

        ▼

TradeRepository

        │

        ▼

PostgreSQL Database

Question

When testing TradeService,

should we connect to

PostgreSQL Database?
Kafka?
Email Server?

No.

We only want to test the business logic inside TradeService.

Everything else should be fake.

Mockito helps us create these fake objects.

Why Do We Need Mockito?

Suppose TradeService depends on

TradeService

      │

      ├── TradeRepository

      ├── EmailService

      ├── KafkaProducer

      └── AuditService

If we use real objects,

our Unit Test becomes

Slow
Difficult to configure
Dependent on external systems
Unstable

Instead,

Mockito creates fake versions called Mocks.

Real Banking Example

Suppose ReconX saves a trade.

repository.save(trade);

Question

Should the Unit Test actually save data into PostgreSQL?

No.

Mockito pretends to save the trade.

No database is required.

What is Mockito?

Mockito is a Java Mocking Framework.

It creates fake implementations of dependent objects.

Instead of

TradeService

↓

Real Database

we use

TradeService

↓

Mock Database

This keeps Unit Tests

Fast
Independent
Reliable
Project Structure
Trade

TradeRepository

TradeService

TradeServiceTest
Trade Class
public class Trade {

    private String tradeRef;

    public Trade(String tradeRef) {
        this.tradeRef = tradeRef;
    }

    public String getTradeRef() {
        return tradeRef;
    }

}
Repository Interface
import java.util.Optional;

public interface TradeRepository {

    Optional<Trade> findByRef(String ref);

    void save(Trade trade);

}
Service Class
import java.util.Optional;

public class TradeService {

    private TradeRepository repository;

    public TradeService(TradeRepository repository) {
        this.repository = repository;
    }

    public Optional<Trade> findTrade(String ref) {

        return repository.findByRef(ref);

    }

}
What is a Mock?

A Mock is a fake implementation of a real object.

Real Repository

↓

Reads PostgreSQL

------------------------

Mock Repository

↓

Returns Fake Data
Creating a Mock

Mockito provides

@Mock

Example

@Mock
private TradeRepository repository;

Meaning

Mockito creates a fake implementation of TradeRepository.

Complete Example
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    TradeRepository repository;

}

Mockito automatically creates the fake repository.

Understanding @Mock

Without Mockito

TradeRepository repository =
        new TradeRepository();

Impossible.

Because

TradeRepository

is an interface.

Mockito creates the implementation automatically.

@InjectMocks

Question

How does TradeService receive the mocked repository?

Mockito provides

@InjectMocks
Example
@Mock
TradeRepository repository;

@InjectMocks
TradeService service;

Mockito automatically does

service =
    new TradeService(repository);

No manual object creation.

Understanding @InjectMocks
Mock Repository

        │

        ▼

TradeService

(Mockito injects automatically)
Complete Example
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    TradeRepository repository;

    @InjectMocks
    TradeService service;

}
What is Stubbing?

Question

How should the Mock behave?

Mockito provides

when()

thenReturn()
Example

Suppose the repository should return one Trade.

Trade trade =
        new Trade("TRD101");

when(repository.findByRef("TRD101"))
        .thenReturn(Optional.of(trade));

Meaning

Whenever

repository.findByRef("TRD101")

is called,

return

Optional.of(trade)

No database call occurs.

Complete Working Example
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    TradeRepository repository;

    @InjectMocks
    TradeService service;

    @Test
    void shouldReturnTrade() {

        Trade trade =
                new Trade("TRD101");

        when(repository.findByRef("TRD101"))
                .thenReturn(Optional.of(trade));

        Optional<Trade> result =
                service.findTrade("TRD101");

        assertThat(result).isPresent();

        assertThat(result.get().getTradeRef())
                .isEqualTo("TRD101");

    }

}
Arrange – Act – Assert
Arrange
Trade trade =
        new Trade("TRD101");

when(repository.findByRef("TRD101"))
        .thenReturn(Optional.of(trade));

Prepare mock data.

Act
Optional<Trade> result =
        service.findTrade("TRD101");

Call the business method.

Assert
assertThat(result).isPresent();

assertThat(result.get().getTradeRef())
        .isEqualTo("TRD101");

Verify the result.

Flow Diagram
TradeService

        │

Calls

        ▼

Mock Repository

        │

Returns

        ▼

TRD101

(No Database)
Why is Stubbing Useful?

Suppose PostgreSQL is down.

Will the Unit Test fail?

No.

Mockito returns the expected result immediately.

The Unit Test stays fast and independent.

Easy Memory Trick
Need Fake Object?

↓

@Mock

↓

Need Service?

↓

@InjectMocks

↓

Need Fake Data?

↓

when()

↓

thenReturn()

↓

Run Test

↓

Verify Result
One-Line Summary

Mockito allows us to replace real dependencies such as databases, email services, and Kafka with mock objects so that we can test business logic quickly, independently, and reliably.

Advanced Testing
Part 3 – Mockito Verification
Learning Objective

After completing this section, participants will be able to:

Verify method calls using verify().
Verify how many times a method is called.
Use times(), never(), atLeast(), and atMost().
Capture method arguments using ArgumentCaptor.
Verify business workflows using Mockito.
Trainer Introduction

Suppose our application saves a Trade.

TradeController

      │

      ▼

TradeService

      │

      ▼

TradeRepository

      │

      ▼

PostgreSQL

Question

How do we know

that

repository.save(trade)

was actually called?

Checking only the returned value is not enough.

We must also verify the interaction between objects.

Mockito provides

verify()

for this purpose.

Why Do We Need verify()?

Suppose the business requirement says

Every successful trade must be saved into the database.

Question

How do we test that?

Mockito allows us to verify that the repository method was called.

Project Classes
Trade.java
public class Trade {

    private String tradeRef;

    public Trade(String tradeRef) {
        this.tradeRef = tradeRef;
    }

    public String getTradeRef() {
        return tradeRef;
    }

}
TradeRepository.java
public interface TradeRepository {

    void save(Trade trade);

}
TradeService.java
public class TradeService {

    private TradeRepository repository;

    public TradeService(TradeRepository repository) {
        this.repository = repository;
    }

    public void saveTrade(Trade trade) {

        repository.save(trade);

    }

}
First verify() Example
TradeServiceTest.java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    TradeRepository repository;

    @InjectMocks
    TradeService service;

    @Test
    void shouldSaveTrade() {

        Trade trade =
                new Trade("TRD101");

        service.saveTrade(trade);

        verify(repository).save(trade);

    }

}
Output
Test Passed

Mockito confirms that

repository.save(trade)

was called.

Understanding verify()
Arrange
Trade trade =
        new Trade("TRD101");

Create test data.

Act
service.saveTrade(trade);

Execute the business method.

Assert
verify(repository)
        .save(trade);

Verify interaction.

Flow Diagram
TradeService

      │

      ▼

saveTrade()

      │

      ▼

Repository.save()

      │

      ▼

Mockito verifies
verify() with times()

Sometimes the business requires that a method be called exactly once.

Mockito provides

times()
Example
verify(repository,
        times(1))
        .save(trade);

Output

Passed

Meaning

Repository.save()

was called exactly one time.

verify() with times(2)

Suppose the service saves the trade twice.

public void saveTwice(Trade trade){

    repository.save(trade);

    repository.save(trade);

}

Test

service.saveTwice(trade);

verify(repository,
        times(2))
        .save(trade);

Output

Passed
verify() with never()

Question

How do we verify

that a method was never called?

Mockito provides

never()

Example

verify(repository,
        never())
        .save(trade);

Meaning

Repository.save()

must never execute.
Real Banking Example

Suppose

Trade Amount

is negative.

Business Rule

Do NOT save the Trade

Service

public void saveTrade(Trade trade){

    if(trade.getAmount()<0){

        return;

    }

    repository.save(trade);

}

Test

verify(repository,
        never())
        .save(any());

Output

Passed
verify() with atLeast()

Suppose logging should happen at least once.

verify(repository,
        atLeast(1))
        .save(trade);

Meaning

One

or

More Calls
verify() with atMost()

Suppose saving should not happen more than three times.

verify(repository,
        atMost(3))
        .save(trade);

Meaning

Maximum

3 Calls
Summary of Verification Methods
Method	Meaning
verify()	Verify one call
times(2)	Exactly 2 calls
never()	No calls
atLeast(1)	One or more calls
atMost(5)	Maximum 5 calls
Why Do We Need ArgumentCaptor?

Suppose TradeService changes the Trade

before saving.

Question

How do we verify

the modified object?

Mockito provides

ArgumentCaptor
Real Banking Example

Customer enters

Price = 100

Business Rule

Add 10% Tax

Final Price

110

Question

How do we verify

that Repository received

110

instead of

100?

ArgumentCaptor solves this problem.

Trade Class
public class Trade {

    private String tradeRef;

    private double price;

    public Trade(String tradeRef,double price){

        this.tradeRef=tradeRef;

        this.price=price;

    }

    public double getPrice(){

        return price;

    }

    public void setPrice(double price){

        this.price=price;

    }

}
Service
public class TradeService {

    private TradeRepository repository;

    public TradeService(TradeRepository repository){

        this.repository=repository;

    }

    public void saveTrade(Trade trade){

        trade.setPrice(
                trade.getPrice()*1.10);

        repository.save(trade);

    }

}
Using ArgumentCaptor
import org.mockito.ArgumentCaptor;

ArgumentCaptor<Trade> captor =
        ArgumentCaptor.forClass(
                Trade.class);

service.saveTrade(
        new Trade("TRD101",100));

verify(repository)
        .save(captor.capture());

Trade savedTrade =
        captor.getValue();

assertThat(savedTrade.getPrice())
        .isEqualTo(110);
Output
Passed

ArgumentCaptor captured the object that was passed to the repository.

Understanding ArgumentCaptor
Trade

Price =100

        │

        ▼

TradeService

Adds Tax

        │

        ▼

Repository.save()

        │

        ▼

ArgumentCaptor

Captures Object

        │

        ▼

Price =110
Complete Mockito Flow
Arrange

        │

        ▼

Create Mock

        │

        ▼

Stub Behavior

when().thenReturn()

        │

        ▼

Call Service

        │

        ▼

verify()

        │

        ▼

ArgumentCaptor

        │

        ▼

Assert Result
Enterprise Banking Example

Suppose ReconX processes a Trade.

Business Rules

Save Trade
Send Email
Publish Kafka Event
Write Audit Log

Mockito can verify every interaction.

verify(repository).save(trade);

verify(emailService).sendEmail(trade);

verify(kafkaProducer).publish(trade);

verify(auditService).log(trade);

This ensures the entire business workflow executed correctly.

Interview Questions
1. What is the purpose of verify()?

verify() checks whether a mocked method was invoked during the test.

2. What does times(2) mean?

It verifies that the method was called exactly two times.

3. What is the purpose of never()?

It verifies that a method was not called at all.

4. What is ArgumentCaptor?

ArgumentCaptor captures the actual object passed to a mocked method so that its values can be inspected and verified.

5. When should we use ArgumentCaptor?

Use it when the service modifies an object before passing it to another component, and you need to verify the final values.

Easy Memory Trick
Need to check

Method Call?

↓

verify()

↓

Exactly once?

↓

times(1)

↓

Never?

↓

never()

↓

Many times?

↓

atLeast()

↓

Maximum?

↓

atMost()

↓

Need Saved Object?

↓

ArgumentCaptor
One-Line Summary

Mockito's verify() confirms that expected business interactions occurred, while ArgumentCaptor allows us to inspect the actual objects passed between components, ensuring enterprise business workflows execute correctly.

This is where students move from Unit Testing (Mockito) to Integration Testing.

Mockito = Fake Database

Testcontainers = Real Database running inside Docker

This is one of the most important topics for Spring Boot developers.

DAY 4 – Advanced Testing
Part 4 – Testcontainers (Integration Testing)
Learning Objective

After completing this section, participants will be able to:

Understand what Integration Testing is.
Learn why Testcontainers was introduced.
Run a real PostgreSQL database using Docker.
Write Integration Tests using Testcontainers.
Understand the difference between Mockito and Testcontainers.
Apply Testcontainers in enterprise banking applications.
Trainer Introduction

Suppose our application looks like this.

TradeController

        │

        ▼

TradeService

        │

        ▼

TradeRepository

        │

        ▼

PostgreSQL Database

Earlier,

we tested

TradeService

using

Mockito.

Question

Did Mockito connect to PostgreSQL?

No.

Mockito used a Fake Repository.

Today,

we want to test

whether our Repository

actually works

with a real PostgreSQL database.

Why Do We Need Integration Testing?

Unit Tests verify

Business Logic

Integration Tests verify

Business Logic

        +

Database

        +

Spring Boot

        +

JPA

        +

SQL Queries

Now we are testing

the interaction

between components.

Traditional Integration Testing

Before Testcontainers,

developers had to

Install PostgreSQL

↓

Create Database

↓

Create User

↓

Configure Password

↓

Run Spring Boot

↓

Execute Tests

Problems

PostgreSQL version mismatch
Configuration differences
Works on one machine only
Difficult to maintain
Real Banking Example

Suppose ReconX uses PostgreSQL.

Developer A

uses

PostgreSQL 14

Developer B

uses

PostgreSQL 16

Developer C

forgot to install PostgreSQL.

Result

Tests fail

Configuration Problems

Different Results
What is Testcontainers?

Testcontainers is a Java library

that starts

real services

inside

Docker containers

for testing.

Instead of installing PostgreSQL manually,

Docker starts

a temporary PostgreSQL database.

How Testcontainers Work
Start Test

      │

      ▼

Docker Starts PostgreSQL

      │

      ▼

Spring Boot Connects

      │

      ▼

Execute Tests

      │

      ▼

Stop Container

Every test

gets

a fresh database.

Why Docker?

Question

Why not use our local PostgreSQL?

Because

every developer

has a different system.

Docker guarantees

everyone uses

the same PostgreSQL version.

First Testcontainers Example
Maven Dependency
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <version>1.20.4</version>
    <scope>test</scope>
</dependency>
Creating PostgreSQL Container
import org.testcontainers.containers.PostgreSQLContainer;

public class DatabaseContainer {

    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16");

}
Understanding the Code
new PostgreSQLContainer<>("postgres:16")

means

Start

PostgreSQL Version 16

inside Docker

Exactly as shown in the trainer presentation.

Complete Working Example
import org.junit.jupiter.api.Test;

import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

public class PostgreSQLContainerTest {

    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16");

    @Test
    void shouldStartContainer() {

        postgres.start();

        System.out.println(postgres.getJdbcUrl());

        assertThat(postgres.isRunning())
                .isTrue();

        postgres.stop();

    }

}
Output
Container Started

jdbc:postgresql://localhost:32768/test

Test Passed
What Happens Internally?
JUnit Starts

      │

      ▼

Docker Starts PostgreSQL

      │

      ▼

Database Ready

      │

      ▼

Spring Boot Connects

      │

      ▼

Run SQL

      │

      ▼

Verify Result

      │

      ▼

Docker Stops
Spring Boot Integration Example

Suppose we have

TradeRepository

Instead of mocking,

we test

the real repository.

Trade trade =
        new Trade("TRD101");

repository.save(trade);

Optional<Trade> result =
        repository.findById("TRD101");

assertThat(result).isPresent();

Now

Hibernate

JPA

SQL

PostgreSQL

are all tested together.

Real Banking Example

Suppose ReconX saves trades.

Flow

Trade

↓

TradeRepository.save()

↓

PostgreSQL

↓

TradeRepository.find()

↓

Verify Trade

If SQL is incorrect,

the Integration Test fails.

Testcontainers Lifecycle
Test Starts

↓

Create PostgreSQL Container

↓

Run Spring Boot

↓

Execute Test

↓

Destroy Container

Every test

starts

with

a clean database.

Mockito vs Testcontainers
Mockito	Testcontainers
Fake Repository	Real PostgreSQL
Unit Testing	Integration Testing
No Docker required	Docker required
Very Fast	Slower
Tests Business Logic	Tests Complete Database Integration
When Should We Use Mockito?

Use Mockito when

Testing Service Layer
Testing Business Logic
Database is not required
Fast feedback is needed

Example

TradeService

↓

Mock Repository
When Should We Use Testcontainers?

Use Testcontainers when

Testing Repository Layer
Testing SQL Queries
Testing JPA Mapping
Testing Spring Boot Configuration
Testing Database Integration

Example

TradeRepository

↓

Real PostgreSQL
Enterprise Banking Example

Suppose

ReconX imports

10,000 trades.

Integration Test

Start PostgreSQL

↓

Insert Trades

↓

Call Repository

↓

Retrieve Trades

↓

Verify Data

↓

Stop PostgreSQL

This confirms

everything works

exactly like production.

Common Mistakes
1. Docker Not Running

Output

Cannot connect to Docker

Solution

Start Docker Desktop.

2. Wrong PostgreSQL Image
new PostgreSQLContainer<>("postgres:16")

Always verify

the image name

and version.

3. Mixing Mockito and Testcontainers

Mockito

Fake Database

Testcontainers

Real Database

Choose the correct tool.

Complete Testing Pipeline
JUnit

↓

Mockito

↓

Business Logic Tested

↓

Testcontainers

↓

Real PostgreSQL Tested

↓

AssertJ

↓

Verify Results

↓

CI/CD Pipeline

↓

Production
Interview Questions
1. What is Testcontainers?

Testcontainers is a Java library that starts real services such as PostgreSQL inside Docker containers for integration testing.

2. Why do we use Testcontainers?

To perform reliable integration tests using real databases without manually installing or configuring them.

3. Does Testcontainers require Docker?

Yes.

Docker is required because Testcontainers runs databases and other services inside Docker containers.

4. What is the difference between Mockito and Testcontainers?
Mockito	Testcontainers
Creates fake objects	Starts real services
Unit Testing	Integration Testing
Fast	More realistic
5. When should we use Testcontainers?

When testing

Spring Data JPA
Repository Layer
Database Queries
Database Integration
Easy Memory Trick
Need Business Logic?

↓

Mockito

-----------------------

Need Real Database?

↓

Testcontainers

-----------------------

Need SQL?

↓

Testcontainers

-----------------------

Need Speed?

↓

Mockito

-----------------------

Need Production-like Test?

↓

Testcontainers
One-Line Summary

Testcontainers allows Java applications to run real databases such as PostgreSQL inside Docker containers, enabling reliable, isolated, and production-like integration testing without manual database setup.

JaCoCo (Code Coverage)
Learning Objective

After completing this section, participants will be able to:

Understand what Code Coverage is.
Learn how JaCoCo measures code coverage.
Read JaCoCo reports.
Understand Line, Method, Branch, and Class coverage.
Learn why 100% coverage does not always mean good testing.
Apply JaCoCo in enterprise Java projects.
Trainer Introduction

Suppose we wrote

100 Unit Tests.

Question

How do we know

whether those tests

actually execute

our application code?

JUnit tells us

Test Passed

But it does not tell us

how much code

was actually tested.

JaCoCo provides

the answer.

What is JaCoCo?

JaCoCo stands for

Java Code Coverage

It measures

how much of your application

is executed

while running tests.

It generates

a detailed coverage report

for developers.

Why Do We Need Code Coverage?

Suppose

TradeService

contains

10 methods.

Your Unit Tests execute

only

8 methods.

Coverage

80%

Question

Which two methods

were never tested?

JaCoCo identifies them.

Example

Suppose

Calculator.java

contains

public class Calculator {

    public int add(int a,int b){

        return a+b;

    }

    public int subtract(int a,int b){

        return a-b;

    }

}

Suppose

your test calls only

add()

Then

add()

Covered

✔

subtract()

Not Covered

✘

Coverage becomes

50%
What Does JaCoCo Measure?

JaCoCo measures

Instructions
Branches
Lines
Methods
Classes

Exactly as shown in the trainer material.

1. Line Coverage

Question

Were the program lines executed?

Example

int total = price * quantity;

return total;

If both lines execute

Line Coverage

100%
2. Method Coverage

Question

Were the methods executed?

Example

add()

subtract()

multiply()

If only

add()

subtract()

execute

Coverage

66%
3. Branch Coverage

Branch Coverage checks

every possible decision.

Example

if(amount>100000){

    return "HIGH";

}

return "LOW";

Question

Did tests execute

both

HIGH

and

LOW

If only HIGH executes

Branch Coverage

50%
4. Class Coverage

Question

Were all classes executed?

Example

TradeService

✔

TradeRepository

✔

AuditService

✘

Coverage

66%
Understanding JaCoCo Colors

JaCoCo reports

three colors.

Green

↓

Executed

-------------------

Yellow

↓

Partially Executed

-------------------

Red

↓

Never Executed

Goal

Reduce

Red

as much as possible.

Real Banking Example

Suppose

Trade Validation

contains

if(price<=0){

    throw new RuntimeException();

}

Your test verifies only

price =100

Question

Did we test

price =0

price =-10

No.

JaCoCo reports

partial branch coverage.

Sample Coverage Report
Instructions

95%

Lines

92%

Methods

100%

Branches

70%

Classes

100%

This means

some decision paths

have not yet been tested.

Adding JaCoCo to Maven
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.12</version>

    <executions>

        <execution>

            <goals>

                <goal>prepare-agent</goal>

            </goals>

        </execution>

        <execution>

            <id>report</id>

            <phase>test</phase>

            <goals>

                <goal>report</goal>

            </goals>

        </execution>

    </executions>

</plugin>
Generate Coverage Report

Run

mvn clean test

Then

mvn jacoco:report

JaCoCo generates

target/site/jacoco/index.html

Open

index.html

to view

the graphical report.

What Happens Internally?
Run Tests

        │

        ▼

JaCoCo Records

Executed Code

        │

        ▼

Generates Report

        │

        ▼

Developer Reviews

Coverage

        │

        ▼

Add Missing Tests
Should Coverage Be 100%?

Question

Should every project

have

100%

coverage?

No.

Coverage measures

execution,

not

correctness.

A poor test

may execute

every line

and still

miss business problems.

Example

Suppose

int result = add(10,20);

Test

assertThat(result)
        .isNotNull();

Coverage

100%

But

did we verify

the answer

is

30?

No.

So

100% coverage

does not

mean

100% quality.

Enterprise Banking Example

Suppose ReconX contains

Trade Matching
VWAP Calculation
Settlement
Reporting

JaCoCo identifies

which modules

are not tested.

Developers

add more tests

before releasing

the application.

Enterprise Testing Pipeline
Developer

        │

        ▼

JUnit

        │

        ▼

Mockito

        │

        ▼

Business Logic Tested

        │

        ▼

Testcontainers

        │

        ▼

Real Database Tested

        │

        ▼

JaCoCo

        │

        ▼

Coverage Report

        │

        ▼

CI/CD Pipeline

        │

        ▼

Production

This is the testing workflow followed by most enterprise Java projects.

Best Practices

✔ Write meaningful Unit Tests.

✔ Test both positive and negative scenarios.

✔ Test all important business rules.

✔ Improve Branch Coverage for decision logic.

✔ Use JaCoCo to identify untested code.

✔ Focus on test quality, not only coverage percentage.

Common Mistakes

❌ Assuming 100% coverage means bug-free software.

❌ Ignoring exception paths.

❌ Not testing if and else branches.

❌ Measuring only percentage instead of business scenarios.

Mockito vs Testcontainers vs JaCoCo
Tool	Purpose	Example
JUnit 5	Execute Unit Tests	Run test methods
AssertJ	Verify expected results	assertThat(result).isEqualTo(100)
Mockito	Mock dependencies	Fake Repository
Testcontainers	Integration Testing	Real PostgreSQL in Docker
JaCoCo	Measure Code Coverage	Coverage Report
Interview Questions
1. What is JaCoCo?

JaCoCo is a Java code coverage tool that measures how much of an application's code is executed during tests and generates coverage reports.

2. What is Code Coverage?

Code Coverage is the percentage of application code executed while running automated tests.

3. What does JaCoCo measure?

JaCoCo measures:

Instructions
Branches
Lines
Methods
Classes
4. Does 100% Code Coverage guarantee bug-free software?

No.

It only shows that the code was executed during tests. It does not guarantee that the business logic is correct.

5. Why is Branch Coverage important?

Branch Coverage ensures that all decision paths (such as both the if and else branches) are tested.

Easy Memory Trick
JUnit

↓

Runs Tests

--------------------

Mockito

↓

Creates Fake Objects

--------------------

Testcontainers

↓

Starts Real Database

--------------------

JaCoCo

↓

Measures Coverage

--------------------

CI/CD

↓

Deploy Application
Final Summary – Enterprise Java Testing
Write Test

        │

        ▼

JUnit

        │

        ▼

AssertJ

        │

        ▼

Mockito

        │

        ▼

Business Logic Verified

        │

        ▼

Testcontainers

        │

        ▼

Real Database Verified

        │

        ▼

JaCoCo

        │

        ▼

Coverage Report

        │

        ▼

CI/CD Pipeline

        │

        ▼

Production
Complete Day 4 Summary

By the end of Day 4, participants should be able to:

Explain the importance of software testing.
Write Unit Tests using JUnit 5.
Create readable assertions using AssertJ.
Follow the Arrange–Act–Assert (AAA) pattern.
Mock external dependencies using Mockito.
Verify method calls and capture arguments with verify() and ArgumentCaptor.
Write Integration Tests using Testcontainers and a real PostgreSQL database.
Measure and interpret code coverage reports using JaCoCo.
Understand the role of JUnit, AssertJ, Mockito, Testcontainers, and JaCoCo in enterprise Java projects.

