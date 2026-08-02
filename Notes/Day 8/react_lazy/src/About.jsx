import { useEffect } from "react";

function About() {
  useEffect(() => {
    console.log("About Component Mounted");

    return () => {
      console.log("About Component Unmounted");
    };
  }, []);

  console.log("About Component Rendered");

  return (
    <div className="card">
      <h2>About Page</h2>

      <p>
        This is the About component.
      </p>

      <p>
        In the <strong>Without Lazy</strong> example, this component is included
        in the initial bundle.
      </p>

      <p>
        In the <strong>With Lazy</strong> example, this component is downloaded
        only when you click the <strong>About</strong> button.
      </p>
    </div>
  );
}

export default About;