
http://www.google.com ---> URL (Uniform resource locator)
http/https--> protocol 
www--> world wide web 
google --> domain 
com --> commercial 

                            req(http/https)------------->

Client                                                          Server 

                <------------res(http/https)


                                    HTML : Hyper text mark up language 
                                       1 to 5 HTML 5 version.      
                                    CSS : Cascading style sheet 
                                    1,2 and 3 
                                    types of css 
                                    inline css 
                                    internal or embedded css 
                                    external css 
bootstrap, semantic UI tailwind css etc. 
                                    JS 
                                    JavaScript was object based 
                                    interpreter scripting language 
                                    mainly use to do validation on client side in 
                                    browser environment. 

DOM : document object model 
JS provided lot of pre defined function which help to 
read, write and update html contents ie dom or any tag contents dynamically. 

JS provided lot of pre defined library as well as framework 
React JS 
Angular Framework 
Vue JS 
etc 
to improve dom functionality 

                            From ES6 version js we can use class features. 


After node js JS also known as client side as well as server side 
scripting language. 

Node with Express JS is like a Spring boot 


display :

by default all html tags internally follow box model 



Flex layout property 

What is Flexbox?

Flexbox (Flexible Box Layout) is a CSS layout model used to 
arrange HTML elements in a single row or a single column. 
It automatically manages spacing, alignment, and sizing of elements, 
making responsive web design much easier.

Real-world uses:

Navigation bars
Dashboard layouts
Login pages
Product cards
Image galleries
Footer sections



| Property             | Purpose              | Example           |
| -------------------- | -------------------- | ----------------- |
| `display:flex`       | Enables Flexbox      | Horizontal layout |
| `flex-direction`     | Row or Column        | Menu, Sidebar     |
| `justify-content`    | Main-axis alignment  | Navigation bar    |
| `align-items`        | Cross-axis alignment | Center login form |
| `gap`                | Space between items  | Card layouts      |
| `flex-wrap`          | Wrap items           | Product gallery   |
| `flex-grow` / `flex` | Control item size    | Dashboard panels  |


CSS Variables (Design Tokens)
Definition

CSS Variables (also called CSS Custom Properties) 
are reusable values used to store colors, fonts, 
spacing, border radius, and other styles. 
They help maintain consistency across the application.

Why Use CSS Variables?
Write reusable CSS
Easy to maintain
Change values in one place
Support dark/light themes
Reduce duplicate code
Syntax

Step 1: Declare Variables
:root{
    --primary-color:#1976d2;
    --secondary-color:#28a745;
    --text-color:#333333;
    --background-color:#f5f5f5;
}
Step 2: Use Variables
button{
    background:var(--primary-color);
    color:white;
}


Dark & Light Theme
Definition

A Theme changes the appearance of a website without 
changing the HTML structure.

Usually applications provide

Light Theme
Dark Theme

Why Use Themes?
Better User Experience
Reduce Eye Strain
User Preference
Professional UI

Syntax

:root{

--background:white;

--text:black;

}

[data-theme="dark"]{
--background:#222;
--text:white;
}

Use Variables

body{

background:var(--background);

color:var(--text);

}

JavaScript

document.documentElement.dataset.theme="dark";

Responsive Design
Definition

Responsive Design makes a webpage look good on all devices.

Examples

Mobile
Tablet
Laptop
Desktop
Why Responsive?

One website should work everywhere.

Syntax
@media(max-width:768px){

.container{

flex-direction:column;

}

}
Explanation
Screen Width >768px

Desktop Layout

↓

Screen Width <768px

Mobile Layout
Common Breakpoints
Mobile

0–767px
Tablet

768–1024px
Desktop

1025px+


Fetch API
What is Fetch API?
Definition

The Fetch API is a modern JavaScript interface used to send HTTP requests to a server and receive data asynchronously.

Why Use Fetch API?
Retrieve data from REST APIs
Send data to the server
Update UI dynamically
Replace XMLHttpRequest
HTTP Methods
GET
POST
PUT
DELETE
Fetch Syntax
fetch(url)
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.log(error));

    GET Request Example

fetch("https://jsonplaceholder.typicode.com/users")
    .then(response => response.json())
    .then(users => console.log(users))
    .catch(error => console.error(error));


Module 3 – Async / Await
Learning Objectives

After completing this module, students will be able to:

Understand asynchronous programming
Understand Promises
Use the async keyword
Use the await keyword
Handle errors using try...catch
Display data from an API
Show loading messages
Build a simple dashboard using Async/Await
What is Asynchronous Programming?

Normally JavaScript executes code line by line.

Sometimes operations like:

Calling an API
Reading a file
Waiting for a database
Downloading images

take time.

Instead of stopping the browser, JavaScript performs these operations asynchronously.

Start Program

↓

Call API

↓

Continue Other Work

↓

Receive Response

↓

Display Data
What is async?

The async keyword makes a function asynchronous.

It automatically returns a Promise.

Syntax
async function loadProducts(){

}
What is await?

The await keyword pauses the function until the Promise is completed.

It can only be used inside an async function.

Syntax
const response = await fetch(url);


What is Server-Sent Events (SSE)?

Server-Sent Events (SSE) is a technology that allows the server to continuously push data to the browser over a single HTTP connection.

Unlike Fetch API, where the client requests data repeatedly, SSE keeps the connection open and the server sends updates whenever new data is available.

How SSE Works
Browser
    │
    │ EventSource Connection
    ▼
Spring Boot Server
    │
    │ Sends Events Continuously
    ▼
Browser Receives Live Updates

Fetch API vs SSE

| Fetch API                        | Server-Sent Events           |
| -------------------------------- | ---------------------------- |
| Client requests data             | Server pushes data           |
| One request = one response       | One request = many responses |
| Connection closes after response | Connection remains open      |
| Manual refresh required          | Automatic updates            |
| Best for CRUD operations         | Best for live dashboards     |


Project Structure

Spring Boot Backend
Controller
@RestController
public class NotificationController {

    @GetMapping(value="/events",
            produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamEvents(){

        return Flux.interval(Duration.ofSeconds(2))
                .map(sequence ->
                        "New Order Received : " + LocalTime.now());

    }

}
index.html
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>SSE Demo</title>

<link rel="stylesheet" href="style.css">

</head>

<body>

<h1>Live Notifications</h1>

<div id="notifications">

Waiting for notifications...

</div>

<script src="script.js"></script>

</body>
</html>

style.css

*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:Arial;
}

body{

    background:#f4f4f4;

    padding:30px;

}

h1{

    margin-bottom:20px;

}

#notifications{

    background:white;

    border-left:5px solid green;

    padding:20px;

    border-radius:8px;

    box-shadow:0 2px 6px gray;

    font-size:18px;

    min-height:80px;

}
script.js
const eventSource = new EventSource("http://localhost:8080/events");

const notifications = document.getElementById("notifications");

eventSource.onmessage = function(event){

    notifications.innerHTML = event.data;

};

eventSource.onerror = function(){

    notifications.innerHTML = "Connection Lost.";

};
Execution Flow
Browser Opens Page
        │
        ▼
Creates EventSource
        │
        ▼
Connects to Spring Boot
        │
        ▼
Server Sends Event Every 2 Seconds
        │
        ▼
Browser Receives Message
        │
        ▼
Dashboard Updates Automatically


What is a Data Table?

A Data Table is a structured way of displaying data in rows and columns. It helps users easily view, search, sort, and manage large amounts of information.

Real-World Examples
Product List
Customer Records
Employee Details
Bank Transactions
Trade Dashboard
Order History
Why Do We Need an Advanced Data Table?

A simple HTML table works for small datasets. However, enterprise applications often contain thousands of records, making it difficult to locate information.

An Advanced Data Table provides features such as:

Sorting
Filtering
Searching
Sticky (Frozen) Header
Pagination
Responsive Design
Reusable Components

