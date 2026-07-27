DAY 2 – OOP, SOLID & THE DOMAIN MODEL

(Based on Slides 2–5)

• Apply OOP concepts in a real enterprise application.
• Design a type-safe Java Domain Model.
• Use Java 21 features like Records and Sealed Interfaces.
• Build immutable domain objects.
• Apply Builder and Factory Design Patterns.
• Create a custom Exception Hierarchy.
• Implement Object Contracts (equals(), hashCode(), Comparable()).
• Apply Bean Validation (JSR-380).
• Understand and apply all SOLID principles.


Yesterday (Day 1) we built the database.

We created tables. We designed relationships.
We normalized the schema. We optimized queries using indexes.
We managed database changes using Liquibase.

Today,

we move from the Database Layer to the Java Layer.

Question:

Where does the application store business logic?



Every enterprise application is built around a Domain Model.

The Domain Model represents real business entities using Java classes.

Examples

Bank
↓
Account
Customer
Transaction

Hospital
↓
Patient
Doctor
Appointment

ReconX
↓
Trade
Money
Trade Reference
Asset Class
Counterparty

Today we will build these Java objects correctly using Object-Oriented Programming (OOP).

What is a Domain Model?
A Domain Model is a collection of Java classes that represent real business objects.

It contains

• Business Data
• Business Rules
• Relationships
• Validation

Instead of storing everything as primitive data types,
we create meaningful objects.
Example
Instead of
String tradeRef;
BigDecimal amount;
String currency;

We create

TradeRef tradeRef;
Money money;
TradeType tradeType;

The application becomes 
More readable
More maintainable
More type-safe.

Why Does the Domain Model Matter?

Imagine building a Banking Application.
Without a Domain Model

String accountNumber;
double balance;
String currency;

Nothing prevents
Negative Balance
Wrong Currency
Invalid Account Number
Now create proper objects

AccountNumber
Money
Currency

Every object validates itself.

Invalid data never enters the system.

This is called Domain Driven Design.

Day 2 Roadmap

Today's session contains two major modules.

Module 1

OOP Mastery

Topics
• Four Pillars of OOP
• Sealed Interfaces
• Records
• Builder Pattern
• Factory Pattern
• Exception Hierarchy

Module 2

Values, Contracts & SOLID

Topics
• Strings
• Package Design
• equals()
• hashCode()
• Comparable()
• Enums with Behaviour
• Bean Validation
• SOLID Principles

Everything we learn today will be used in the Spring Boot application during later sessions.

Where Does the Domain Model Sit?
Let's understand the complete architecture.

React UI
↓
REST API
↓
Service Layer
↓
Java Domain Model
↓

Database

The Domain Model is the center of the application.
Every layer communicates through Domain Objects.

Understanding the Architecture Diagram

The PPT shows four layers.

Presentation Layer

React Dashboard
↓
Application Layer
REST Controllers
Services
Kafka Producers
↓
Java Domain Model

Trade
Money
TradeRef
Enums
Exceptions
↓
Data Layer

PostgreSQL

JPA

Notice something important.
The Service Layer never directly works with database tables.

Instead,
it works with Java Objects.
Database Tables
↓
JPA Entity
↓
Java Object
↓
Business Logic
↓

REST Response

This separation keeps the application clean and maintainable.
Real-Time Banking Example

Suppose a customer transfers
₹50,000

The UI sends
{
  "fromAccount":"12345",
  "toAccount":"56789",
  "amount":50000
}

The Service Layer should not process raw JSON.
Instead, it creates 
Money, Account, Transaction Objects.

These objects validate

Amount Currency Account Number

before business logic executes.

Why Not Use Primitive Types Everywhere?

Imagine

String currency;
BigDecimal amount;

Developer accidentally writes

amount = -1000;
currency = "ABC";

Program still compiles.

Now create

Money money;

Money constructor validates
Amount > 0
Currency exists
Invalid Money objects cannot be created.
This is why enterprise applications use Domain Models.

Why Is Today's Session Important?
Everything we build later depends on today's classes.

Tomorrow
Spring Boot Services
↓
Need Trade Objects
REST APIs
↓
Return Trade Objects
Kafka
↓

Publishes Trade Objects
Reconciliation Engine
↓
Processes Trade Objects
If our Domain Model is poorly designed, every layer becomes difficult to maintain.

If our Domain Model is correct, the rest of the application becomes much simpler.

How Every Topic Will Be Taught 
The PPT explains the learning pattern used throughout the course.

Each topic follows the same sequence.

Step 1
Understand the concept.
↓
Step 2
Learn where it is used.
↓
Step 3
See the architecture diagram.
↓
Step 4
Study the real ReconX code.
↓
Step 5
Perform the lab.
↓
Step 6
Complete the knowledge check.



This approach helps connect theory with practical implementation.

Real-Time ReconX Flow
Trade File Received
↓
TradeFactory creates Trade Objects
↓
Money validates Amount
↓
TradeRef validates Reference
↓
Service Layer processes Trade
↓
Kafka publishes Trade
↓
Database stores Trade
Notice

Every layer depends on the Java Domain Model.

DAY 2 – OOP, SOLID & THE DOMAIN MODEL

MODULE 1 – OBJECT-ORIENTED PROGRAMMING (OOP)
SECTION 1 – The Four Pillars of OOP

(Based on Slides 6–12)

Learning Objective

After completing this section, participants will be able to:

• Understand Object-Oriented Programming (OOP).

• Understand the Four Pillars of OOP.

• Apply Encapsulation.

• Apply Inheritance.

• Apply Polymorphism.

• Apply Abstraction.

• Understand where OOP is used in enterprise banking applications.


Before Java became popular, most programming languages followed Procedural Programming.

Example

Read Data
↓
Process Data
↓
Print Result

Everything was written inside functions. As applications became larger,

developers faced many problems.

• Duplicate code
• Difficult maintenance
• Poor scalability
• Difficult testing

To solve these problems,

Object-Oriented Programming (OOP) was introduced. Instead of thinking in terms of functions,

we think in terms of Objects.

Real World
↓
Objects
↓

Software Objects
For example,
Bank
↓
Customer
↓

Java Class

Hospital
↓
Patient
↓

Java Class

ReconX
↓
Trade
↓

Java Class

Everything becomes an Object.

What is OOP?

Object-Oriented Programming is a programming methodology where software is built using Objects.

Every Object contains

• State (Data)
• Behaviour (Methods)

Example

Trade
    State
        Trade Reference
        Quantity
        Price
        Status
Behaviour
    validate()
    match()
    settle()
    cancel()

Data and Behaviour remain together.

This makes applications easier to understand.

Real-Time Banking Example

Suppose a Trade arrives. Without OOP Variables 
String tradeRef;
BigDecimal price;
String status;
Business Logic
    validateTrade();
    settleTrade();
    cancelTrade();

Everything is separated.

Hard to maintain.

With OOP

Trade trade = new Trade();
Now
    trade.validate();   
    trade.settle();
    trade.cancel();

Everything related to Trade remains inside Trade.

The Four Pillars of OOP

Every enterprise application uses four principles.

Encapsulation
Inheritance
Polymorphism
Abstraction

These four pillars make software

• Modular
• Reusable
• Secure
• Maintainable


Pillar 1 – Encapsulation

Encapsulation means
Wrapping Data and Methods together inside one Class.
Also, protecting internal data using access modifiers.

Think of a Bank ATM.
Customer can
    Deposit
    Withdraw
    Check Balance
Customer cannot directly access Database

Internal Balance Variable
Similarly,
Java objects expose only required methods.
Internal implementation remains hidden.

Example Without Encapsulation

public class Trade {
    public String tradeRef;
    public BigDecimal amount;
}

Problem Anyone can write
trade.amount = new BigDecimal("-1000");
Negative Amount.
No validation.
Unsafe.

Example With Encapsulation

public class Trade {
    private BigDecimal amount;
    public void setAmount(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException();
        }
        this.amount=amount;
    }
    public BigDecimal getAmount(){
        return amount;
    }
}

Explain
private
↓
Protects Data
Getter
↓
Read Value
Setter
↓

Validates Data Only valid data enters the object.

Why Encapsulation?
Suppose Trade Amount cannot be negative.
If variables are public, anyone can assign
-5000
Application becomes inconsistent.

Encapsulation guarantees Business Rules remain inside the object.
Real Banking Example
Customer Account
Customer should never directly change
Balance.

Instead
Customer performs
    Deposit()
    Withdraw()
    Transfer()

These methods validate

• Amount
• Balance
• Account Status

This is Encapsulation.

Pillar 2 – Inheritance

Inheritance means
Creating a new class from an existing class.
Child Class inherits Parent Class.
Purpose
Reuse existing code.
Example
Suppose

All financial instruments have
ISIN
Currency
Country
Create Parent Class
    class Instrument
    Child Classes
    Bond
    Equity
    Option

Instead of repeating
Currency
ISIN
Country

everywhere, they are inherited.

Java Example
class Instrument{
    String isin;
    String currency;
}
class Equity extends Instrument{
    String exchange;

}
class Bond extends Instrument{
    double coupon;

}

Both classes automatically inherit
ISIN and Currency

Benefits of Inheritance

• Reuse code.
• Reduce duplication.
• Easier maintenance.
• Logical hierarchy.

Real Banking Example

Employee
↓
Trader
↓
Senior Trader
↓
Risk Manager

Every employee has

Employee ID
Name
Department
Only additional fields differ.

Inheritance models this naturally.

Pillar 3 – Polymorphism

Polymorphism means One Interface Many Implementations.

Same Method Different Behaviour.
Example
Suppose Trade has method
calculateCharges();
Equity  calculates  Brokerage.

Bond    calculates  Interest.

Option  calculates  Premium.

Method name remains same.

Implementation changes.

Java Example
interface Instrument{
    void process();

}
class Equity implements Instrument{
    public void process(){
        System.out.println("Equity Processing");
    }
}
class Bond implements Instrument{
    public void process(){
        System.out.println("Bond Processing");
    }
}

Same Method Different Behaviour.

Runtime Polymorphism

Instrument i;
i=new Equity();
i.process();

Output
Equity Processing

Later
i=new Bond();
i.process();

Output
Bond Processing

Same variable.

Different behaviour. This is Runtime Polymorphism.

Why Polymorphism?

Suppose tomorrow Crypto is introduced.
Simply create
class Crypto implements Instrument
No existing code changes.
Application automatically supports new asset types.



Pillar 4 – Abstraction
Abstraction means Showing only essential features. Hiding implementation details.
Real-Life Example
Driving a Car. 
Driver knows Accelerator Brake Steering
Driver does NOT know Fuel Injection Engine Timing Gear Synchronization Internal Engine Logic

Those details remain hidden.

Java Example

interface Payment{
    void pay();
}
Implementation

class CardPayment

class UPIPayment

class NetBankingPayment

Customer only calls
pay();

Customer never knows how payment is internally processed.

Real Banking Example

Spring Boot Service
calls tradeRepository.save();

Developer never writes SQL Connection Pool

Transaction Management
Hibernate hides all implementation.

This is Abstraction.

Complete ReconX Example

Trade Processing
↓
Trade Object
↓
validate()
↓
process()
↓
settle()
↓
publish() Internally
Kafka
↓
PostgreSQL
↓
REST
↓

Logging

All hidden.
Business Logic remains simple.

Comparing the Four Pillars
Pillar	Purpose	Example

Encapsulation	    Protect data	                    private fields + getters/setters
Inheritance	        Reuse code	                        Equity extends Instrument
Polymorphism	    Same method, different behaviour	process()
Abstraction	        Hide implementation	                Interface + implementation

Why OOP is Important?
Large Banking Systems contain
Thousands of Classes.
Millions of Lines of Code.
Without OOP, maintenance becomes impossible.

OOP allows

• Reusability
• Scalability
• Loose Coupling
• Easy Testing
• Clean Architecture


DAY 2 – OOP, SOLID & THE DOMAIN MODEL
MODULE 1 – OBJECT-ORIENTED PROGRAMMING (OOP)
SECTION 2 – Sealed Classes, Sealed Interfaces & Records

(Based on Slides 13–18)

Learning Objective


• Understand Sealed Classes and Sealed Interfaces.
• Understand the need for restricting inheritance.
• Learn the permits keyword.
• Understand Records in Java.
• Create immutable objects.
• Understand why modern Java uses Records instead of traditional POJOs.
• Learn enterprise use cases in the ReconX application.


Until Java 16, any class could extend another class unless it was declared as final.

Example

class Instrument{
}
class Equity extends Instrument{
}
class Bond extends Instrument{
}
class Crypto extends Instrument{
}
class MyOwnInstrument extends Instrument{
}

Question:

Should every developer be allowed to create new financial instruments?

In enterprise applications,
the answer is usually No.

Business defines valid instrument types.
Developers should not invent new types without approval.

To solve this problem,

Java introduced Sealed Classes and Sealed Interfaces.
Problem Before Sealed Classes

Suppose we have

class Payment{
}

Developer A creates
class CardPayment extends Payment{
}

Developer B creates
class UPIPayment extends Payment{
}

Developer C creates
class BitcoinPayment extends Payment{
}

Developer D creates
class UnknownPayment extends Payment{
}

Application becomes difficult to control.

Business requirements may allow only

• Card
• UPI
• Net Banking

No other payment types.

Sealed Classes solve this problem.

What is a Sealed Class?
A Sealed Class restricts which classes can inherit from it.
Only explicitly permitted classes can extend it.

Example

public sealed class Instrument
permits Equity, Bond, Option {

}

Explain
sealed
Restricts inheritance.

permits Specifies the only allowed subclasses.

Now,

only

• Equity
• Bond
• Option

can extend Instrument.

Any other class results in a compilation error.

Real-Time Banking Example

ReconX supports only three asset classes.

• Equity
• Bond
• Option

Developer writes

class Crypto extends Instrument{
}

Compiler Error.
Reason
Crypto is not listed in the permits clause.

This prevents unauthorized extension of the domain model.

Sealed Interface
Just like classes,
interfaces can also be sealed.

Example

public sealed interface TradeEvent
permits TradeCreated,TradeMatched,
TradeCancelled {

}

Only these three events are valid.

Developers cannot introduce arbitrary event types.
This ensures the event model remains controlled.
Types of Permitted Classes

A permitted subclass must declare one of the following:

• final
• sealed
• non-sealed

Let's understand each one.

final Class

final class Equity extends Instrument{
}

Meaning
No class can extend Equity.

Inheritance stops here.

Use when the hierarchy should end.

sealed Class sealed class Bond
extends Instrument
permits CorporateBond,
GovernmentBond{
}

Meaning

Bond itself restricts further inheritance.

Only the listed subclasses are allowed.

non-sealed Class

non-sealed class Option
extends Instrument{
}

Meaning
Inheritance restrictions are removed.
Now anyone can extend Option.

Example

class EuropeanOption extends Option{
}
class AmericanOption extends Option{
}

Complete Example

public sealed interface Instrument
permits Equity,
Bond,
Option{
}

public final class Equity
implements Instrument{
}

public sealed class Bond
implements Instrument
permits CorporateBond,
GovernmentBond{
}

public non-sealed class Option
implements Instrument{
}

This is a common enterprise design.

Why Use Sealed Classes?

Advantages

• Prevent unauthorized inheritance.
• Improve compiler checking.
• Easier maintenance.
• Safer domain model.
• Better pattern matching support.
• Cleaner business hierarchy.

What is a Record?

The PPT next introduces another modern Java feature:

Record.

Before Java 16, creating a simple value object required a lot of boilerplate code.


String str = new String("Ravi");
SOP(str.toUpperCase())
SOP(str);
str = str+"Deep";
SOP(str);

Example



public class TradeRef{
    private final String value;
    public TradeRef(String value){
        this.value=value;
    }
    public String getValue(){
        return value;
    }
    @Override
    public boolean equals(...){}
    @Override
    public int hashCode(){}
    @Override
    public String toString(){}
}

More than 40 lines of code. Most of it is repetitive.

Record Solution

Same class becomes
public record TradeRef(String value){

}

Only one line.

Java automatically generates

• Constructor
• Getter
• equals()
• hashCode()
• toString()

Much cleaner.

What Java Automatically Creates
For
public record Money(BigDecimal amount, Currency currency){
}

Java generates

Constructor
new Money(amount,currency)

Accessor Methods
money.amount()
money.currency()
equals()
hashCode()
toString()

No need to write them manually.

What is Immutability?

A Record is immutable. After object creation,
its values cannot change.

Example

Money money = new Money(new BigDecimal("100"), Currency.INR);

After creation

money.amount=200;

Compiler Error.
The object cannot be modified.

Why Immutability?
Suppose two threads share the same Money object.
If one thread changes Amount, the second thread receives incorrect data.

Immutable objects prevent such issues.

Benefits
• Thread Safety
• Predictable Behaviour
• Easier Debugging
• Better Performance

Real-Time Banking Example

Money
₹10,000 should never change accidentally.

Instead of changing Money, create a new Money object. Old Object

₹10,000
↓
New Object
₹15,000

Original value remains safe.
Adding Validation Inside Record
Records can contain constructors.
Example

public record TradeRef(String value){
    public TradeRef{
        if(value==null || value.isBlank()){
            throw new IllegalArgumentException(
            "Trade Reference cannot be empty");
        }
    }
}

Now

Every TradeRef object validates itself.
Invalid objects cannot be created.
Why Records are Better than POJOs?

Traditional POJO
• Large amount of boilerplate code.
• Manual equals().
• Manual hashCode().
• Manual constructor.
• Manual toString().

Record
• Compact.
• Immutable.
• Less code.
• Automatically generated methods.
• Better readability.

ReconX Example

Instead of
String tradeRef;
BigDecimal amount;
String currency;

Create
TradeRef tradeRef;
Money money;

Money
↓
Immutable
TradeRef
↓
Validated

Application becomes Type Safe
Reliable Easy to Maintain.


Transition to Next Section

Now that we have designed immutable domain objects using Records and controlled inheritance using Sealed Classes, the next section introduces two of the most widely used enterprise design patterns: the Builder Pattern and the Factory Pattern. We'll learn why these patterns simplify object creation, avoid constructor overloads, and make complex business object creation more readable and maintainable.

DAY 2 – OOP, SOLID & THE DOMAIN MODEL
MODULE 1 – OBJECT-ORIENTED PROGRAMMING (OOP)
SECTION 3 – Builder Pattern & Factory Pattern

(Based on Slides 19–24)

Learning Objective

After completing this section, participants will be able to:

• Understand why Design Patterns are required.
• Learn the Builder Pattern.
• Understand method chaining.
• Build immutable complex objects.
• Learn the Factory Pattern.
• Understand when Builder and Factory should be used.
• Apply both patterns in enterprise applications.


Until now, we have learned how to create Java objects using constructors.

Example

Trade trade = new Trade();
or
Trade trade = new Trade(
        tradeRef,
        quantity,
        price,
        counterparty,
        instrument,
        tradeDate,
        status);

Question

What happens if a class contains 15 fields?
Will we remember the order?

Probably not.
Suppose the constructor is
Trade(
tradeRef,
price,
quantity,
currency,
counterparty,
instrument,
tradeDate,
status,
book,
portfolio,
trader)

A small mistake changes the meaning of the object.

This is why enterprise applications use the Builder Pattern.
Problem Before Builder Pattern
Suppose we have

public class Trade {
    public Trade(
        String tradeRef,
        BigDecimal price,
        int quantity,
        String currency,
        String counterparty,
        LocalDate tradeDate,
        String trader,
        String portfolio,
        String status){
    }
}

Creating an object

Trade trade = new Trade("TRD001", new BigDecimal("100"),100,
                    "INR","JP Morgan",LocalDate.now(),
                    "Akash","Retail","PENDING");

Question
Can you easily identify

Which value belongs to which field?
No.

This is difficult to read and maintain.

What is the Builder Pattern?

Builder Pattern creates complex objects
step by step. Instead of passing every value into one constructor,
we build the object gradually.

Example

Trade trade = Trade.builder()
↓
.tradeRef("TRD001")

↓
.quantity(100)

↓
.price(new BigDecimal("150"))

↓
.build();

Each field is clearly visible.

Why Builder Pattern?

Advantages
• Better readability.
• No constructor confusion.
• Easy to maintain.
• Supports optional fields.
• Creates immutable objects.
• Fluent API.

Builder Pattern is widely used in

• Spring Boot
• Lombok
• Hibernate
• AWS SDK
• Kafka Clients

Builder Pattern Example

Traditional Constructor

Trade trade = new Trade("TRD001",100,new BigDecimal("250"),"PENDING");

import java.math.BigDecimal;

public class Trade {
    private String tradeRef;
    private int quantity;
    private BigDecimal price;
    private String status;

    public Trade(String tradeRef,
                 int quantity,
                 BigDecimal price,
                 String status) {

        this.tradeRef = tradeRef;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public void display() {
        System.out.println("Trade Reference : " + tradeRef);
        System.out.println("Quantity        : " + quantity);
        System.out.println("Price           : " + price);
        System.out.println("Status          : " + status);
    }
}


import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Trade trade = new Trade(
                "TRD001",
                100,
                new BigDecimal("250"),
                "PENDING"
        );
        trade.display();
    }
}

Builder Pattern 

Trade.java 

import java.math.BigDecimal;

public class Trade {

    private String tradeRef;
    private int quantity;
    private BigDecimal price;
    private String status;

    // Private Constructor
    private Trade(Builder builder) {
        this.tradeRef = builder.tradeRef;
        this.quantity = builder.quantity;
        this.price = builder.price;
        this.status = builder.status;
    }

    // Static Builder Method
    public static Builder builder() {
        return new Builder();
    }

    // Display Method
    public void display() {
        System.out.println("Trade Reference : " + tradeRef);
        System.out.println("Quantity        : " + quantity);
        System.out.println("Price           : " + price);
        System.out.println("Status          : " + status);
    }

    // Builder Class
    public static class Builder {

        private String tradeRef;
        private int quantity;
        private BigDecimal price;
        private String status;

        public Builder tradeRef(String tradeRef) {
            this.tradeRef = tradeRef;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Trade build() {
            return new Trade(this);
        }
    }
}

Main.java 
import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        Trade trade = Trade.builder()
                .tradeRef("TRD001")
                .quantity(100)
                .price(new BigDecimal("250"))
                .status("PENDING")
                .build();

        trade.display();

    }

}

Notice

Every value is self-explanatory.
Understanding Method Chaining
Builder works because every setter method returns


What is Factory Pattern?
Now the PPT introduces

Factory Pattern.
Question

Who should create objects?

Client?
Or
Dedicated Factory?

Factory Pattern centralizes object creation.
Instead of using new everywhere,
the Factory decides which object to create.
Why Factory Pattern?
Suppose

ReconX supports

• Equity
• Bond
• Option

Question Should Controller know which class to instantiate?

No.

Controller asks Factory. Factory returns correct object.

Real-Time Example

Without Factory

if(type.equals("EQUITY"))
new Equity();
else if(type.equals("BOND"))
new Bond();
else
new Option();

Every Service duplicates this logic.
Poor design.

Factory Solution

Create InstrumentFactory.
Controller simply writes

Instrument instrument = InstrumentFactory.create(type);

Factory handles the rest.

Factory Example

AssetType.java (Enum)

public enum AssetType {

    EQUITY,
    BOND,
    OPTION

}

Instrument.java (Interface)

public interface Instrument {
    void trade();
}

Equity.java

public class Equity implements Instrument {

    @Override
    public void trade() {
        System.out.println("Trading Equity Instrument");
    }

}
Bond.java
public class Bond implements Instrument {
    @Override
    public void trade() {
        System.out.println("Trading Bond Instrument");
    }

}

Option.java
public class Option implements Instrument {

    @Override
    public void trade() {
        System.out.println("Trading Option Instrument");
    }

}

InstrumentFactory.java

public class InstrumentFactory {

    public static Instrument create(AssetType type) {

        return switch (type) {

            case EQUITY -> new Equity();

            case BOND -> new Bond();

            case OPTION -> new Option();

        };

    }

}
Main.java
public class Main {

    public static void main(String[] args) {

        Instrument equity =
                InstrumentFactory.create(AssetType.EQUITY);
        equity.trade();
        System.out.println();
        Instrument bond =
                InstrumentFactory.create(AssetType.BOND);
        bond.trade();
        System.out.println();
        Instrument option =
                InstrumentFactory.create(AssetType.OPTION);
        option.trade();
    }

}



Explain
Input
↓
Asset Type
Factory
↓
Creates Correct Object
↓
Returns Parent Interface

Trade File contains
Asset Type
EQUITY
Application
↓
Factory
↓
Creates 
new Equity();

Tomorrow

New Asset
CRYPTO

Only Factory changes.
Entire application continues working.


Builder vs Factory

Builder
Creates one complex object step by step.

Factory Decides which object to create.

Builder Focus Construction.

Factory Focus Selection.

Builder             vs              Factory Comparison
Builder Pattern	                    Factory Pattern
Creates complex objects	            Chooses which object to create
Step-by-step construction	        Centralized object creation
Uses method chaining	            Uses conditional logic or polymorphism
Good for many fields	            Good for multiple implementations
Ends with build()	                Returns the required object

Complete ReconX Flow

Trade JSON
↓
TradeFactory
↓
Creates Equity
↓
Builder
↓
Creates Trade
↓
Validation
↓
Trade Object Ready
Notice
Factory chooses
Builder constructs.

Both patterns work together.

Transition to Next Section

Now that we know how to create domain objects using Builder and Factory patterns, the next section explains Exception Hierarchy and Custom Exceptions. We will learn how enterprise applications organize exceptions, create meaningful custom exception classes, implement global error handling, and build a robust error-handling strategy for Spring Boot applications.

DAY 2 – OOP, SOLID & THE DOMAIN MODEL
MODULE 1 – OBJECT-ORIENTED PROGRAMMING (OOP)
SECTION 4 – Exception Hierarchy & Custom Exceptions

(Based on Slides 23–29)

Learning Objective

After completing this section, participants will be able to:

• Understand Java Exceptions.
• Understand Checked vs Unchecked Exceptions.
• Create a Custom Exception Hierarchy.
• Learn why enterprise applications use RuntimeException.
• Design domain-specific exceptions.
• Understand how Spring Boot maps exceptions to HTTP responses.
• Build a reusable exception model.


No application runs perfectly.
Many things can go wrong.

Examples
• Invalid User Input
• Database Connection Failure
• Duplicate Trade Reference
• Trade Not Found
• Invalid Trade Price

Question

How should our application report these errors?

Option 1
Return  null

Problem

Caller never knows
Why it failed.
Option 2
Return  -1

Problem
What does   -1  mean?

It is 
Duplicate?
Invalid?
Not Found?

Option 3
Throw a meaningful Exception.

This is the preferred enterprise solution.

What is an Exception?
An Exception is an object that represents an error or 
unexpected condition during program execution.

Instead of silently failing,

the application throws an Exception.

Example

throw new InvalidTradeException();

The caller immediately knows

Something went wrong.

Real Banking Example
Suppose
Customer transfers
₹5,000
Balance Available
₹2,000

Question
Should application return
false

No.
Correct approach
throw new InsufficientBalanceException();

Now Caller understands

Exactly why the transaction failed.

Why Custom Exceptions?

Java already provides

IllegalArgumentException
NullPointerException
IOException

Question

Can these describe business problems?

Example
Duplicate Trade Reference
No.

Business needs

DuplicateTradeRefException
The exception name itself explains the problem.

Enterprise Problem
Suppose every developer throws
RuntimeException

Example
throw new RuntimeException("Error");

Question

What error occurred?

Impossible to know.

Instead,

abstract class ReconException extends RuntimeException {
    MyException() {


    }
    MyException(String name){
        super(name);
    }
}

throw new MyException();
or 
throw new MyException("custom message");


Create meaningful exception classes.

Exception Hierarchy

The PPT recommends creating one abstract root exception.

Example

RuntimeException
        │
ReconException
   │      │      │      │
DuplicateTradeRefException
InvalidTradeException
TradeNotFoundException
ReconMismatchException

Every business exception belongs to one hierarchy.

Why One Root Exception?
Suppose our application has

50 different business exceptions.

Question
How do we catch all of them?
Instead of
catch(DuplicateTradeRefException e)
catch(InvalidTradeException e)
catch(TradeNotFoundException e)

Simply write

catch(ReconException e)
Every business exception is handled together.
Abstract Root Exception

Example
public abstract class ReconException extends RuntimeException{
    protected ReconException(String message){
        super(message);
    }
    protected ReconException(
            String message,
            Throwable cause){
        super(message,cause);
    }

}

Explain Line by Line
extends RuntimeException

Creates an Unchecked Exception.

No

throws required.

abstract

Cannot create new ReconException();

Only child classes can be created.

Reason
ReconException is only a parent.
It never represents a real error.
super(message);
Passes the message to
RuntimeException.
Java automatically stores the error message.
super(message,cause);
Stores
Original Exception

Custom Message.
Useful when wrapping another exception.

Why RuntimeException?
Question Should every service method write
throws InvalidTradeException
Not necessary.
Enterprise applications usually use
Unchecked Exceptions
for business errors.

Benefits
• Cleaner code
• Less boilerplate
• Spring Boot automatically handles RuntimeExceptions

Checked vs Unchecked Exceptions

Checked Exception
Examples
IOException
SQLException
Compiler forces
throws
or
try-catch

Unchecked Exception
Examples
NullPointerException
IllegalArgumentException
RuntimeException

Compiler does not force handling.

Business exceptions usually extend RuntimeException.
Creating a Custom Exception
Example
public class DuplicateTradeRefException extends ReconException{
    public DuplicateTradeRefException(
            String tradeRef){
        super(
        "Duplicate Trade Reference : "
        + tradeRef);
    }
}

Explain

Child Class
↓
Specific Error
Constructor
↓
Creates meaningful message
Result

Duplicate Trade Reference : TRD1001
Real Banking Example
Suppose
Trade File contains
TRD1001

Database already contains

TRD1001 Application throws
throw new DuplicateTradeRefException(
        "TRD1001");

Instead of
RuntimeException

Operations Team immediately understands

Duplicate Trade Reference.
Trade Not Found Example
Suppose
Client requests

GET
/api/trades/100

Trade does not exist.

Throw
throw new TradeNotFoundException("100");

Later
Spring Boot converts it into
404
Not Found

Exactly as shown in the PPT.
Mapping Exceptions to HTTP Status
The PPT mentions that

Day 5

will map exceptions to HTTP responses.

Example

Exception	                      HTTP Status
DuplicateTradeRefException	      409 Conflict
InvalidTradeException	          400 Bad Request
TradeNotFoundException	          404 Not Found
ReconMismatchException	            422 Unprocessable Entity

Spring Boot

@ControllerAdvice

handles this automatically later.

Why Not Return Null?
Suppose
Trade trade = service.find(id);
Returns
null
Developer forgets
if(trade!=null)
Result NullPointerException

Instead
Throw TradeNotFoundException

Problem becomes clear.
Why Not Return Error Codes?
Old Style
-1 means    Unknown Error.

New Style
DuplicateTradeRefException
Name itself explains
the problem.
Much better.
Complete ReconX Flow

Trade Request
↓
Validation
↓
Duplicate Trade?
↓
Yes
↓
Throw
DuplicateTradeRefException
↓
Global Exception Handler
↓
HTTP 409 Conflict
↓

Client receives meaningful response.

Lab from PPT

Students should perform the following.

• Create
ReconException
• Extend RuntimeException
• Create
DuplicateTradeRefException
• Create
InvalidTradeException
• Throw custom exceptions
• Catch specific exception
• Catch parent exception



This completes Module 1 – OOP Mastery.

In the next module, Values, Contracts & SOLID, we will begin with Strings, Package Design, and Structured toString() methods, followed by equals(), hashCode(), Comparable(), Enums with Behavior, Bean Validation (JSR-380), and the SOLID Principles. These topics define how enterprise Java objects behave correctly in collections, APIs, validation frameworks, and clean architectures.

DAY 2 – OOP, SOLID & THE DOMAIN MODEL
MODULE 2 – VALUES, CONTRACTS & SOLID
SECTION 1 – Strings, Package Design & Structured toString()

(Based on Slides 30–32)

Learning Objective

After completing this section, participants will be able to:

• Understand Java String immutability.
• Learn why StringBuilder exists.
• Design clean package structures.
• Understand Java access modifiers.
• Write meaningful toString() methods.
• Learn secure logging practices.
• Organize enterprise Java projects correctly.


Suppose you join a company.

The project contains

1200 Java Classes
150 Packages
80 Developers

Question

Can every developer place classes anywhere?
No.

Without proper package structure,
the project becomes impossible to maintain.
Similarly,

logging every object incorrectly may expose

Customer Data
Passwords
Personal Information

Enterprise applications follow strict rules for

• Packages
• Logging
• Strings
• Access Control

Today's section explains these rules.

What is a String?
A String is a sequence of characters.
Example

String name1 = "Akash";             heap memory created. 
String name2 = "Akash";             name1 and name2 refer to same memory 
                                    string pooling 
String name3 = new String("Akash"); new memory created 
String name4 = new String("Akash"); new memory created 

name1==name2                true            == check value and memory reference                  
name3==name4                false 
name1.equals(name2)         true            equals check only value. 
name3.equals(name4)         true 

1 2 3 4 

Strings are used everywhere.

Examples

Customer Name
Trade Reference
Currency Code
ISIN
Email Address
JSON
REST URLs

Almost every Java application uses Strings.

Important Property of String

Strings are Immutable.

Question

What does Immutable mean?
Once a String object is created,

its value cannot be changed.

Example
String s = "Hello";
Now write

s = s + " World";

Question

Did Java modify

Hello?

No.

Java creates

a completely new String.

Old Object Hello

New Object
Hello World

Original object still exists until garbage collected.

Memory Representation
Step 1
String s = "Java";

Memory
s
↓
"Java"
Step 2
s = s + " 21";
Memory
"Java"
↓
Unused
"Java 21"
↑
s
Notice

Original String never changes.
A new object is created.
Why are Strings Immutable?

Advantages
• Thread Safe
• Secure
• Faster String Pool usage
• Safe Hashing
• Reliable Keys in HashMap

Because Strings never change, multiple threads can safely share them.

Real Banking Example

Trade Reference
TRD-20260716-0001
Should never change accidentally.
If Strings were mutable, someone could modify
Trade Reference after storage.
This would corrupt data. Immutability prevents such problems.

Problem with String Concatenation
Suppose
You concatenate
1000 Strings.

String result = "";
for(int i=1;i<=1000;i++){
    result += i;
}

Question

How many String objects are created?
Answer
1001+

Every concatenation creates
a new String.

Performance becomes poor.
Solution – StringBuilder
Java provides
StringBuilder
Example

StringBuilder sb =  new StringBuilder();

for(int i=1;i<=1000;i++){
    sb.append(i);
}

String result = sb.toString();

Only one mutable buffer is used.

Much faster.

String                      vs              StringBuilder
String	                                    StringBuilder
Immutable	                                Mutable
New object on every change	                Same object reused
Thread-safe because immutable	            Not thread-safe
Good for fixed values	                    Good for repeated modifications

When to Use StringBuilder?

Use StringBuilder

• Inside loops
• Building SQL
• Creating JSON
• Building log messages
• Creating reports


Do NOT use
+
inside large loops.

Real-Time Example
Bad


String log = "";
for(Trade t : trades){
    log += t.getTradeRef();     new memory created. 
}

Good

StringBuilder log = new StringBuilder();

for(Trade t : trades){
    log.append(t.getTradeRef());    only one memory 

}

Enterprise applications always prefer

StringBuilder for repeated concatenation.

Package Design

The PPT now discusses

java    --> root package 
javax -->root package 
java.lang.*;
javax.swing.*;


Package Structure.

Question

Why do packages exist?

Packages organize related classes.
Instead of placing 1000 classes inside one folder,

we group them logically.

Typical Spring Boot Package Structure

com.dbtraining.reconx
│
    ├── model
    ├── dto
    ├── controller
    ├── service
    ├── repository
    ├── exception
    ├── config
    ├── util

Every package has one responsibility.

Meaning of Each Package

model
    Contains Domain Objects.
    Examples
        Trade
        Money
        TradeRef
dto
    Contains Request and Response Objects.
    Example
        TradeRequest    
        TradeResponse
        DTOs are used to transfer data between the client 
        and the server.
controller
    Handles HTTP Requests.
    Example
        TradeController
        Receives client requests and returns responses.
service
    Contains Business Logic.
    Example
        TradeService
        Performs validation, calculations, and business processing.
repository or DAO
    Communicates with the Database.
    Example
    TradeRepository
    JDBC, ORM (Hibernate,SPA), spring JPA Data 
    Uses JPA or JDBC to read and write data.
exception
    Contains Custom Exceptions.
    Examples
    TradeNotFoundException
    InvalidTradeException
    Keeps all exception classes in one location.

Why Package by Feature?

Suppose Trade   contains
Controller
Service
DTO
Repository

Keeping related classes together makes projects easier to understand and maintain.
Large companies prefer clear package boundaries rather than random placement of classes.

Access Modifiers
Java provides four access levels.
public
protected
default (package-private)
private

Each controls

Who can access a class or member.

public: Accessible  Everywhere.

Example
public class TradeService{
}

Any class can use it.

private: Accessible Only inside the same class.

Example
private BigDecimal amount;
No other class can modify amount directly.
Supports Encapsulation.

protected: Accessible
Inside same package as well as other package if it is subclass. 

Useful during inheritance.

package-private: No keyword.
Example
class TradeValidator{
}
Accessible only inside the same package.

Useful for internal helper classes.

Why Use Access Modifiers?

Suppose Every variable is

public. Any class can change
Trade Amount.
Unsafe. 
Instead Keep fields private
Expose only
validated methods.
This protects business rules.

Structured toString()

Every Java Object inherits

toString()

Default Output

com.dbtraining.Trade@7f31245a   when we display reference of any class. 

Useful?
No.
Enterprise applications override

toString() to produce meaningful log messages.

Example
@Override
public String toString(){
    return "Trade[" +
            "ref=" + tradeRef +
            ", qty=" + quantity +
            ", price=" + price +
            ", currency=" + currency +
            "]";
}

Output
Trade[
ref=TRD001,
qty=100,
price=500,
currency=INR
]

Much more readable.

Why Override toString()?
Imagine debugging production issues.
Bad Log
Trade@12A56C

Developer learns nothing.

Good Log
Trade

Ref : TRD001
Qty : 100
Price : 250
Currency : INR
Immediately understandable.

Never Log Sensitive Information

The PPT highlights an important enterprise rule.
Never log
• Passwords
• PAN Numbers
• Aadhaar Numbers
• Credit Card Numbers
• Customer Names

Example

Bad
Counterparty Name: JP Morgan

Good
Counterparty ID: CP1001

Logs should contain only safe identifiers, not personally identifiable information (PII).
Real Banking Example
Suppose an application logs
Customer Password
123456
If logs are leaked, all customer accounts become vulnerable.

Instead, log only
User ID
Request ID
Trade Reference
Transaction ID

Never log confidential information.

Complete ReconX Flow

REST Request
↓
DTO
↓
Service
↓
Trade Object
↓
toString()
↓
Structured Log
↓
Developer reads logs
↓

Problem identified quickly

Logs become useful without exposing sensitive data.


Transition to Next Section

The next section covers one of the most important Java interview topics: Object Contracts—equals(), hashCode(), and Comparable. We'll understand how Java collections such as HashSet, HashMap, and TreeSet rely on these methods, why they must follow strict contracts, and how ReconX uses the business key (tradeRef) for equality and ordering.

DAY 2 – OOP, SOLID & THE DOMAIN MODEL
MODULE 2 – VALUES, CONTRACTS & SOLID
SECTION 2 – Object Contracts: equals(), hashCode() & Comparable

(Based on Slides 33–35)

Learning Objective

After completing this section, participants will be able to:

• Understand object equality.
• Learn the relationship between equals() and hashCode().
• Understand the Java Object Contract.
• Learn Comparable and natural ordering.
• Understand how HashSet and HashMap use these methods.
• Implement enterprise business keys correctly.


Every Java object automatically inherits methods from the Object class.
Some important methods are:
equals()
hashCode()
toString()
getClass()
clone()

Question

Why are equals() and hashCode() so important?

Because every Java Collection depends on them.

Examples

• HashSet
• HashMap
• LinkedHashMap
• TreeSet
• TreeMap

If these methods are implemented incorrectly,

your application may produce duplicate records, 
fail to retrieve objects correctly, or behave unpredictably.

What is Object Equality?
Suppose we create two Trade objects.

Trade t1 = new Trade("TRD001");      new heap memory 
Trade t2 = new Trade("TRD001");     new heap memory 

Question

Are these two objects equal?

There are two possible answers.
Object Identity
t1 == t2
Result
false

Reason

They are different objects stored at different memory locations.

== compares memory addresses.

Logical Equality
Business says
Trade Reference identifies a Trade.

Both objects have
TRD001
Business considers them
the same Trade.

Therefore

t1.equals(t2)

should return
true

Enterprise applications compare

Business Identity, not memory location.

Real Banking Example
Suppose
Database contains
Trade Reference
TRD1001
Another file arrives
Trade Reference
TRD1001
Should ReconX create another Trade?

No.

It is a duplicate.
Therefore Trade equality depends on
Trade Reference.
Not Object Address.
What is equals()?

equals() determines

Logical Equality.

Default implementation in Object compares memory addresses.

Enterprise applications override it.
Example
@Override
public boolean equals(Object o){
    if(this==o)
        return true;
    if(!(o instanceof Trade other))
        return false;
    return tradeRef.equals(other.tradeRef);
}

Here,
two Trade objects are equal if their
tradeRef is equal.
Understanding instanceof Pattern Matching

Modern Java allows
if(o instanceof Trade other)

instead of
if(o instanceof Trade){
    Trade other=(Trade)o;
}

Advantages
• Less code
• No explicit casting
• Better readability

Java automatically creates

other after successful type checking.

What is hashCode()?

hashCode() returns an integer value representing the object.

Collections like

HashMap and HashSet
use this value
to quickly locate objects.
Example

@Override
public int hashCode(){
    return tradeRef.hashCode();

}
Here
Trade Reference determines the hash value.
Relationship Between equals() and hashCode()
This is one of the most important interview questions.
Rule
If
a.equals(b) returns true
Then a.hashCode() must be equal to b.hashCode()
Otherwise
HashSet and HashMap
will behave incorrectly.
This is called
The Java Object Contract.
Why Does HashSet Need Both?
Suppose we insert
Trade t1 = new Trade("TRD001");
HashSet
calculates

hashCode()
↓
Finds the bucket.
↓
Calls
equals()
↓
Checks whether an equal object already exists.
If yes, duplicate is rejected.
How HashSet Works
Step 1
Calculate
hashCode()
↓
Step 2
Locate Bucket.
↓
Step 3
Call
equals()
↓
Duplicate?
↓
Yes
↓

Do Not Insert.

This is exactly what the PPT explains.

What Happens If the Contract is Broken?
Suppose equals() uses
Trade Reference.
But hashCode() uses
Object Identity.
Example
equals()
↓
tradeRef
hashCode()
↓
System.identityHashCode()
Now Two equal objects
have Different hash codes.
HashSet places them into different buckets.
Duplicate survives.
This is a serious bug.
Exactly as shown in the PPT.
Real Banking Example
ReconX receives
TRD20260716-0001
twice.
Correct implementation
equals()
↓
tradeRef
hashCode()
↓
tradeRef.hashCode()
Result
HashSet stores
only one Trade.
Duplicate automatically removed.

Understanding Comparable

Question

How should Trade objects be sorted?

Alphabetically?
Date?
Trade Reference?
Quantity?

Java provides Comparable to define Natural Ordering.

Comparable Interface

Example
public interface Comparable<T>{
    int compareTo(T other);
}

Implement compareTo() to define default sorting.

ReconX Example
The PPT defines Natural Ordering as Most Recent Trade First.

Example

@Override
public int compareTo(
TradeType other){
    return other.tradeDate()
            .compareTo(
            this.tradeDate());
}

Latest trades appear first.
No Comparator required.
The ordering is defined once on the shared interface.
compareTo() Return Values
Negative
↓
Current Object
comes before
Other Object
Zero
↓
Objects are Equal
Positive
↓

Current Object comes after
Other Object Java sorting algorithms depend on these return values.

Comparable vs Comparator

Comparable

• Natural Ordering
• Implemented inside the class
Example

Trade sorted by Date.

Comparator
• External Ordering
• Multiple sorting options

Example Sort by
Price
or
Quantity
or
Trade Reference.


Transition to Next Section

Now that we understand object identity, equality, hashing, and natural ordering, the next section introduces Enums with Behavior. We'll learn why Java enums are much more than constants—they can store data, implement methods, encapsulate business rules, and replace complex if-else or switch statements in enterprise applications such as ReconX.

DAY 2 – OOP, SOLID & THE DOMAIN MODEL
MODULE 2 – VALUES, CONTRACTS & SOLID
SECTION 3 – Enums with Behavior

(Based on Slides 36–37)

Learning Objective

After completing this section, participants will be able to:

• Understand Java Enums.
• Learn why Enums are type-safe.
• Store data inside Enums.
• Add methods and business logic to Enums.
• Replace multiple if-else conditions with Enums.
• Apply behavioral enums in enterprise applications.


What is an Enum?

An Enum (Enumeration) is a special Java type used to represent a fixed set of constant values.

Instead of using Strings or numbers, we use an Enum to make the program safe, readable, and error-free.

Simple Definition

An Enum is a special class in Java that represents a fixed collection of constants.

Real-Life Examples

Some values never change.

Days of the Week
Months
Traffic Signal
Currency
Order Status
Trade Status
Asset Type

These are perfect candidates for an Enum.

Example 1: Simple Enum
Day.java
public enum Day {

    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY

}

Main.java
public class Main {

    public static void main(String[] args) {

        Day today = Day.SATURDAY;

        System.out.println(today);

    }

}
Output
SATURDAY
Why Not Use String?

Suppose we store Trade Status as a String.

String status = "COMPLETED";

One day, a developer writes

status = "COMPLTED";

(Missing one letter.)

Will Java show an error?

No.

The application may fail later.

Using Enum

TradeStatus.java
public enum TradeStatus {

    PENDING,
    MATCHED,
    FAILED,
    CANCELLED

}
Main.java
public class Main {

    public static void main(String[] args) {

        TradeStatus status = TradeStatus.MATCHED;

        System.out.println(status);

    }

}
Output
MATCHED

Suppose we write

TradeStatus status = TradeStatus.COMPLETED;
Output
Compilation Error

Java immediately detects the invalid value.

This is called Type Safety.

What is Type Safety?

Type Safety means Java allows only valid values.

Example

TradeStatus status = TradeStatus.PENDING;

Valid ✅

TradeStatus status = TradeStatus.ABC;

Invalid ❌

Compiler Error.

Real-Time Banking Example

A trade can have only four statuses.

PENDING

MATCHED

FAILED

CANCELLED

Can someone assign

PROCESSING

No.

Compiler Error.

Business rules become safe.

Enum is More Than Constants

Most beginners think Enum is only used for constants.

Example

public enum Day {

    MONDAY,
    TUESDAY,
    WEDNESDAY

}

But this is only the beginning.

An Enum is actually a special class.

It can contain

Variables
Constructors
Methods
Business Logic

Implement Interfaces
Enum with Variables

Suppose every Trade Status has a message.

public enum TradeStatus {
    PENDING("Waiting for Processing"),
    MATCHED("Trade Matched Successfully"),
    FAILED("Trade Failed");
    private String message;
    TradeStatus(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
Main.java
public class Main {
    public static void main(String[] args) {
        System.out.println(TradeStatus.MATCHED.getMessage());
    }
}

Output
Trade Matched Successfully
Enum with Constructor

Notice

TradeStatus(String message)

This is an Enum Constructor.

Question

Can we create an Enum object?

new TradeStatus();

Answer

No.

Enum constructors are always private.

Java automatically creates Enum objects.

Enum with Methods

Enums can also contain methods.

DiscountType.java
public enum DiscountType {

    REGULAR,
    PREMIUM,
    VIP;

    public double getDiscount() {

        switch (this) {

            case REGULAR:
                return 5;

            case PREMIUM:
                return 10;

            case VIP:
                return 20;

            default:
                return 0;

        }

    }

}
Main.java
public class Main {

    public static void main(String[] args) {

        System.out.println("Discount : " +
                DiscountType.VIP.getDiscount() + "%");

    }

}
Output
Discount : 20%
Enum with Different Behavior

Each constant can have its own implementation.

DiscountType.java
public enum DiscountType {

    REGULAR {

        @Override
        public double getDiscount() {
            return 5;
        }

    },

    PREMIUM {

        @Override
        public double getDiscount() {
            return 10;
        }

    },

    VIP {

        @Override
        public double getDiscount() {
            return 20;
        }

    };

    public abstract double getDiscount();

}
Main.java
public class Main {

    public static void main(String[] args) {

        System.out.println("Regular : " +
                DiscountType.REGULAR.getDiscount());

        System.out.println("Premium : " +
                DiscountType.PREMIUM.getDiscount());

        System.out.println("VIP : " +
                DiscountType.VIP.getDiscount());

    }

}
Output
Regular : 5.0

Premium : 10.0

VIP : 20.0

Each Enum constant behaves differently.

Removing Large if-else Using Enum

Without Enum

String asset = "EQUITY";

if (asset.equals("EQUITY")) {
    System.out.println("Process Equity");
}
else if (asset.equals("BOND")) {
    System.out.println("Process Bond");
}
else if (asset.equals("OPTION")) {
    System.out.println("Process Option");
}

Problems

Large if-else
Hard to maintain
Easy to make mistakes
Better Solution Using Enum
AssetType.java
public enum AssetType {

    EQUITY {

        @Override
        public void process() {
            System.out.println("Processing Equity");
        }

    },

    BOND {

        @Override
        public void process() {
            System.out.println("Processing Bond");
        }

    },

    OPTION {

        @Override
        public void process() {
            System.out.println("Processing Option");
        }

    };

    public abstract void process();

}
Main.java
public class Main {

    public static void main(String[] args) {

        AssetType asset = AssetType.BOND;

        asset.process();

    }

}
Output
Processing Bond

No if-else is required.

Real-Time ReconX Example

Suppose ReconX supports three reconciliation rules.

EXACT

PRICE_TOLERANCE

QUANTITY_TOLERANCE

Instead of

if(rule.equals("EXACT"))

Use

rule.match(internalTrade, externalTrade);

Each rule knows how to compare trades.

This makes the code cleaner and easier to maintain.

Enum vs String
| String                   | Enum                   |
| ------------------------ | ---------------------- |
| Can store invalid values | Only predefined values |
| No compile-time checking | Compile-time checking  |
| Typing mistakes possible | No typing mistakes     |
| Less readable            | More readable          |
| Hard to maintain         | Easy to maintain       |


Advantages of Enum

Type Safe
Compiler Validation
Better Readability
Removes String Errors
Can Store Variables
Can Have Constructors
Can Have Methods
Can Contain Business Logic
Easy Maintenance
Removes Large if-else Blocks
Enterprise Use Cases



Enums are widely used for

• Order Status
• Payment Status
• User Roles
• HTTP Methods
• Transaction Types
• Asset Classes
• Currency Codes
• Reconciliation Rules



DAY 2 – OOP, SOLID & THE DOMAIN MODEL
MODULE 2 – VALUES, CONTRACTS & SOLID
SECTION 4 – Bean Validation (JSR-380 / Jakarta Validation)

(Based on Slides 38–40)

Learning Objective

After completing this section, participants will be able to:

• Understand Bean Validation.
• Learn why validation is important.
• Use commonly used validation annotations.
• Validate incoming REST requests.
• Understand custom validation.
• Learn enterprise validation best practices.


Suppose you build an Online Banking Application.


What is Validation?

Validation means checking whether the user input is correct before processing it.
It helps prevent invalid or incorrect data from entering the application or database.

Simple Definition

Validation is the process of checking user input before business logic or database operations are performed.

Why Do We Need Validation?

Imagine a Banking Application where a customer fills out a money transfer form.

Account Number : 1234567890

Amount         : 5000

Email          : abc@gmail.com

Mobile Number  : 9876543210


Everything looks good.

Now imagine another customer enters:

Account Number : ""

Amount         : -5000

Email          : abc

Mobile Number  : 123

Question:

Should this data be saved in the database?
No.

Invalid data can lead to application errors, incorrect transactions, and inconsistent database records.
Therefore, every enterprise application validates user input before executing business logic.

What is Bean Validation?

Bean Validation is a Java Specification (JSR-380) that validates Java objects using annotations.
Instead of writing multiple if conditions, we simply add validation annotations above the fields.

Spring Boot automatically performs the validation.
Without Bean Validation

Traditional Java code

if(tradeRef == null || tradeRef.isBlank()){
    throw new IllegalArgumentException("Trade Reference is required");
}

if(price <= 0){
    throw new IllegalArgumentException("Price must be positive");
}

if(quantity <= 0){
    throw new IllegalArgumentException("Quantity must be positive");
}

Problems
Repeated code
Difficult to maintain
Easy to forget validation
Makes code lengthy
With Bean Validation

@NotBlank
private String tradeRef;

@Positive
private BigDecimal price;

@Positive
private Integer quantity;


Common Validation Annotations
1. @NotNull
Definition

Ensures the value is not null.


2. @NotBlank
Definition

Used only for String values.

Checks:

Not null
Not empty
Not only spaces

3. @Positive
Definition

Value must be greater than zero.

4. @PositiveOrZero
Definition

Value must be 0 or greater.

Example

5. @Email
Definition

Checks whether the email format is valid.

Example

6. @Pattern
Definition

Validates data using a Regular Expression (Regex).

Example



Complete DTO Example

import java.math.BigDecimal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public class TradeRequest {

    @NotBlank(message = "Trade Reference is required")
    private String tradeRef;

    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;

    @NotNull(message = "Currency is required")
    private String currency;

    @Email(message = "Invalid Email Address")
    private String email;

    public TradeRequest() {
    }

    public TradeRequest(String tradeRef,
                        BigDecimal price,
                        Integer quantity,
                        String currency,
                        String email) {

        this.tradeRef = tradeRef;
        this.price = price;
        this.quantity = quantity;
        this.currency = currency;
        this.email = email;
    }

    public String getTradeRef() {
        return tradeRef;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public String getEmail() {
        return email;
    }
}

Using @Valid in Spring Boot

TradeController.java
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/trades")
public class TradeController {
    @PostMapping
    public ResponseEntity<String> saveTrade(
            @Valid @RequestBody TradeRequest request) {

        return ResponseEntity.ok("Trade Saved Successfully");
    }
}

What Does @Valid Do?
@Valid
@RequestBody TradeRequest request means
"Before calling this controller method, validate the TradeRequest object."
If validation fails, the controller method is never executed.

Spring Boot Validation Flow
Client
↓
HTTP Request
↓
TradeRequest DTO
↓
Bean Validation
↓
Valid?
      ↓
   Yes
      ↓
 Controller
↓
Service
↓
Database

If validation fails
Client
↓
TradeRequest
↓
Validation
↓
Invalid
↓
400 Bad Request
↓

Validation Errors Returned

Real Banking Example

Customer sends
{
  "tradeRef": "",
  "price": -200,
  "quantity": 0,
  "currency": null,
  "email": "abc"
}

Spring Boot automatically returns 400 Bad Request
Validation Errors
Trade Reference is required
Price must be greater than zero
Quantity must be greater than zero
Currency is required
Invalid Email Address
The Service Layer is never called.

Custom Validation Message

@NotBlank(message = "Trade Reference is mandatory")
private String tradeRef;
Instead of must not be blank
The client receives
Trade Reference is mandatory
This makes APIs more user-friendly.

Custom Validation (Business Example)

Suppose a trade date cannot be in the future.

You can create a custom annotation.

@ValidTradeDate
private LocalDate tradeDate;

The validator checks

Trade Date <= Current Date

If invalid,

the request is rejected.

Validation belongs in DTOs.

Business Rules belong in the Service Layer.


Validation vs Business Rules
| Validation          | Business Rule            |
| ------------------- | ------------------------ |
| Checks input format | Checks business logic    |
| Price > 0           | Trade already exists     |
| TradeRef not blank  | Counterparty is active   |
| Email is valid      | Credit limit available   |
| Quantity > 0        | Settlement date is valid |






Transition to Next Section

The final section of Day 2 introduces the SOLID Principles, one of the most important software design concepts in object-oriented programming. We'll cover Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, and Dependency Inversion, along with enterprise examples from the ReconX application, clean architecture practices, and common interview questions.

DAY 2 – OOP, SOLID & THE DOMAIN MODEL
MODULE 2 – VALUES, CONTRACTS & SOLID
SECTION 5 – SOLID Principles

(Based on Slides 41–52)

Learning Objective

After completing this section, participants will be able to:

• Understand all five SOLID principles.
• Design maintainable Java applications.
• Reduce coupling.
• Increase flexibility.
• Understand dependency injection.
• Apply SOLID principles in Spring Boot applications.
• Recognize SOLID interview questions.


S – Single Responsibility Principle (SRP)
What is SRP?
SRP stands for Single Responsibility Principle.
It means
One Class should do only One Job.
or
A class should have only one reason to change.

Simple Definition
A class should perform only one responsibility.
Do not put multiple jobs into one class.
Why Do We Need SRP?
Suppose you have a class called TradeService.

It performs

Trade Validation
Save Trade
Send Email
Generate Report

Question
If the Email format changes,
should we modify Trade Validation?
No.
Email and Validation are two different responsibilities.
Real-Life Example

Think about a School.
Teacher
Teaches Students

Accountant
Manages Fees

Security Guard
Provides Security

Question

Should the Teacher also collect fees and provide security?
No.

Everyone should perform only one job.
This is SRP.

Banking Example
Suppose a customer transfers money.
The application performs these tasks.

Validate Input
↓
Save Transaction
↓
Send SMS
↓
Send Email

Should one class perform all these tasks?
No.

Each task should have its own class.

Bad Example (Without SRP)

public class TradeService {
    public void validateTrade() {
        System.out.println("Validating Trade");
    }
    public void saveTrade() {
        System.out.println("Saving Trade");
    }
    public void sendEmail() {
        System.out.println("Sending Email");
    }
    public void generateReport() {
        System.out.println("Generating Report");
    }
}

Problem
This class performs

Validation
↓
Database
↓
Email
↓
Report

One class has 4 jobs.

If Report changes,
TradeService changes.
If Email changes,
TradeService changes again.
Too many reasons to change.

Good Example (Using SRP)

TradeValidator.java
public class TradeValidator {
    public void validate() {
        System.out.println("Trade Validated");
    }

}

TradeRepository.java
public class TradeRepository {
    public void save() {
        System.out.println("Trade Saved");
    }

}

EmailService.java
public class EmailService {
    public void sendEmail() {
        System.out.println("Email Sent");
    }
}

ReportService.java
public class ReportService {
    public void generateReport() {
        System.out.println("Report Generated");
    }
}

Main.java
public class Main {
    public static void main(String[] args) {
        TradeValidator validator = new TradeValidator();
        TradeRepository repository = new TradeRepository();
        EmailService email = new EmailService();
        ReportService report = new ReportService();

        validator.validate();
        repository.save();
        email.sendEmail();
        report.generateReport();

    }
}

Output
Trade Validated
Trade Saved
Email Sent
Report Generated

Each class performs only one job.

Advantages
Easy to understand
Easy to maintain
Easy to test
Easy to reuse
Less chance of bugs


O – Open Closed Principle (OCP)
What is OCP?
OCP stands for Open Closed Principle.
Simple Definition
A class should be

Open for Extension and Closed for Modification

This means
You should be able to add new features without changing existing code.

Why Do We Need OCP?

Suppose your banking application supports
Savings Account
Current Account

After one month, the bank introduces
Salary Account

Question
Should we modify the existing code every time a new account type is added?
No.

Instead,
we should simply add a new class.

Real-Life Example

Think about a Mobile Charger.

Mobile Charger
↓
Android Phone
↓
Samsung Phone
↓
OnePlus Phone

Later,
a new Android phone is launched.
Do you change the charger?
No.
You simply connect the new phone.
The charger remains unchanged.
This is OCP.

Banking Example
Suppose a bank supports these payment methods.

UPI, Credit Card, Net Banking

Tomorrow,

the bank introduces
Wallet Payment

Should we modify the existing payment classes?
No.

We simply create a new Wallet class.

Bad Example (Without OCP)

public class PaymentService {
    public void pay(String type) {
        if(type.equals("UPI")) {
            System.out.println("UPI Payment");
        }
        else if(type.equals("CARD")) {
            System.out.println("Card Payment");
        }
        else if(type.equals("NETBANKING")) {
            System.out.println("Net Banking Payment");
        }
    }
}

Problem
Suppose tomorrow
Wallet is introduced.

We must modify this class.

else if(type.equals("WALLET")){
    System.out.println("Wallet Payment");
}

Again,

if Crypto Payment is added,
we modify the same class again.
Every new feature changes existing code.

This violates OCP.
Good Example (Using OCP)

Step 1
Payment.java

public interface Payment {
    void pay();
}

Step 2
UPI.java
public class UPI implements Payment {
    @Override
    public void pay() {
        System.out.println("UPI Payment");
    }
}
Card.java
public class Card implements Payment {
    @Override
    public void pay() {
        System.out.println("Card Payment");
    }
}
NetBanking.java
public class NetBanking implements Payment {
    @Override
    public void pay() {
        System.out.println("Net Banking Payment");
    }
}

Step 3
Main.java
public class Main {
    public static void main(String[] args) {
        Payment payment;
        payment = new UPI();
        payment.pay();
        payment = new Card();
        payment.pay();
        payment = new NetBanking();
        payment.pay();
    }
}

Output
UPI Payment
Card Payment
Net Banking Payment
Now Business Introduces Wallet Payment

Do we modify

UPI
Card
NetBanking

No.

We simply create one new class.
Wallet.java

public class Wallet implements Payment {
    @Override
    public void pay() {
        System.out.println("Wallet Payment");
    }
}

Main.java
public class Main {
    public static void main(String[] args) {
        Payment payment = new Wallet();
        payment.pay();
    }
}

Output
Wallet Payment
Notice
We did not modify any existing class.
We only extended the application.
This is OCP.

How It Works
             Payment
                ▲
      ┌─────────┼─────────┐
      │         │         │
     UPI      Card   NetBanking
                          │
                          ▼
                       Wallet

Whenever a new payment method comes,
just create a new class.
No existing code changes.
Real-Time Banking Example
Initially Bank Supports
UPI
Card
Net Banking
Business adds
Wallet
Next year
Crypto Payment
Next year
International Transfer
We never modify old classes.
We only add new classes.
Advantages

Easy to add new features.
Existing code remains stable.
Less chance of bugs.
Easy maintenance.
Better scalability.

Common Mistakes
Wrong
if(type.equals("UPI"))
else if(type.equals("CARD"))
else if(type.equals("NETBANKING"))

Large if-else blocks become difficult to maintain.

Better
interface Payment
Each payment type implements the interface.

Best Practices
Use interfaces or abstract classes.
Prefer polymorphism instead of large if-else statements.
Add new classes instead of modifying old ones.
Keep existing code stable.


One-Line Rule

Add new functionality by creating a new class, not by changing existing code.


L – Liskov Substitution Principle (LSP)
What is LSP?
LSP stands for Liskov Substitution Principle.
It is the third principle of SOLID.
Simple Definition
A child class should be able to replace its parent class 
without breaking the program.
Simply,

Parent Object
↓
Can be replaced by
↓
Child Object
↓

Program should work correctly
Why Do We Need LSP?
Suppose we write a program using the parent class.
Later,
we replace it with a child class.
Question
Should the program still work?
Yes.
If the program crashes or behaves incorrectly,
then LSP is violated.
Real-Life Example
Think about a USB Mouse.
Your computer supports

Mouse

You can connect

HP Mouse
Dell Mouse
Logitech Mouse

The computer works correctly with all of them.
Every mouse behaves like a mouse.
This follows LSP.
Banking Example

Suppose a bank supports different payment methods.

Payment
↓
UPI
↓
Credit Card
↓
Net Banking

All payment methods should process payment.
Wherever a Payment object is expected,
we should be able to use

UPI
Credit Card
Net Banking
without changing the program.

Good Example

Step 1

Payment.java
public interface Payment {
    void pay();

}

Step 2
UPI.java
public class UPI implements Payment {
    @Override
    public void pay() {
        System.out.println("Payment using UPI");
    }
}

Card.java
public class Card implements Payment {
    @Override
    public void pay() {
        System.out.println("Payment using Credit Card");
    }
}

NetBanking.java
public class NetBanking implements Payment {
    @Override
    public void pay() {
        System.out.println("Payment using Net Banking");
    }
}

Step 3

Main.java
public class Main {
    public static void main(String[] args) {
        Payment payment;

        payment = new UPI();
        payment.pay();

        payment = new Card();
        payment.pay();

        payment = new NetBanking();
        payment.pay();

    }

}
Output
Payment using UPI
Payment using Credit Card
Payment using Net Banking

Notice
Every child class replaces the parent successfully.
This follows LSP.

How It Works
          Payment
              ▲
      ┌───────┼────────┐
      │       │        │
     UPI    Card   NetBanking


Advantages of LSP
Proper use of inheritance
Better polymorphism
Easy code reuse
Easy testing
Reliable applications





I – Interface Segregation Principle (ISP)
What is ISP?
ISP stands for Interface Segregation Principle.
It is the fourth principle of SOLID.
Simple Definition
A class should not be forced to implement methods that it does not need.
In simple words,
Instead of one large interface, create multiple small interfaces.
Why Do We Need ISP?
Suppose a banking application has one interface called

TradeOperations
It contains

Process Trade
Send Email
Generate Report
Print Report
Export Excel

Question
If a class only wants to process trades,
why should it implement email and report methods?
It should not.
This is why ISP exists.
Real-Life Example
Think about a Smartphone.

A smartphone can

Call
Take Photos
Browse Internet
Play Music

Now imagine an old landline telephone.
Can it take photos?
No.
Should we force it to implement a takePhoto() method?
No.
Every device should implement only the features it supports.
This is ISP.
Banking Example

Suppose we have different services.
Trade Processor
↓
Process Trade
Notification Service
↓
Send Email
Report Service
↓
Generate Report

Each service has a different responsibility.
Bad Example (Without ISP)
One large interface.

public interface TradeOperations {
    void processTrade();
    void sendEmail();
    void generateReport();
    void exportExcel();
}

Now suppose we create a class.
public class TradeProcessor implements TradeOperations {
    @Override
    public void processTrade() {
        System.out.println("Trade Processed");
    }
    @Override
    public void sendEmail() {

    }
    @Override
    public void generateReport() {

    }
    @Override
    public void exportExcel() {

    }
}

Problem
The class only wants to process trades.

But it is forced to implement
sendEmail()
generateReport()
exportExcel()

These methods are unnecessary.
This violates ISP.

Good Example (Using ISP)
Instead of one large interface,
create small interfaces.

Step 1

TradeProcessor.java
public interface TradeProcessor {
    void processTrade();
}

NotificationService.java
public interface NotificationService {
    void sendEmail();
}

ReportService.java
public interface ReportService {
    void generateReport();

}

Step 2

TradeProcessorImpl.java
public class TradeProcessorImpl implements TradeProcessor {
    @Override
    public void processTrade() {
        System.out.println("Trade Processed");
    }

}

EmailService.java
public class EmailService implements NotificationService {
    @Override
    public void sendEmail() {
        System.out.println("Email Sent");
    }
}

ReportGenerator.java
public class ReportGenerator implements ReportService {
    @Override
    public void generateReport() {
        System.out.println("Report Generated");
    }
}

Step 3
Main.java
public class Main {
    public static void main(String[] args) {
        TradeProcessor processor = new TradeProcessorImpl();
        NotificationService email = new EmailService();
        ReportService report = new ReportGenerator();
        processor.processTrade();
        email.sendEmail();
        report.generateReport();
    }
}

Output
Trade Processed

Email Sent

Report Generated
How It Works
             TradeProcessor
                   ▲
                   │
         TradeProcessorImpl

-------------------------------

         NotificationService
                   ▲
                   │
            EmailService

-------------------------------

            ReportService
                  ▲
                  │
          ReportGenerator

Each interface has only one responsibility.


D – Dependency Inversion Principle (DIP)

What is DIP?
DIP stands for Dependency Inversion Principle.
It is the fifth and last principle of SOLID.

Simple Definition
A class should depend on an interface (abstraction), 
not on a concrete class (implementation).

POJO and POJI 
Plain Old Java Object 
Plain Old Java Interface 


In simple words,
Don't directly create objects using new. Instead, depend on interfaces.
Why Do We Need DIP?
Suppose a banking application stores trades in PostgreSQL.
After one year, the bank decides to use Oracle Database.
Question:
Should we modify the entire TradeService class?
No.
Only the database implementation should change.

interface Bank {
    void withdraw();
}
class SBI implements Bank {

}
class Hdfc implements Bank {

}

Real-Life Example

Think about charging your mobile phone.
You plug the charger into a socket.
Socket
↓
Mobile Charger

The charger depends on the socket standard, not on the electricity company.
Whether electricity comes from Tata Power, BESCOM, or Adani Power, the charger works.
The charger depends on a standard (abstraction).

Banking Example
Suppose a banking application saves trades.

Today

TradeService
↓
PostgreSQL

Tomorrow

TradeService
↓
Oracle Database

If TradeService directly creates a PostgreSQL object, 
changing the database becomes difficult.

Instead, TradeService should depend on a TradeRepository interface.

Bad Example (Without DIP)


TradeService.java               business logic 
public class TradeService {
    private TradeRepository repository =new TradeRepository();
    public void saveTrade() {
        repository.save();

    }
}
TradeRepository.java            db logic 
public class TradeRepository {
    public void save() {
        System.out.println("Saved in PostgreSQL");
    }
}

Problem

Tomorrow the company changes the database.

PostgreSQL
↓
Oracle

We must modify TradeService.
This creates tight coupling.

Good Example (Using DIP)

Step 1
TradeRepository.java

public interface TradeRepository {
    void save();
}
Step 2

PostgreSQLRepository.java
public class PostgreSQLRepository implements TradeRepository {
    @Override
    public void save() {
        System.out.println("Trade Saved in PostgreSQL");
    }
}
OracleRepository.java
public class OracleRepository implements TradeRepository {
    @Override
    public void save() {
        System.out.println("Trade Saved in Oracle");
    }
}

Step 3
TradeService.java
public class TradeService {
    private TradeRepository repository;
    public TradeService(TradeRepository repository) {
        this.repository = repository;
    }
    public void saveTrade() {
        repository.save();
    }
}

Notice
There is no
new PostgreSQLRepository();
inside TradeService.

Step 4

Main.java
public class Main {
    public static void main(String[] args) {
        TradeRepository repository =new PostgreSQLRepository();
        TradeService service = new TradeService(repository);
        service.saveTrade();
    }
}

Output
Trade Saved in PostgreSQL
Changing Database
Now the bank wants Oracle.
Only change this line.

TradeRepository repository = new OracleRepository();

Everything else remains the same.

Output
Trade Saved in Oracle

No changes are required in TradeService.

This is DIP.

How It Works
                TradeService

                     │

                     ▼

             TradeRepository

             ▲             ▲

             │             │

 PostgreSQLRepository   OracleRepository

TradeService knows only the TradeRepository interface.

It does not know which database is being used.

Spring Boot Example

In Spring Boot, Dependency Injection is used to implement DIP.

Repository
@Repository
public class TradeRepository {

    public void save() {

        System.out.println("Trade Saved");

    }

}
Service
@Service
public class TradeService {

    private final TradeRepository repository;

    public TradeService(TradeRepository repository) {

        this.repository = repository;

    }

    public void saveTrade() {

        repository.save();

    }

}

Notice

There is no

new TradeRepository();

Spring automatically creates the object and injects it into TradeService.

This is called Dependency Injection (DI).

DIP vs Dependency Injection

Many beginners confuse these terms.


| Dependency Inversion Principle (DIP) | Dependency Injection (DI)       |
| ------------------------------------ | ------------------------------- |
| Design Principle                     | Design Pattern / Technique      |
| Says depend on interfaces            | Provides required objects       |
| Part of SOLID                        | Implemented by Spring Framework |



SOLID Summary
| Principle                     | Easy Meaning                                                                             |
| ----------------------------- | ---------------------------------------------------------------------------------------- |
| **S - Single Responsibility** | One class should do one job.                                                             |
| **O - Open Closed**           | Add new features without modifying existing code.                                        |
| **L - Liskov Substitution**   | A child class should be usable wherever the parent is expected.                          |
| **I - Interface Segregation** | Create small, focused interfaces instead of one large interface.                         |
| **D - Dependency Inversion**  | Depend on interfaces, not concrete classes. Use Dependency Injection for loose coupling. |
