import { useContext } from "react";
import { UserContext } from "./UserContext";

function Home() {
  const { user, setUser } = useContext(UserContext);

  return (
    <div className="home">
      <h2>Welcome, {user}</h2>

      <button onClick={() => setUser("Rahul")}>
        Change User
      </button>
    </div>
  );
}

export default Home;