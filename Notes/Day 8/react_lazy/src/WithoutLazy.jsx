import { useState } from "react";
import Home from "./Home";
import About from "./About";

function WithoutLazy() {
  console.log("Without Lazy Component Rendered");

  const [page, setPage] = useState("home");

  return (
    <div>
      <h2>Without Lazy Loading</h2>

      <div className="buttons">
        <button onClick={() => setPage("home")}>
          Home
        </button>

        <button onClick={() => setPage("about")}>
          About
        </button>
      </div>

      <hr />

      {page === "home" ? <Home /> : <About />}
    </div>
  );
}

export default WithoutLazy;