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
fetch("https://jsonplaceholder.typicode.com/todos").
then(response=>response.json()).then(result=>console.log(result)).
catch(error=>console.log(error));

