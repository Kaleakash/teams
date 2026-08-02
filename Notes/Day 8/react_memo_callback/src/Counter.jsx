import { memo } from "react";

function Counter({ count, square }) {
  console.log("Counter Rendered");

  return (
    <div className="card">
      <h3>Counter Component</h3>

      <h2>Count : {count}</h2>

      <h2>Square : {square}</h2>
    </div>
  );
}

// Export memoized component
export default memo(Counter);