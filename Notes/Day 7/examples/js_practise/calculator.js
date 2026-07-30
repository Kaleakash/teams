function sum() {
    let num1 = document.getElementById("num1").value;
    let num2 = document.getElementById("num2").value;
    let result = parseFloat(num1) + parseFloat(num2);
    document.getElementById("result").innerHTML = "Result: " + result;
    reset();

}
function sub() {
    let num1 = document.getElementById("num1").value;
    let num2 = document.getElementById("num2").value;
    let result = parseFloat(num1) - parseFloat(num2);
    document.getElementById("result").innerHTML = "Result: " + result;
    reset();
}

function reset() {
    document.getElementById("num1").value="";
    document.getElementById("num2").value="";
}