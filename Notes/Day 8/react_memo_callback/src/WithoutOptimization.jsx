import { useState } from "react";
import Counter from "./Counter";
import Child from "./Child";

function WithoutOptimization() {
  console.log("WithoutOptimization Rendered");

  const [count, setCount] = useState(0);
  const [text, setText] = useState("");

  // This runs on EVERY render
  const square = () => {
    console.log("Calculating Square...");
    return count * count;
  };

  // New function is created on EVERY render
  const increment = () => {
    console.log("Increment Function Created");
    setCount((prev) => prev + 1);
  };

  return (
    <div>
      <h2>Without Optimization</h2>

      <Counter count={count} square={square()} />

      <Child increment={increment} />

      <br />
      <br />

      <input
        type="text"
        placeholder="Type here..."
        value={text}
        onChange={(e) => setText(e.target.value)}
      />

      <p>Text : {text}</p>
    </div>
  );
}

export default WithoutOptimization;