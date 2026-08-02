import { memo } from "react";

function Child({ increment }) {
  console.log("Child Rendered");

  return (
    <div className="card">
      <h3>Child Component</h3>

      <button onClick={increment}>
        Increment
      </button>
    </div>
  );
}

export default memo(Child);