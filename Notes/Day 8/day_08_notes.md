React JS – High-Level Notes


JS : 
DOM : Document Object Model 
JS provided lot of pre defined library as well as framework 
jQuery 
Angular JS 
Angular Framework which MVC 
controller is replaced by components. 
React JS is 

What is React?

React is an open-source JavaScript library used to build fast, interactive, 
and reusable user interfaces (UI) or user components. 
It follows a component-based architecture 
and efficiently updates the UI using the Virtual DOM.

Developed By: Meta (Facebook)

Why React?
Build Single Page Applications (SPA)
Reusable Components it is use to control the view or part of view page. 
using component we can create user defined tags. 

Fast Rendering with Virtual DOM
One-way Data Binding
Large Ecosystem
Easy to Learn
Strong Community Support

we can create component using 
1. class style component            ES6
2. function style component         ES5 
    normal function 
    arrow functions. 

Features of React
Component-Based Architecture
Virtual DOM
JSX
One-Way Data Flow
Reusable Components
Hooks   : it is a special function which make function component as state full components. 
State Management
Fast Rendering
Declarative Programming


Node JS : Node js is a run time environment for JavaScript library or framework. 
it contains lot of pre defined modules which help to do server side programming language 
like java. 
npm : node package manager : which help to download external node js modules. 
toolkit. 
vite : it is toolkit which help to create the frontend library or framework base upon js. 

npm create vite@latest demo-app (this command is use to create react js project)

demo-app 

react --->
javascript -->
ESLint --->
No 
after project creation 
you need to move inside a project folder 
cd demo-app 
npm install 
npm run dev 

open the project in vs code. 


React Application Flow

Browser
    │
    ▼
main.jsx
    │
    ▼
App.jsx
    │
    ▼
Components
    │
    ▼
User Interface

Component

A component is a reusable piece of UI that returns JSX.

Example:

function Welcome() {
    return <h1>Welcome</h1>;
}
JSX

JSX stands for JavaScript XML.

It allows HTML-like syntax inside JavaScript.

Example:

const element = <h1>Hello React</h1>;
Props

Props are read-only data passed from a parent component to a child component.

Example:

<Home name="Akash" />

Child:

function Home({ name }) {
    return <h2>{name}</h2>;
}
State

State stores dynamic data within a component.

Example:

const [count, setCount] = useState(0);
Event Handling

React uses camelCase event names.

Example:

<button onClick={increment}>
    Increment
</button>

Conditional Rendering

Render UI based on conditions.

Example:

{
    isLoggedIn ? <Home /> : <Login />
}
List Rendering

Render multiple items using map().

Example:

users.map(user => (
    <li>{user}</li>
))

Forms

React forms use controlled components.

Example:

<input
    value={name}
    onChange={(e) => setName(e.target.value)}
/>

Hooks

Hooks allow functional components to use 
React features like state and lifecycle methods.

Common Hooks:

useState
useEffect
useContext
useMemo
useCallback
useRef

useState

Used to manage component state.

const [count, setCount] = useState(0);
useEffect

Used for side effects such as:

API Calls
Timers
Event Listeners
useEffect(() => {

}, []);
Component Lifecycle
Mount
   │
Update
   │
Unmount

Handled using useEffect.

Virtual DOM
User Action
      │
      ▼
Virtual DOM

Compare Changes

      │

      ▼

Real DOM Update

React updates only the changed elements instead of the entire page.

One-Way Data Flow
Parent

   │ Props

   ▼

Child

Data flows only from parent to child.

Folder Structure
src/

App.jsx

main.jsx

components/

assets/

styles/




Module 1 – Higher-Order Components (HOC)
What is a Higher-Order Component?

A Higher-Order Component (HOC) is a function that takes an existing component 
as input and returns a new component with additional functionality. 
It allows common logic such as authentication, logging, or error handling 
to be reused across multiple components without modifying the original component.

Why HOC?

Instead of writing the same authentication or permission-check logic in every page, 
you write it once and reuse it everywhere.

Real-world Use Cases
Authentication
Authorization
Logging
Error Boundaries
Analytics
Role-Based Access

Example
Dashboard Component
↓
withAuth()
↓

Authenticated Dashboard

function withWelcome(Component){

    return function(){

        return (
            <>
                <h2>Welcome User</h2>
                <Component/>
            </>
        );

    };

}

Module 2 – Compound Components
What are Compound Components?

Compound Components are a group of related components that work 
together while sharing the same internal state. 
The parent component manages the shared state, 
and child components consume it automatically, 
resulting in a clean and flexible API.

Why?

Instead of passing many props to every child, 
the parent coordinates the shared behavior.

Real-world Use Cases

Data Table
Tabs
Accordion
Dropdown
Menu
Modal

Example

<DataTable>

    <DataTable.Header/>

    <DataTable.Body/>

</DataTable>


with help of 
fetch we can call rest api 
axios :axios is a third party library which internally use fetch 
function to call rest api. 

npm install axios 

axios.get()
axios.post()
axios.put()
axios.delete()


Module 3 – Custom Hooks
What are Custom Hooks?

A Custom Hook is a reusable JavaScript function whose name 
starts with use. It encapsulates stateful logic (such as
 data fetching, subscriptions, timers, or debouncing) so 
 it can be shared across multiple components.

Why?

while creating custom hook we use few pre defined hook. 
Avoid duplicating the same useEffect and useState logic in multiple components.

Real-world Use Cases

API Calls
Search
Pagination
Infinite Scroll
Theme
Authentication
WebSocket
Server-Sent Events

Example
const products = useProducts();

const users = useUsers();


Module 4 – Context API
App 
    Parent --> state variable with value as n=100;
            using the props we need to pass value from Parent to Child1
            Child1 
                    
                        Child3
            Child4
                    Child5

What is Context API?

Context API allows data to be shared across the component tree 
without passing props through every intermediate component (prop drilling). 
It is ideal for application-wide state.

Why?

Without Context, data has to be passed through multiple levels of components.

Example without Context
App

↓

Dashboard

↓

Product

↓

Price

Price must be passed through every component.

Example with Context
App

↓

Context Provider

↓

Any Component
Real-world Use Cases
Authentication
Theme
Language
Shopping Cart
User Details

Module 5 – Performance Optimization
Why Performance Optimization?

React automatically re-renders components when state or props change. 
In large applications, unnecessary re-renders can reduce performance. 
React provides optimization techniques such as React.memo, useMemo, 
and useCallback to avoid extra work.

React.memo

Prevents unnecessary component re-rendering.

const Product = React.memo(ProductCard);

useMemo

Caches expensive calculations.

const total = useMemo(() => {

    return products.reduce(...);

}, [products]);

useCallback

Caches function references.

const saveProduct = useCallback(() => {

}, []);

Real-world Use Cases
Dashboard
Charts
Large Tables
Search
Analytics

Module 6 – Code Splitting
What is Code Splitting?

Code Splitting divides a React application into smaller 
JavaScript bundles. Components are downloaded only when needed, 
reducing the initial load time. React.lazy loads components on demand, 
while Suspense displays a fallback UI during loading.


Why?

Large applications become faster because only the required pages are loaded initially.

Example
const Dashboard = React.lazy(() => import("./Dashboard"));

<Suspense fallback={<h2>Loading...</h2>}>

    <Dashboard/>

</Suspense>
Real-world Use Cases
Admin Panel
Reports
Analytics
Heavy Components

Module 7 – Forms using React Hook Form & Yup
React Hook Form

A lightweight library used to manage forms efficiently with minimal re-renders.

Yup

A schema validation library 
used to validate form inputs declaratively. 
Together, they simplify form management and validation.

Why?
Less code
Better validation
Faster forms
Example

const {

    register,

    handleSubmit,

    formState:{errors}

} = useForm();
Validation

<input

    {...register("email")}

/>

Module 8 – React Testing Library (RTL)
What is React Testing Library?

React Testing Library is used to test React components from the user's perspective. 
Instead of testing implementation details, 
it verifies what users see and how they interact with the application.

Why?

Ensures UI works correctly after changes and helps prevent regressions.

Example

render(<Login/>);

expect(
screen.getByText("Login")

).toBeInTheDocument();

Real-world Use Cases
Login Page
Registration
Dashboard
Product Table
Forms
Buttons
Enterprise React Architecture
Browser

      │

      ▼

React Application

      │

      ▼

Authentication (HOC)

      │

      ▼

Context API

      │

      ▼

Custom Hooks

      │

      ▼

API Service

      │

      ▼

Backend API
React Production Workflow
Create Components

        │

        ▼

Reuse Components

        │

        ▼

Custom Hooks

        │

        ▼

Context API

        │

        ▼

Performance Optimization

        │

        ▼

Lazy Loading

        │

        ▼

Forms

        │

        ▼

Testing

        │

        ▼

Production Deployment




