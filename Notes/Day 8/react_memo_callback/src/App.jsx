import { useState } from "react";
import WithoutOptimization from "./WithoutOptimization";
import WithOptimization from "./WithOptimization";

function App() {
  const [optimized, setOptimized] = useState(false);

  return (
    <div className="container">
      <h1>React Performance Optimization Demo</h1>

      <div className="buttons">
        <button onClick={() => setOptimized(false)}>
          Without Optimization
        </button>

        <button onClick={() => setOptimized(true)}>
          With Optimization
        </button>
      </div>

      <hr />

      {optimized ? (
        <WithOptimization />
      ) : (
        <WithoutOptimization />
      )}
    </div>
  );
}

export default App;