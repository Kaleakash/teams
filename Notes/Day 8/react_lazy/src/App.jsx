import { useState } from "react";
import WithoutLazy from "./WithoutLazy";
import WithLazy from "./WithLazy";

function App() {
  const [lazyLoading, setLazyLoading] = useState(false);

  return (
    <div className="container">
      <h1>React.lazy & Suspense Demo</h1>

      <div className="buttons">
        <button onClick={() => setLazyLoading(false)}>
          Without Lazy
        </button>

        <button onClick={() => setLazyLoading(true)}>
          With Lazy
        </button>
      </div>

      <hr />

      {lazyLoading ? <WithLazy /> : <WithoutLazy />}
    </div>
  );
}

export default App;