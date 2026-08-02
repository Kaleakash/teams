import { lazy, Suspense, useState } from "react";

const Home = lazy(() => import("./Home"));
const About = lazy(() => import("./About"));

function WithLazy() {
  console.log("With Lazy Component Rendered");

  const [page, setPage] = useState("home");

  return (
    <div>
      <h2>With Lazy Loading</h2>

      <div className="buttons">
        <button onClick={() => setPage("home")}>
          Home
        </button>

        <button onClick={() => setPage("about")}>
          About
        </button>
      </div>

      <hr />

      <Suspense
        fallback={
          <h2 className="loading">
            Loading Component...
          </h2>
        }
      >
        {page === "home" ? <Home /> : <About />}
      </Suspense>
    </div>
  );
}

export default WithLazy;