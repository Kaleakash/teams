import { useEffect } from "react";

function Home() {
  useEffect(() => {
    console.log("Home Component Mounted");

    return () => {
      console.log("Home Component Unmounted");
    };
  }, []);

  console.log("Home Component Rendered");

  return (
    <div className="card">
      <h2>Home Page</h2>

      <p>
        This is the Home component.
      </p>

      <p>
        Open the browser Console and Network tab to observe the behavior.
      </p>
    </div>
  );
}

export default Home;