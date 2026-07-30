// // ES5 style means old version 

// let obj1 = new Promise(function(resolve, reject) {
//     // Promise logic here

//    // resolve("Promise resolved successfully!");
//    reject("Promise rejected with an error!");

// });
// // to consume promise 
// obj1.then(function(value) {
//     console.log('Promise resolved with value:', value);
// }).catch(function(error) {
//     console.error('Promise rejected with error:', error);
// }).finally(function() {
//     console.log('Promise handling completed.');
// });
// console.log("normal statement1");
// console.log("normal statement2");
// console.log("normal statement3");

//fetch("").then().catch()
// ES5 style consume rest api 
// fetch("https://jsonplaceholder.typicode.com/todos").
// then(response=>response.json()).then(result=>console.log(result)).
// catch(error=>console.log(error));


// ES6 style means new version
async function fetchData() {
    //let response = fetch("https://jsonplaceholder.typicode.com/todos"); // asynchronous operation
    // await if a keyword use to make function or code as synchronous 
    // await is replacement of then() method
    try{
    let response = await fetch("https://jsonplaceholder.typicode.com/todos"); // synchronous operation
    let result = await response.json(); // synchronous operation
    console.log(result);
    console.log("normal statement1");
    console.log("normal statement2");
    console.log("normal statement3");
    }catch(error){
        console.log(error);
    }finally{
        console.log("Promise handling completed.");
    }
}

fetchData();





