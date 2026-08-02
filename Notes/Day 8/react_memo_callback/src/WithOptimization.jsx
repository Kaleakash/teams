import { useState, useMemo, useCallback } from "react";
import Counter from "./Counter";
import Child from "./Child";

function WithOptimization() {
  console.log("WithOptimization Rendered");

  const [count, setCount] = useState(0);
  const [text, setText] = useState("");

  // Executes only when count changes
  const square = useMemo(() => {
    console.log("Calculating Square...");
    return count * count;
  }, [count]);

  // Function reference remains the same
  const increment = useCallback(() => {
    console.log("Increment Function Called");
    setCount((prev) => prev + 1);
  }, []);

  return (
    <div>
      <h2>With Optimization</h2>

      <Counter count={count} square={square} />

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

export default WithOptimization;